package br.com.zaya.service;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.integration.KonciliWebhookClient;
import br.com.zaya.models.ConciliacaoResponse;
import br.com.zaya.models.ImportacaoResultado;
import br.com.zaya.repository.ConciliacaoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportacaoConciliacaoService {
    private static final Logger LOG = Logger.getLogger(ImportacaoConciliacaoService.class.getName());
    private static final String CNPJ_PADRAO = "04933508000446";

    private final KonciliWebhookClient webhookClient;
    private final ConciliacaoRepository repository;

    public enum DuplicidadeStrategy {
        IDTRA_CODVEND,
        IDTRA_IDEX
    }

    public ImportacaoConciliacaoService() {
        this.webhookClient = new KonciliWebhookClient();
        this.repository = new ConciliacaoRepository();
    }

    public ImportacaoResultado importarPorPeriodo(
            String webhookUrl,
            String dataIni,
            String dataFim,
            DuplicidadeStrategy strategy,
            boolean vincularNotasAposImportacao
    ) {
        long inicioMs = System.currentTimeMillis();

        int recebidos = 0;
        int inseridos = 0;
        int duplicados = 0;
        int invalidos = 0;
        int falhas = 0;
        int idsAutoGerados = 0;
        int notasVinculadas = 0;

        JapeSession.SessionHandle hnd = null;
        JdbcWrapper jdbc = null;

        try {
            List<ConciliacaoResponse> itens = webhookClient.buscarConciliacoes(webhookUrl, CNPJ_PADRAO, dataIni, dataFim);
            recebidos = itens.size();

            if (recebidos == 0) {
                return new ImportacaoResultado(0, 0, 0, 0, 0, 0, 0, System.currentTimeMillis() - inicioMs);
            }

            hnd = JapeSession.open();
            EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
            jdbc = dwf.getJdbcWrapper();
            BigDecimal proximoId = repository.buscarMaiorId(jdbc).add(BigDecimal.ONE);

            for (int i = 0; i < itens.size(); i++) {
                ConciliacaoResponse item = itens.get(i);

                try {
                    if (!registroValido(item, strategy)) {
                        invalidos++;
                        continue;
                    }

                    boolean existe = isDuplicado(item, strategy);
                    if (existe) {
                        duplicados++;
                        continue;
                    }

                    BigDecimal idPersistencia = resolverIdParaPersistencia(item);
                    boolean idGerado = false;
                    if (idPersistencia == null) {
                        idPersistencia = proximoId;
                        idGerado = true;
                    }

                    repository.inserir(item, idPersistencia);
                    inseridos++;

                    if (idGerado) {
                        idsAutoGerados++;
                        proximoId = idPersistencia.add(BigDecimal.ONE);
                    }
                } catch (Exception itemEx) {
                    falhas++;
                    LOG.log(Level.SEVERE, "[IMPORTACAO_ITEM_ERRO] idx=" + (i + 1) + " erro=" + itemEx.getMessage(), itemEx);
                }
            }

            if (vincularNotasAposImportacao && inseridos > 0) {
                notasVinculadas = repository.vincularNotasPendentes(jdbc);
            }

            return new ImportacaoResultado(
                    recebidos,
                    inseridos,
                    duplicados,
                    invalidos,
                    falhas,
                    idsAutoGerados,
                    notasVinculadas,
                    System.currentTimeMillis() - inicioMs
            );
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[IMPORTACAO_GERAL_ERRO] " + e.getMessage(), e);
            return new ImportacaoResultado(
                    recebidos,
                    inseridos,
                    duplicados,
                    invalidos,
                    falhas + 1,
                    idsAutoGerados,
                    notasVinculadas,
                    System.currentTimeMillis() - inicioMs
            );
        } finally {
            if (jdbc != null) {
                jdbc.closeSession();
            }
            JapeSession.close(hnd);
        }
    }

    public ImportacaoResultado importarMesAtualAgendado() {
        String dataIni = YearMonth.now().atDay(1).toString();
        String dataFim = LocalDate.now().toString();
        return importarPorPeriodo(
                System.getProperty("koncili.webhook.url", "https://hub.zaya.it/webhook/koncili_polyvox"),
                dataIni,
                dataFim,
                DuplicidadeStrategy.IDTRA_IDEX,
                true
        );
    }

    public ImportacaoResultado persistirConciliacoes(List<ConciliacaoResponse> conciliacoes, DuplicidadeStrategy strategy) {
        long inicioMs = System.currentTimeMillis();

        int recebidos = conciliacoes == null ? 0 : conciliacoes.size();
        int inseridos = 0;
        int duplicados = 0;
        int invalidos = 0;
        int falhas = 0;
        int idsAutoGerados = 0;

        if (conciliacoes == null || conciliacoes.isEmpty()) {
            return new ImportacaoResultado(0, 0, 0, 0, 0, 0, 0, System.currentTimeMillis() - inicioMs);
        }

        JapeSession.SessionHandle hnd = null;
        JdbcWrapper jdbc = null;

        try {
            hnd = JapeSession.open();
            EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
            jdbc = dwf.getJdbcWrapper();
            BigDecimal proximoId = repository.buscarMaiorId(jdbc).add(BigDecimal.ONE);

            for (int i = 0; i < conciliacoes.size(); i++) {
                ConciliacaoResponse item = conciliacoes.get(i);
                try {
                    if (!registroValido(item, strategy)) {
                        invalidos++;
                        continue;
                    }

                    if (isDuplicado(item, strategy)) {
                        duplicados++;
                        continue;
                    }

                    BigDecimal idPersistencia = resolverIdParaPersistencia(item);
                    boolean idGerado = false;
                    if (idPersistencia == null) {
                        idPersistencia = proximoId;
                        idGerado = true;
                    }

                    repository.inserir(item, idPersistencia);
                    inseridos++;

                    if (idGerado) {
                        idsAutoGerados++;
                        proximoId = idPersistencia.add(BigDecimal.ONE);
                    }
                } catch (Exception e) {
                    falhas++;
                    LOG.log(Level.SEVERE, "[PERSISTENCIA_ITEM_ERRO] idx=" + (i + 1) + " erro=" + e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            falhas++;
            LOG.log(Level.SEVERE, "[PERSISTENCIA_GERAL_ERRO] " + e.getMessage(), e);
        } finally {
            if (jdbc != null) {
                jdbc.closeSession();
            }
            JapeSession.close(hnd);
        }

        return new ImportacaoResultado(
                recebidos,
                inseridos,
                duplicados,
                invalidos,
                falhas,
                idsAutoGerados,
                0,
                System.currentTimeMillis() - inicioMs
        );
    }

    private boolean isDuplicado(ConciliacaoResponse item, DuplicidadeStrategy strategy) throws Exception {
        if (strategy == DuplicidadeStrategy.IDTRA_IDEX) {
            return repository.existsByIdTransacaoAndIdExtrato(item.id_transacao, item.id_extrato);
        }
        return repository.existsByIdTransacaoAndCodVenda(item.id_transacao, item.cod_venda);
    }

    private boolean registroValido(ConciliacaoResponse item, DuplicidadeStrategy strategy) {
        if (item == null || safe(item.id_transacao) == null || safe(item.cod_venda) == null) {
            return false;
        }
        if (strategy == DuplicidadeStrategy.IDTRA_IDEX) {
            return safe(item.id_extrato) != null;
        }
        return true;
    }

    private BigDecimal resolverIdParaPersistencia(ConciliacaoResponse item) {
        BigDecimal idRegistro = toBigDecimalInteiro(item.id_registro);
        if (idRegistro != null) {
            return idRegistro;
        }
        return toBigDecimalInteiro(item.id);
    }

    private static BigDecimal toBigDecimalInteiro(Object valor) {
        if (valor == null) {
            return null;
        }
        try {
            BigDecimal bd = new BigDecimal(valor.toString().trim());
            BigDecimal normalizado = bd.stripTrailingZeros();
            if (normalizado.scale() > 0) {
                return null;
            }
            return normalizado;
        } catch (Exception e) {
            return null;
        }
    }

    private static String safe(String valor) {
        if (valor == null) {
            return null;
        }
        String trim = valor.trim();
        return trim.isEmpty() ? null : trim;
    }
}


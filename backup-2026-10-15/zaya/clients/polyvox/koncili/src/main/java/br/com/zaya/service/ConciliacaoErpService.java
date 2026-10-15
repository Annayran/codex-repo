/**
 * FLUXO DE CONCILIAÇÃO KONCILI - SANKHYA ERP
 *
 * Orquestração completa de processamento de pendências de pagamento Koncili com integração Sankhya:
 *
 * SEQUÊNCIA DE PROCESSAMENTO (PROCESSAREPENDENCIA):
 * 1. VALIDACAO: Converte NUNOTA em BigDecimal, busca financeiro de receita (RECDESP=1) não baixado
 * 2. RATEIO: Cria despesa espelhada com distribuição percentual de comissão+frete+taxas por natureza
 *    - Insere registros em TGFRDET (RateioRecDesp) com percentuais por centro de resultado
 *    - Marca financeiro com RATEADO="S"
 * 3. COMPENSACAO: Usa CompensacaoFinanceiraHelper para offset receita vs. despesa
 *    - Cria NUDEV e NURENEG cruzados entre títulos
 *    - Reduz VLRDESDOB da receita pelo valor da compensação
 * 4. BAIXA: Executa settlement (BaixaHelper.baixar()) na receita conforme data Koncili
 *    - Popula DHBAIXA, VLRBAIXA, CODBCO
 *    - BaixaHelper cria automaticamente movimento bancário (TGFMBC) com NUBCO
 * 5. CONCILIACAO: Marca movimento bancário como CONCILIADO="S" com DHCONCILIACAO
 * 6. NOTIFICACAO: Envia webhook para Koncili confirmar settlement
 *
 * ESTRUTURA DE LOGS:
 * [KONCILI_PROC]: Processamento geral (INICIO, FIM, ETAPAS)
 * [KONCILI_VALID]: Validações e verificações de dados
 * [KONCILI_RATE]: Criação e alocação de rateios
 * [KONCILI_COMP]: Execução de compensação financeira
 * [KONCILI_BAIXA]: Execução de settlement/baixa do título
 * [KONCILI_CONC]: Reconciliação de movimentos bancários
 * [KONCILI_HOOK]: Notificação via webhook
 *
 * RASTREABILIDADE:
 * Todos os logs incluem:
 * - [id=IDPENDENCIA]: Identificação única para correlação
 * - [op=OPERACAO]: Operação específica executada
 * - dados operacionais: Valores, timestamps, e resultados intermediários
 *
 * TRATAMENTO DE ERROS:
 * - Try-catch com log detalhado em cada etapa crítica
 * - Validação idempotente para re-execuções seguras
 * - Registra motivo em enum EtapaProcessamento para rastreabilidade
 */
package br.com.zaya.service;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.mgefin.model.utils.CompensacaoFinanceiraHelper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.util.TipoOperacaoUtils;
import br.com.sankhya.modelcore.facades.BaixaFinanceiroSP;
import br.com.sankhya.modelcore.financeiro.helper.BaixaHelper;
import br.com.sankhya.modelcore.financeiro.util.DadosBaixa;
import br.com.sankhya.modelcore.financeiro.util.DadosCompensacao;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import br.com.sankhya.modelcore.util.ModelSessionManager;
import br.com.zaya.integration.KonciliWebhookClient;
import br.com.zaya.repository.ConciliacaoRepository;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConciliacaoErpService {
    private static final Logger LOG = Logger.getLogger(ConciliacaoErpService.class.getName());
    private static final String CNPJ_PADRAO = "04933508000446";
    private static final BigDecimal TOP_COMPENSACAO_DESPESA = BigDecimal.valueOf(1500L);
    private static final BigDecimal TOP_COMPENSACAO_RECEITA = BigDecimal.valueOf(1400L);

    // Prefixos padronizados para logs estruturados
    private static final String LOG_PROCESSAMENTO = "[KONCILI_PROC]";
    private static final String LOG_VALIDACAO = "[KONCILI_VALID]";
    private static final String LOG_RATEIO = "[KONCILI_RATE]";
    private static final String LOG_COMPENSACAO = "[KONCILI_COMP]";
    private static final String LOG_BAIXA = "[KONCILI_BAIXA]";
    private static final String LOG_CONCILIACAO = "[KONCILI_CONC]";
    private static final String LOG_WEBHOOK = "[KONCILI_HOOK]";

    private final ConciliacaoRepository conciliacaoRepository;
    private final KonciliWebhookClient webhookClient;

    public ConciliacaoErpService() {
        this.conciliacaoRepository = new ConciliacaoRepository();
        this.webhookClient = new KonciliWebhookClient();
    }

    /**
     * Formata mensagem de log estruturada com ID da pendência para rastreabilidade
     */
    private static String logMsg(String prefixo, BigDecimal idPendencia, String operacao, String dados) {
        return String.format("%s [id=%s] [op=%s] %s", prefixo, idPendencia, operacao, dados);
    }

    /**
     * Formata mensagem de log estruturada para operações sem ID
     */
    private static String logMsg(String prefixo, String operacao, String dados) {
        return String.format("%s [op=%s] %s", prefixo, operacao, dados);
    }

    public void executarConciliacaoPadrao() throws Exception {
        executarCompensacaoPadrao();
    }

    public CompensacaoResultado executarCompensacaoUnica(BigDecimal idPendencia) throws Exception {
        long inicioExec = System.currentTimeMillis();
        LOG.info(String.format("[KONCILI_EXECUCAO_UNICA_INICIO] idPendencia=%s", idPendencia));
        EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = dwFacade.getJdbcWrapper();
        CompensacaoResultado resultado = new CompensacaoResultado();

        try {
            ConciliacaoRepository.ConciliacaoPendente pendencia = conciliacaoRepository.buscarPendenciaPorId(jdbc, idPendencia);

            if (pendencia == null) {
                LOG.warning(String.format("[KONCILI_EXECUCAO_UNICA_NAO_ENCONTRADA] idPendencia=%s", idPendencia));
                resultado.setTotalPendencias(1);
                resultado.registrarFalha(idPendencia, "PENDENCIA_NAO_ENCONTRADA");
                return resultado;
            }

            resultado.setTotalPendencias(1);
            LOG.info(String.format("[KONCILI_EXECUCAO_UNICA_PROCESSANDO] %s", resumoPendencia(pendencia)));

            try {
                PendenciaResultado resultadoPendencia = processarPendencia(dwFacade, pendencia);
                if (resultadoPendencia.isSucesso()) {
                    resultado.registrarSucesso();
                } else {
                    resultado.registrarFalha(pendencia.getId(), resultadoPendencia.getMotivo());
                }
            } catch (Exception processEx) {
                resultado.registrarFalha(pendencia.getId(), processEx.getMessage());
                LOG.log(Level.SEVERE, String.format("[KONCILI_EXECUCAO_UNICA_ERRO] idPendencia=%s", idPendencia), processEx);
            }

        } finally {
            jdbc.closeSession();
            resultado.setDuracaoMs(System.currentTimeMillis() - inicioExec);
            LOG.info(String.format("[KONCILI_EXECUCAO_UNICA_FIM] idPendencia=%s duracao_ms=%s resultado=%s",
                    idPendencia, resultado.getDuracaoMs(), resultado.toMensagemRetorno()));
        }

        return resultado;
    }

    public void executarCompensacaoPadrao() throws Exception {
        CompensacaoResultado resultado = executarCompensacaoPadraoComResumo();
        if (resultado.getFalhas() > 0) {
            LOG.warning("[KONCILI_LOTE_RESULTADO] " + resultado.toMensagemRetorno());
        } else {
            LOG.info("[KONCILI_LOTE_RESULTADO] " + resultado.toMensagemRetorno());
        }
    }

    public CompensacaoResultado executarCompensacaoPadraoComResumo() throws Exception {
        long inicioLote = System.currentTimeMillis();
        LOG.info("[KONCILI_LOTE_INICIO] inicio do processamento padrao de compensacao");
        EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = dwFacade.getJdbcWrapper();
        CompensacaoResultado resultado = new CompensacaoResultado();

        try {
            List<ConciliacaoRepository.ConciliacaoPendente> pendencias = conciliacaoRepository.buscarPendentesCompensacao(jdbc, 10);
            resultado.setTotalPendencias(pendencias.size());
            LOG.info(String.format("[KONCILI_LOTE_PENDENCIAS] total=%s limite=%s", pendencias.size(), 10));
            if (pendencias.isEmpty()) {
                LOG.info("[KONCILI_LOTE_SEM_PENDENCIAS] nenhuma pendencia elegivel encontrada para conciliacao");
            }
            for (ConciliacaoRepository.ConciliacaoPendente pendencia : pendencias) {
                LOG.info(String.format("[KONCILI_PENDENCIA_INICIO] %s", resumoPendencia(pendencia)));
                try {
                    PendenciaResultado resultadoPendencia = processarPendencia(dwFacade, pendencia);
                    if (resultadoPendencia.isSucesso()) {
                        resultado.registrarSucesso();
                    } else {
                        resultado.registrarFalha(pendencia.getId(), resultadoPendencia.getMotivo());
                    }
                } catch (Exception processEx) {
                    resultado.registrarFalha(pendencia.getId(), processEx.getMessage());
                    LOG.log(Level.SEVERE, String.format("[KONCILI_PENDENCIA_ERRO] id=%s", pendencia.getId()), processEx);
                }
                LOG.info(String.format("[KONCILI_PENDENCIA_FIM] %s", resumoPendencia(pendencia)));
            }
        } finally {
            jdbc.closeSession();
            resultado.setDuracaoMs(System.currentTimeMillis() - inicioLote);
            LOG.info(String.format("[KONCILI_LOTE_FIM] duracao_ms=%s", resultado.getDuracaoMs()));
        }

        return resultado;
    }

    private PendenciaResultado processarPendencia(EntityFacade dwFacade, ConciliacaoRepository.ConciliacaoPendente pendencia) throws Exception {
        long inicioProcessamento = System.currentTimeMillis();
        LOG.info(logMsg(LOG_PROCESSAMENTO, pendencia.getId(), "INICIO",
                String.format("nunota=%s codvend=%s idtra=%s vlrvend=%s vlrcom=%s vlrfrete=%s vlrtx=%s",
                        pendencia.getNuNota(), pendencia.getCodVenda(), pendencia.getIdTransacao(),
                        pendencia.getValorVenda(), pendencia.getValorComissao(), pendencia.getValorFrete(), pendencia.getValorTaxas())));

        BigDecimal nuNota;
        try {
            nuNota = new BigDecimal(pendencia.getNuNota());
        } catch (Exception e) {
            LOG.warning(logMsg(LOG_VALIDACAO, pendencia.getId(), "NUNOTA_INVALIDO", "valor='" + pendencia.getNuNota() + "'"));
            try {
                conciliacaoRepository.marcarEtapaProcessamento(pendencia.getId(), ConciliacaoRepository.EtapaProcessamento.ERRO, null, null);
            } catch (Exception erroStatus) {
                LOG.log(Level.WARNING, logMsg(LOG_PROCESSAMENTO, pendencia.getId(), "FALHA_REGISTRO_STATUS", ""), erroStatus);
            }
            return PendenciaResultado.erro("NUNOTA_INVALIDO");
        }

        LOG.info(logMsg(LOG_VALIDACAO, pendencia.getId(), "NUNOTA_OK", "nunota=" + nuNota));

        JapeWrapper financeiroDao = JapeFactory.dao("Financeiro");
        Collection<DynamicVO> financeiros = financeiroDao.find("this.NUNOTA = ? AND this.RECDESP = ?", nuNota, BigDecimal.ONE);
        LOG.info(logMsg(LOG_VALIDACAO, pendencia.getId(), "BUSCA_FINANCEIRO", "encontrados=" + financeiros.size()));

        if (financeiros.isEmpty()) {
            LOG.warning(logMsg(LOG_VALIDACAO, pendencia.getId(), "FINANCEIRO_AUSENTE", "nunota=" + nuNota));
            try {
                conciliacaoRepository.marcarEtapaProcessamento(pendencia.getId(), ConciliacaoRepository.EtapaProcessamento.ERRO, null, null);
            } catch (Exception erroStatus) {
                LOG.log(Level.WARNING, logMsg(LOG_PROCESSAMENTO, pendencia.getId(), "FALHA_REGISTRO_STATUS", ""), erroStatus);
            }
            return PendenciaResultado.erro("FINANCEIRO_NAO_ENCONTRADO");
        }

        boolean processouReceitaElegivel = false;
        boolean compensouComSucesso = false;
        String ultimoMotivoErro = null;

        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();

            for (DynamicVO financeiro : financeiros) {
                if (isFinanceiroBaixado(financeiro)) {
                    LOG.info(logMsg(LOG_VALIDACAO, pendencia.getId(), "FINANCEIRO_JA_BAIXADO",
                            "nufin=" + financeiro.asBigDecimal("NUFIN")));
                    continue;
                }

                processouReceitaElegivel = true;
                BigDecimal nuFinReceita = financeiro.asBigDecimal("NUFIN");
                try {
                    LOG.info(logMsg(LOG_PROCESSAMENTO, pendencia.getId(), "RECEITA_PROCESSANDO",
                            String.format("nufin=%s valor=%s cencus=%s",
                                    nuFinReceita,
                                    financeiro.asBigDecimal("VLRDESDOB"),
                                    financeiro.asBigDecimal("CODCENCUS"))));

                    List<BigDecimal> nuFinsDespesa = criarDespesasRateadasComEspelho(dwFacade, financeiro, pendencia);
                    if (nuFinsDespesa.isEmpty()) {
                        LOG.warning(logMsg(LOG_RATEIO, pendencia.getId(), "DESPESA_VAZIA", "nufin_receita=" + nuFinReceita));
                        throw new Exception("RATEIO_SEM_DESPESA");
                    }

                    LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "DESPESA_CRIADA",
                            String.format("qtd=%s primeira=%s", nuFinsDespesa.size(), nuFinsDespesa.get(0))));

                    conciliacaoRepository.marcarEtapaProcessamento(pendencia.getId(), ConciliacaoRepository.EtapaProcessamento.RATEADO, nuFinReceita, nuFinsDespesa.get(0));

                    BigDecimal totalTaxas = totalTaxasRateio(pendencia);
                    BigDecimal valorAcerto = totalTaxas;

                    LOG.info(logMsg(LOG_COMPENSACAO, pendencia.getId(), "VALORES_ACERTO",
                            String.format("total_taxas=%s valor_acerto=%s valor_venda_ref=%s",
                                    totalTaxas, valorAcerto, pendencia.getValorVenda())));

                    if (valorAcerto.signum() <= 0) {
                        LOG.warning(logMsg(LOG_VALIDACAO, pendencia.getId(), "ACERTO_INVALIDO",
                                String.format("vlrcom=%s vlrfrete=%s vlrtx=%s",
                                        pendencia.getValorComissao(),
                                        pendencia.getValorFrete(),
                                        pendencia.getValorTaxas())));
                        throw new Exception("VALOR_ACERTO_INVALIDO");
                    }

                    compensarTitulos(nuFinReceita, nuFinsDespesa, valorAcerto);
                    conciliacaoRepository.marcarEtapaProcessamento(pendencia.getId(), ConciliacaoRepository.EtapaProcessamento.COMPENSADO, nuFinReceita, nuFinsDespesa.get(0));

                    // Baixa o titulo original de receita apos a compensacao.
                    baixarTituloReceitaConformeKoncili(financeiro, pendencia);
                    conciliacaoRepository.marcarEtapaProcessamento(pendencia.getId(), ConciliacaoRepository.EtapaProcessamento.BAIXADO, nuFinReceita, nuFinsDespesa.get(0));

                    conciliacaoRepository.marcarComoConciliado(pendencia.getId(), nuFinsDespesa.get(0));
                    notificarWebhookSePossivel(pendencia.getIdTransacao(), pendencia.getId(), nuNota, pendencia.getCodVenda());
                    LOG.info(logMsg(LOG_PROCESSAMENTO, pendencia.getId(), "FIM",
                            String.format("nufin_receita=%s nufin_despesa=%s duracao_ms=%s",
                                    nuFinReceita, nuFinsDespesa.get(0), System.currentTimeMillis() - inicioProcessamento)));
                    compensouComSucesso = true;
                    break;

                } catch (Exception processEx) {
                    LOG.log(Level.SEVERE, logMsg(LOG_PROCESSAMENTO, pendencia.getId(), "ERRO",
                            "nufin_receita=" + nuFinReceita + " erro=" + processEx.getMessage()), processEx);
                    throw processEx;
                }
            }
        } finally {
            JapeSession.close(hnd);
        }

        if (!processouReceitaElegivel) {
            LOG.warning(logMsg(LOG_VALIDACAO, pendencia.getId(), "SEM_RECEITA_ELEGIVEL", ""));
            try {
                conciliacaoRepository.marcarEtapaProcessamento(pendencia.getId(), ConciliacaoRepository.EtapaProcessamento.ERRO, null, null);
            } catch (Exception erroStatus) {
                LOG.log(Level.WARNING, logMsg(LOG_PROCESSAMENTO, pendencia.getId(), "FALHA_REGISTRO_STATUS", ""), erroStatus);
            }
            return PendenciaResultado.erro("SEM_RECEITA_ELEGIVEL");
        }

        if (!compensouComSucesso) {
            return PendenciaResultado.erro(ultimoMotivoErro == null ? "SEM_COMPENSACAO" : ultimoMotivoErro);
        }

        return PendenciaResultado.sucesso();
    }

    private static class PendenciaResultado {
        private final boolean sucesso;
        private final String motivo;

        private PendenciaResultado(boolean sucesso, String motivo) {
            this.sucesso = sucesso;
            this.motivo = motivo;
        }

        static PendenciaResultado sucesso() {
            return new PendenciaResultado(true, null);
        }

        static PendenciaResultado erro(String motivo) {
            return new PendenciaResultado(false, motivo);
        }

        boolean isSucesso() {
            return sucesso;
        }

        String getMotivo() {
            return motivo;
        }
    }

    public static class CompensacaoResultado {
        private int totalPendencias;
        private int sucessos;
        private int falhas;
        private long duracaoMs;
        private final List<String> detalhesFalha = new ArrayList<String>();

        void setTotalPendencias(int totalPendencias) {
            this.totalPendencias = totalPendencias;
        }

        void registrarSucesso() {
            this.sucessos++;
        }

        void registrarFalha(BigDecimal idPendencia, String motivo) {
            this.falhas++;
            detalhesFalha.add(String.format("id=%s motivo=%s", String.valueOf(idPendencia), String.valueOf(motivo)));
        }

        void setDuracaoMs(long duracaoMs) {
            this.duracaoMs = duracaoMs;
        }

        public int getTotalPendencias() {
            return totalPendencias;
        }

        public int getSucessos() {
            return sucessos;
        }

        public int getFalhas() {
            return falhas;
        }

        public long getDuracaoMs() {
            return duracaoMs;
        }

        public String toMensagemRetorno() {
            String base = String.format("Compensacao ERP finalizada. total=%d sucesso=%d falhas=%d duracaoMs=%d",
                    totalPendencias,
                    sucessos,
                    falhas,
                    duracaoMs);

            if (falhas <= 0) {
                return base;
            }

            StringBuilder detalhes = new StringBuilder();
            int limite = Math.min(5, detalhesFalha.size());
            for (int i = 0; i < limite; i++) {
                if (i > 0) {
                    detalhes.append(" | ");
                }
                detalhes.append(detalhesFalha.get(i));
            }

            if (detalhesFalha.size() > limite) {
                detalhes.append(" | ...");
            }

            return base + " detalhes=" + detalhes;
        }
    }

    private List<BigDecimal> criarDespesasRateadasComEspelho(
            EntityFacade dwFacade,
            DynamicVO financeiro,
            ConciliacaoRepository.ConciliacaoPendente pendencia
    ) throws Exception {
        List<BigDecimal> nuFinsCriados = new ArrayList<BigDecimal>();
        Map<String, BigDecimal> rateio = montarRateioTaxas(pendencia);
        BigDecimal topDespesa = parametroBigDecimal("AD_TOPFINMP", new BigDecimal(1400L));
        BigDecimal codCom = parametroBigDecimal("AD_CODCOMTFMP", null);
        BigDecimal codNatFrete = parametroBigDecimal("AD_CODNATFTMP", null);
        BigDecimal codNatTarifa = parametroBigDecimal("AD_CODNATTFMP", null);

        LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "INICIO",
                String.format("nunota=%s comissao=%s frete=%s taxas=%s top=%s",
                        pendencia.getNuNota(), pendencia.getValorComissao(),
                        pendencia.getValorFrete(), pendencia.getValorTaxas(), topDespesa)));

        // Validar Centro de Resultado
        DynamicVO centroResultadoVO = JapeFactory.dao("CentroResultado")
                .findByPK(BigDecimalUtil.getValueOrZero(financeiro.asBigDecimal("CODCENCUS")));

        if (centroResultadoVO == null) {
            LOG.warning(logMsg(LOG_RATEIO, pendencia.getId(), "CENTRO_RESULTADO_AUSENTE",
                    "codcencus=" + financeiro.asBigDecimal("CODCENCUS")));
            throw new Exception("CENTRO_RESULTADO_NAO_ENCONTRADO");
        }

        BigDecimal codCta = BigDecimalUtil.getValueOrZero(centroResultadoVO.asBigDecimal("AD_CODCTABCOINT"));
        BigDecimal codNat = BigDecimalUtil.getValueOrZero(centroResultadoVO.asBigDecimal("AD_CODNAT"));
        if (codCta == null || codCta.signum() <= 0) {
            LOG.warning(logMsg(LOG_RATEIO, pendencia.getId(), "CONTA_BANCARIA_AUSENTE",
                    "codcencus=" + financeiro.asBigDecimal("CODCENCUS")));
            throw new Exception("CONTA_BANCARIA_NAO_CONFIGURADA");
        }

        if (codNat == null || codNat.signum() <= 0) {
            LOG.warning(logMsg(LOG_RATEIO, pendencia.getId(), "NATUREZA_AUSENTE",
                    "codcencus=" + financeiro.asBigDecimal("CODCENCUS")));
            throw new Exception("NATUREZA_NAO_CONFIGURADA");
        }

        DynamicVO contaBancariaVO = JapeFactory.dao("ContaBancaria").findByPK(codCta);
        if (contaBancariaVO == null) {
            LOG.warning(logMsg(LOG_RATEIO, pendencia.getId(), "CONTA_BANCARIA_INVALIDA",
                    "codctabcoint=" + codCta));
            throw new Exception("CONTA_BANCARIA_INVALIDA");
        }

        BigDecimal codBco = BigDecimalUtil.getValueOrZero(contaBancariaVO.asBigDecimal("CODBCO"));
        BigDecimal codCencusRateio = BigDecimalUtil.getValueOrZero(financeiro.asBigDecimal("CODCENCUS"));

        LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "PARAMETROS_OK",
                String.format("codcencus=%s codctabcoint=%s codbco=%s codnat=%s topdesp=%s codcom=%s codfrete=%s codtax=%s",
                        codCencusRateio, codCta, codBco, codNat, topDespesa, codCom, codNatFrete, codNatTarifa)));

        BigDecimal valorTotalRateio = totalTaxasRateio(pendencia);
        if (valorTotalRateio == null || valorTotalRateio.signum() <= 0) {
            LOG.warning(logMsg(LOG_RATEIO, pendencia.getId(), "VALOR_TOTAL_INVALIDO",
                    "total=" + valorTotalRateio));
            return nuFinsCriados;
        }

        LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "VALOR_TOTAL_OK",
                "total=" + valorTotalRateio));

        // Cria um unico financeiro de despesa com a soma das taxas
        DynamicVO novoFinVO = financeiro.buildClone();
        novoFinVO.setPrimaryKey(null);
        novoFinVO.clearReferences();
        novoFinVO.setProperty("CODTIPOPER", topDespesa);
        novoFinVO.setProperty("DHTIPOPER", TipoOperacaoUtils.getTopVO(topDespesa).asTimestamp("DHALTER"));
        novoFinVO.setProperty("RECDESP", BigDecimal.ONE.negate());
        novoFinVO.setProperty("VLRDESDOB", valorTotalRateio);
        novoFinVO.setProperty("CODCTABCOINT", codCta);
        novoFinVO.setProperty("CODBCO", codBco);
        novoFinVO.setProperty("CODNAT", codNat);
        novoFinVO.setProperty("NUFIN", null);
        novoFinVO.setProperty("HISTORICO", "KONCILI DESPESA TOTAL TAXAS");

        LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "CRIAR_FINANCEIRO",
                String.format("top=%s recdesp=%s valor=%s", topDespesa, BigDecimal.ONE.negate(), valorTotalRateio)));

        dwFacade.createEntity("Financeiro", (EntityVO) novoFinVO);
        BigDecimal nuFinDespesa = novoFinVO.asBigDecimal("NUFIN");
        nuFinsCriados.add(nuFinDespesa);

        // Garante o marcador de rateio no financeiro de despesa criado.
        JapeFactory.dao("Financeiro")
                .prepareToUpdateByPK(nuFinDespesa)
                .set("RATEADO", "S")
                .update();

        LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "FINANCEIRO_CRIADO",
                String.format("nufin=%s valor=%s rateado=S", nuFinDespesa, valorTotalRateio)));

        // Insere o percentual de rateio para cada componente positivo no mesmo NUFIN
        Map<BigDecimal, BigDecimal> percentualPorNat = new LinkedHashMap<BigDecimal, BigDecimal>();
        Map<BigDecimal, StringBuilder> componentesPorNat = new LinkedHashMap<BigDecimal, StringBuilder>();
        for (Map.Entry<String, BigDecimal> componente : rateio.entrySet()) {
            BigDecimal valorComponente = componente.getValue();
            if (valorComponente == null || valorComponente.signum() <= 0) {
                LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "COMPONENTE_SKIP",
                        "tipo=" + componente.getKey() + " valor=" + valorComponente));
                continue;
            }

            BigDecimal codNatRateio = resolverCodNatRateio(componente.getKey(), codCom, codNatFrete, codNatTarifa, codNat);
            BigDecimal percentual = calcularPercentualRateio(valorComponente, valorTotalRateio);

            BigDecimal percentualAcumulado = percentualPorNat.get(codNatRateio);
            if (percentualAcumulado == null) {
                percentualAcumulado = BigDecimal.ZERO;
            }
            percentualPorNat.put(codNatRateio, percentualAcumulado.add(percentual));

            StringBuilder componentes = componentesPorNat.get(codNatRateio);
            if (componentes == null) {
                componentes = new StringBuilder();
                componentesPorNat.put(codNatRateio, componentes);
            }
            if (componentes.length() > 0) {
                componentes.append(",");
            }
            componentes.append(componente.getKey());

            LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "PERCENTUAL_CALCULO",
                    String.format("tipo=%s valor=%s percentual=%s codnat=%s",
                            componente.getKey(), valorComponente, percentual, codNatRateio)));
        }

        LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "PERCENTUAIS_COMPILADOS",
                String.format("naturezas=%s", percentualPorNat.keySet())));

        for (Map.Entry<BigDecimal, BigDecimal> entry : percentualPorNat.entrySet()) {
            BigDecimal codNatRateio = entry.getKey();
            BigDecimal percentualFinal = entry.getValue();

            LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "INSERIR_RATEIO",
                    String.format("nufin=%s codnat=%s percentual=%s componentes=%s",
                            nuFinDespesa, codNatRateio, percentualFinal, componentesPorNat.get(codNatRateio))));

            inserirRateio(nuFinDespesa, codNatRateio, percentualFinal, codCencusRateio);
        }

        LOG.info(logMsg(LOG_RATEIO, pendencia.getId(), "FIM",
                String.format("nufins_criados=%s primeiro_nufin=%s", nuFinsCriados.size(), nuFinDespesa)));

        if (nuFinsCriados.isEmpty()) {
            LOG.warning(logMsg(LOG_RATEIO, pendencia.getId(), "RESULTADO_VAZIO", "nenhum nufin foi criado"));
        }

        return nuFinsCriados;
    }

    private static BigDecimal calcularPercentualRateio(BigDecimal valorComponente, BigDecimal valorTotal) {
        if (valorComponente == null || valorTotal == null || valorTotal.signum() == 0) {
            return BigDecimal.ZERO;
        }
        return valorComponente
                .divide(valorTotal, 5, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private static BigDecimal resolverCodNatRateio(String componente, BigDecimal codCom, BigDecimal codNatFrete, BigDecimal codNatTarifa, BigDecimal fallback) {
        if ("COMISSAO".equalsIgnoreCase(componente) && codCom != null && codCom.signum() > 0) {
            return codCom;
        }
        if ("FRETE".equalsIgnoreCase(componente) && codNatFrete != null && codNatFrete.signum() > 0) {
            return codNatFrete;
        }
        if (codNatTarifa != null && codNatTarifa.signum() > 0) {
            return codNatTarifa;
        }
        return fallback;
    }

    private static BigDecimal parametroBigDecimal(String nome, BigDecimal padrao) {
        try {
            Object valor = MGECoreParameter.getParameter(nome);
            if (valor == null) {
                return padrao;
            }
            if (valor instanceof BigDecimal) {
                return (BigDecimal) valor;
            }
            return new BigDecimal(valor.toString().trim());
        } catch (Exception e) {
            return padrao;
        }
    }

    private static Timestamp buscarDhAlterTopAtiva(BigDecimal codTop) throws Exception {
        if (codTop == null || codTop.signum() <= 0) {
            return null;
        }
        EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = dwf.getJdbcWrapper();
        NativeSql sql = null;
        java.sql.ResultSet rs = null;
        try {
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT MAX(T.DHALTER) AS DHALTER FROM TGFTOP T WHERE T.CODTIPOPER = :CODTOP");
            sql.setNamedParameter("CODTOP", codTop);
            rs = sql.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("DHALTER");
            }
            return null;
        } finally {
            com.sankhya.util.JdbcUtils.closeResultSet(rs);
            NativeSql.releaseResources(sql);
        }
    }

    private static void inserirRateio(BigDecimal nuFin, BigDecimal codNat, BigDecimal percRateio, BigDecimal codCencus) throws Exception {
        if (percRateio == null || percRateio.signum() <= 0) {
            LOG.info(logMsg(LOG_RATEIO, "INSERCAO_SKIP",
                    String.format("nufin=%s codnat=%s percentual=%s (invalido)", nuFin, codNat, percRateio)));
            return;
        }

        BigDecimal codUsu = BigDecimal.ZERO;
        Object usuarioLogado = JapeSessionContext.getProperty("usuario_logado");
        if (usuarioLogado instanceof BigDecimal) {
            codUsu = (BigDecimal) usuarioLogado;
        }

        LOG.info(logMsg(LOG_RATEIO, "INSERCAO_EM_TGFRDET",
                String.format("nufin=%s codnat=%s percentual=%s codcencus=%s codusu=%s",
                        nuFin, codNat, percRateio, codCencus, codUsu)));

        try {
            JapeFactory.dao("RateioRecDesp").create()
                    .set("ORIGEM", "F")
                    .set("NUFIN", nuFin)
                    .set("CODNAT", codNat)
                    .set("CODCENCUS", codCencus)
                    .set("PERCRATEIO", percRateio)
                    .set("CODUSU", codUsu)
                    .set("DTALTER", new Timestamp(System.currentTimeMillis()))
                    .set("CODCTACTB", BigDecimal.ZERO)
                    .save();

            LOG.info(logMsg(LOG_RATEIO, "INSERCAO_OK",
                    String.format("nufin=%s codnat=%s percentual=%s", nuFin, codNat, percRateio)));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, logMsg(LOG_RATEIO, "INSERCAO_ERRO",
                    String.format("nufin=%s codnat=%s percentual=%s erro=%s",
                            nuFin, codNat, percRateio, e.getMessage())), e);
            throw e;
        }
    }

    private void notificarWebhookSePossivel(String idTransacao, BigDecimal idKncl, BigDecimal nuNota, String codVenda) {
        if (idTransacao == null || idTransacao.trim().isEmpty()) {
            LOG.warning(logMsg(LOG_WEBHOOK, idKncl, "SKIP",
                    String.format("nunota=%s codvend=%s motivo=idtra_vazio", nuNota, codVenda)));
            return;
        }

        try {
            LOG.info(logMsg(LOG_WEBHOOK, idKncl, "ENVIANDO",
                    String.format("idtra=%s nunota=%s cnpj=%s", idTransacao, nuNota, CNPJ_PADRAO)));

            webhookClient.notificarConciliacaoResolvida(CNPJ_PADRAO, Integer.parseInt(idTransacao));

            LOG.info(logMsg(LOG_WEBHOOK, idKncl, "OK", String.format("idtra=%s", idTransacao)));
        } catch (Exception e) {
            LOG.log(Level.WARNING, logMsg(LOG_WEBHOOK, idKncl, "ERRO",
                    String.format("idtra=%s erro=%s", idTransacao, e.getMessage())), e);
        }
    }

    private static void compensarTitulos(BigDecimal nufinRec, List<BigDecimal> nufinsDespesa, BigDecimal vlrAcerto) throws Exception {
        if (nufinRec == null || nufinsDespesa == null || nufinsDespesa.isEmpty() || vlrAcerto == null || vlrAcerto.signum() <= 0) {
            throw new Exception(String.format("[KONCILI_COMPENSACAO_ERRO] nufin_receita=%s nufins_despesa=%s vlr_acerto=%s motivo=parametros_invalidos",
                    nufinRec, nufinsDespesa, vlrAcerto));
        }

        LOG.info(logMsg(LOG_COMPENSACAO, "INICIO",
                String.format("nufin_receita=%s nufins_despesa_qtd=%s vlr_acerto=%s",
                        nufinRec, nufinsDespesa.size(), vlrAcerto)));

        Collection<BigDecimal> despesasCol = new ArrayList<BigDecimal>();
        for (BigDecimal nufinDespesa : nufinsDespesa) {
            if (nufinDespesa != null) {
                LOG.info(logMsg(LOG_COMPENSACAO, "VALIDAR_DESPESA", "nufin=" + nufinDespesa));
                despesasCol.add(nufinDespesa);
            }
        }
        if (despesasCol.isEmpty()) {
            throw new Exception(String.format("[KONCILI_COMPENSACAO_ERRO] nufin_receita=%s motivo=sem_titulos_despesa_validos",
                    nufinRec));
        }

        LOG.info(logMsg(LOG_COMPENSACAO, "PARAMETROS_MONTADOS",
                String.format("receita=%s despesas_validas=%s", nufinRec, despesasCol.size())));

        Collection<BigDecimal> receitasCol = new ArrayList<BigDecimal>();
        receitasCol.add(nufinRec);
        AuthenticationInfo authInfo = new AuthenticationInfo("SUP", BigDecimal.ZERO, BigDecimal.ZERO, 0);
        DadosCompensacao dadosCompensacao = new DadosCompensacao();
        dadosCompensacao.setChave("br.com.sankhya.mgefin.mov.CompensacaoFinanceira_prefs");
        dadosCompensacao.setCompensaBolRapido(true);
        dadosCompensacao.setDtBaixa(new Timestamp(System.currentTimeMillis()));
        dadosCompensacao.setTipo("T");
        dadosCompensacao.setTitulosDesp(despesasCol);
        dadosCompensacao.setTitulosRec(receitasCol);
        dadosCompensacao.setUsarDtVencBaixa(false);
        dadosCompensacao.setUserID(authInfo.getUserID());
        dadosCompensacao.setValorAtualMoeda(BigDecimal.ZERO);
        dadosCompensacao.setVlrAcerto(vlrAcerto);
        dadosCompensacao.setTopDesp(TOP_COMPENSACAO_DESPESA);
        dadosCompensacao.setTopRec(TOP_COMPENSACAO_RECEITA);

        LOG.info(logMsg(LOG_COMPENSACAO, "EXECUTAR",
                String.format("topdesp=%s toprec=%s vlracerto=%s",
                        TOP_COMPENSACAO_DESPESA, TOP_COMPENSACAO_RECEITA, vlrAcerto)));

        JapeSession.putProperty("br.com.sankhya.fin.CompensacaoFinanceira", true);
        CompensacaoFinanceiraHelper.acertar(dadosCompensacao);

        LOG.info(logMsg(LOG_COMPENSACAO, "FIM_OK",
                String.format("nufin_receita=%s nufins_despesa=%s", nufinRec, despesasCol)));
    }

    private void baixarTituloReceitaConformeKoncili(
            DynamicVO financeiroReceita,
            ConciliacaoRepository.ConciliacaoPendente pendencia
    ) throws Exception {
        BigDecimal nuFinReceita = financeiroReceita.asBigDecimal("NUFIN");
        LOG.info(logMsg(LOG_BAIXA, pendencia.getId(), "INICIO", "nufin=" + nuFinReceita));

        // Recarrega para evitar baixa duplicada por VO desatualizado.
        DynamicVO financeiroAtualizado = JapeFactory.dao("Financeiro").findByPK(nuFinReceita);
        if (financeiroAtualizado != null) {
            LOG.info(logMsg(LOG_BAIXA, pendencia.getId(), "RECARREGAR_VO", "nufin=" + nuFinReceita));
            financeiroReceita = financeiroAtualizado;
        }

        if (isFinanceiroBaixado(financeiroReceita)) {
            LOG.info(logMsg(LOG_BAIXA, pendencia.getId(), "JA_BAIXADO", "nufin=" + nuFinReceita));
            return;
        }

        BigDecimal codContaBaixa = buscarContaBancariaCentroResultado(financeiroReceita);
        if (codContaBaixa == null || codContaBaixa.signum() <= 0) {
            String erro = "Conta bancaria nao configurada no Centro de Resultado";
            LOG.warning(logMsg(LOG_BAIXA, pendencia.getId(), "CONTA_AUSENTE", "nufin=" + nuFinReceita + " erro=" + erro));
            throw new Exception(erro);
        }

        BigDecimal valorBaixa = resolverValorBaixa(financeiroReceita);
        Timestamp dataBaixa = resolverDataBaixa(pendencia);

        LOG.info(logMsg(LOG_BAIXA, pendencia.getId(), "PARAMETROS",
                String.format("nufin=%s codconta=%s data=%s valor=%s",
                        nuFinReceita, codContaBaixa, dataBaixa, valorBaixa)));

        try {
            baixarNuFin(nuFinReceita, codContaBaixa, dataBaixa, valorBaixa, pendencia.getId());
            LOG.info(logMsg(LOG_BAIXA, pendencia.getId(), "FIM_OK",
                    String.format("nufin=%s", nuFinReceita)));
        } catch (Exception e) {
            if (isErroTituloJaBaixado(e)) {
                DynamicVO finValidacao = JapeFactory.dao("Financeiro").findByPK(nuFinReceita);
                if (finValidacao != null && isFinanceiroBaixado(finValidacao)) {
                    LOG.info(logMsg(LOG_BAIXA, pendencia.getId(), "IDEMPOTENTE_OK", "nufin=" + nuFinReceita));
                    return;
                }
            }
            LOG.log(Level.SEVERE, logMsg(LOG_BAIXA, pendencia.getId(), "ERRO", "nufin=" + nuFinReceita + " erro=" + e.getMessage()), e);
            throw e;
        }
    }

    private static boolean isErroTituloJaBaixado(Exception e) {
        Throwable atual = e;
        while (atual != null) {
            String msg = atual.getMessage();
            if (msg != null) {
                String lower = msg.toLowerCase();
                if (lower.contains("titulo ja baixado") || lower.contains("título já baixado")) {
                    return true;
                }
            }
            atual = atual.getCause();
        }
        return false;
    }

    private BigDecimal buscarContaBancariaCentroResultado(DynamicVO financeiro) throws Exception {
        BigDecimal codCencus = BigDecimalUtil.getValueOrZero(financeiro.asBigDecimal("CODCENCUS"));
        DynamicVO centroResultadoVO = JapeFactory.dao("CentroResultado").findByPK(codCencus);
        if (centroResultadoVO == null) {
            LOG.warning(logMsg(LOG_BAIXA, "BUSCA_CENTRO_RESULTADO",
                    String.format("codcencus=%s nao encontrado", codCencus)));
            return null;
        }

        BigDecimal codCtaBcoint = centroResultadoVO.asBigDecimal("AD_CODCTABCOINT");
        BigDecimal codNat = centroResultadoVO.asBigDecimal("AD_CODNAT");

        LOG.info(logMsg(LOG_BAIXA, "CENTRO_RESULTADO_OK",
                String.format("codcencus=%s codctabcoint=%s codnat=%s", codCencus, codCtaBcoint, codNat)));

        return BigDecimalUtil.getValueOrZero(codCtaBcoint);
    }

    private static Timestamp resolverDataBaixa(ConciliacaoRepository.ConciliacaoPendente pendencia) {
        Timestamp agora = new Timestamp(System.currentTimeMillis());
        LOG.info(logMsg(LOG_BAIXA, "DATA_RESOLUCAO",
                String.format("id=%s origem=HOJE valor=%s", pendencia.getId(), agora)));
        return agora;
    }

    private static BigDecimal resolverValorBaixa(DynamicVO financeiroReceita) {
        BigDecimal valorAtual = nvl(financeiroReceita.asBigDecimal("VLRDESDOB"));
        LOG.info(logMsg(LOG_BAIXA, "VALOR_RESOLUCAO",
                String.format("origem=VLRDESDOB_APOS_COMPENSACAO valor=%s", valorAtual)));
        return valorAtual;
    }

    private static void baixarNuFin(BigDecimal nuFin, BigDecimal codContaBaixa, Timestamp dataBaixa, BigDecimal valorBaixa, BigDecimal idPendencia) throws Exception {
        LOG.info(logMsg(LOG_BAIXA, idPendencia, "VALIDACAO_ENTIDADES",
                String.format("nufin=%s codconta=%s", nuFin, codContaBaixa)));

        EntityFacade ewf = EntityFacadeFactory.getDWFFacade();
        DynamicVO finVO = (DynamicVO) ewf.findEntityByPrimaryKeyAsVO("Financeiro", nuFin);
        DynamicVO contaVO = (DynamicVO) ewf.findEntityByPrimaryKeyAsVO("ContaBancaria", codContaBaixa);

        if (finVO == null) {
            throw new Exception(String.format("Financeiro nao encontrado: nufin=%s", nuFin));
        }
        if (contaVO == null) {
            throw new Exception(String.format("Conta bancaria nao encontrada: nufin=%s codconta=%s", nuFin, codContaBaixa));
        }

        LOG.info(logMsg(LOG_BAIXA, idPendencia, "ENTIDADES_OK",
                String.format("nufin=%s codconta=%s codbco=%s", nuFin, codContaBaixa, contaVO.asBigDecimal("CODBCO"))));

        BigDecimal topBaixa = BigDecimal.valueOf(1400L);
        LOG.info(logMsg(LOG_BAIXA, idPendencia, "PREPARAR_BAIXA",
                String.format("nufin=%s top=%s data=%s valor=%s", nuFin, topBaixa, dataBaixa, valorBaixa)));

        // Buscar DHALTER da TOP na TGFTOP
        Timestamp dhAlterTop = buscarDhAlterTopAtiva(topBaixa);
        if (dhAlterTop == null) {
            LOG.warning(logMsg(LOG_BAIXA, idPendencia, "DHALTER_TOP_NULO", "codtop=" + topBaixa));
            dhAlterTop = new Timestamp(System.currentTimeMillis());
        }

        LOG.info(logMsg(LOG_BAIXA, idPendencia, "PARAMETROS_TOP_BAIXA",
                String.format("nufin=%s codtipoperbaixa=%s dhtipoperbaixa=%s", nuFin, topBaixa, dhAlterTop)));


        // Buscar operador
        DynamicVO usuVO = (DynamicVO) ewf.findEntityByPrimaryKeyAsVO("Usuario", contaVO.asBigDecimal("CODOPEREXCL"));
        if (usuVO == null) {
            throw new Exception(String.format("Operador nao encontrado: nufin=%s codconta=%s", nuFin, codContaBaixa));
        }

        LOG.info(logMsg(LOG_BAIXA, idPendencia, "OPERADOR_OK",
                String.format("nomeusu=%s codusu=%s codemp=%s", usuVO.asString("NOMEUSU"), usuVO.asBigDecimal("CODUSU"), usuVO.asBigDecimal("CODEMP"))));

        // Configurar contexto de autenticação
        AuthenticationInfo authInfo = new AuthenticationInfo(usuVO.asString("NOMEUSU"), usuVO.asBigDecimal("CODUSU"), usuVO.asBigDecimal("CODUSU"), 0);
        authInfo.makeCurrent();

        LOG.info(logMsg(LOG_BAIXA, idPendencia, "SESSAO_CONTEXTO",
                String.format("usuario=%s grupo=%s empresa=%s",
                        authInfo.getUserID(), authInfo.getGroupID(), usuVO.asBigDecimal("CODEMP"))));

        JapeSessionContext.putProperty("usuario_logado", authInfo.getUserID());
        JapeSessionContext.putProperty("grupo_usu_logado", authInfo.getGroupID());
        JapeSessionContext.putProperty("emp_usu_logado", BigDecimalUtil.getValueOrZero(usuVO.asBigDecimal("CODEMP")));
        JapeSessionContext.putProperty("dh_atual", new Timestamp(System.currentTimeMillis()));
        JapeSessionContext.putProperty("d_atual", new Timestamp(TimeUtils.getToday()));
        JapeSessionContext.putProperty("usuarioVO", usuVO);
        JapeSessionContext.putProperty("authInfo", authInfo);
        JapeSessionContext.putProperty("modelSession", ModelSessionManager.getSingleton().getSession(authInfo.getAuthenticationID()));

        // Baixa padrão consolidada: prepara DadosBaixa com o máximo de contexto possível
        BaixaHelper baixaHelper = new BaixaHelper(nuFin, authInfo.getUserID());
        DadosBaixa dadosBaixa = baixaHelper.montaDadosBaixa(dataBaixa, false, false);

        dadosBaixa.setImprimeRecibo(false);

        dadosBaixa.getDadosAdicionais().setCodEmpresa(finVO.asBigDecimal("CODEMP"));

        dadosBaixa.getDadosAdicionais().setCodTipoOperacao(topBaixa);

        dadosBaixa.getDadosBancarios().setCodConta(codContaBaixa);

        dadosBaixa.getDadosBancarios().setCodBanco(contaVO.asBigDecimal("CODBCO"));

        //dadosBaixa.getDadosBancarios().setCodLancamento(codLancamentoValido);

        dadosBaixa.getDadosBancarios().setHistorico(finVO.asString("HISTORICO"));

        dadosBaixa.getDadosBancarios().setNumDocumento(finVO.asBigDecimal("NUMNOTA"));

        dadosBaixa.getValoresBaixa().setVlrDesconto(0D);

        dadosBaixa.getValoresBaixa().setTaxaAdm(0D);

        dadosBaixa.getValoresBaixa().setVlrMulta(0D);

        dadosBaixa.getValoresBaixa().setVlrJuros(0D);

        dadosBaixa.getValoresBaixa().setVlrTotal(valorBaixa.doubleValue());

        FluidUpdateVO finUpdate = JapeFactory.dao("Financeiro").prepareToUpdateByPK(nuFin).set("CODTIPOPERBAIXA", topBaixa).set("DHBAIXA", new Timestamp(System.currentTimeMillis()));
        finUpdate.update();

        baixaHelper.setDadosBaixa(dadosBaixa);

        baixaHelper.baixar(dadosBaixa);


        DynamicVO finPosBaixa = JapeFactory.dao("Financeiro").findByPK(nuFin);
        if (finPosBaixa == null || finPosBaixa.asTimestamp("DHBAIXA") == null) {
            throw new Exception(String.format("DHBAIXA nao preenchido apos BaixaHelper: nufin=%s", nuFin));
        }

        BigDecimal nubco = BigDecimalUtil.getValueOrZero(NativeSql.getBigDecimal("NUBCO", "TGFFIN", "NUFIN = ?", new Object[]{nuFin}));
        if (nubco == null || nubco.signum() <= 0) {
            throw new Exception(String.format("NUBCO nao gerado apos BaixaHelper: nufin=%s", nuFin));
        }

        JapeFactory.dao("Financeiro")
                .prepareToUpdateByPK(nuFin)
                .set("NUBCO", nubco)
                .update();

        JapeFactory.dao("MovimentoBancario")
                .prepareToUpdateByPK(new Object[]{nubco})
                .set("CONCILIADO", "S")
                .update();

        LOG.info(logMsg(LOG_BAIXA, idPendencia, "BAIXA_HELPER_OK",
                String.format("nufin=%s dhbaixa=%s nubco=%s", nuFin, finPosBaixa.asTimestamp("DHBAIXA"), nubco)));
        LOG.info(logMsg(LOG_CONCILIACAO, idPendencia, "MOVIMENTO_CONCILIADO",
                String.format("nubco=%s conciliado=S", nubco)));
    }

    private static void conciliarMovimentoBancario(BigDecimal nubco, Timestamp dataConciliacao) throws Exception {
        try {
            JapeFactory.dao("MovimentoBancario")
                    .prepareToUpdateByPK(new Object[]{nubco})
                    .set("CONCILIADO", "S")
                    .update();
        } catch (Exception e) {
            throw new Exception(String.format("Erro ao atualizar o movimento bancário nubco=%s: %s", nubco, e.getMessage()), e);
        }
    }

    private static void invokeIfPresent(Object target, String methodName, Object arg) {
        if (target == null || methodName == null) {
            return;
        }
        Class<?> clazz = target.getClass();
        try {
            java.lang.reflect.Method best = null;
            for (java.lang.reflect.Method m : clazz.getMethods()) {
                if (!m.getName().equals(methodName) || m.getParameterTypes().length != 1) {
                    continue;
                }
                best = m;
                break;
            }
            if (best == null) {
                return;
            }
            Class<?> param = best.getParameterTypes()[0];
            if (arg == null || param.isInstance(arg) || (param.isPrimitive() && wrapperMatchesPrimitive(param, arg.getClass()))) {
                best.invoke(target, arg);
            }
        } catch (Exception ignore) {
            // Mantém compatibilidade entre versões do Sankhya sem quebrar a baixa.
        }
    }

    private static boolean wrapperMatchesPrimitive(Class<?> primitive, Class<?> wrapper) {
        if (primitive == boolean.class) return wrapper == Boolean.class;
        if (primitive == int.class) return wrapper == Integer.class;
        if (primitive == long.class) return wrapper == Long.class;
        if (primitive == double.class) return wrapper == Double.class;
        if (primitive == float.class) return wrapper == Float.class;
        if (primitive == short.class) return wrapper == Short.class;
        if (primitive == byte.class) return wrapper == Byte.class;
        if (primitive == char.class) return wrapper == Character.class;
        return false;
    }


    private static Map<String, BigDecimal> montarRateioTaxas(ConciliacaoRepository.ConciliacaoPendente pendencia) {
        Map<String, BigDecimal> rateio = new LinkedHashMap<String, BigDecimal>();

        // Converte valores negativos em positivos (valor absoluto)
        BigDecimal vlrComissao = nvl(pendencia.getValorComissao()).abs();
        BigDecimal vlrFrete = nvl(pendencia.getValorFrete()).abs();
        BigDecimal vlrTaxas = nvl(pendencia.getValorTaxas()).abs();

        rateio.put("COMISSAO", vlrComissao);
        rateio.put("FRETE", vlrFrete);
        rateio.put("TAXAS", vlrTaxas);
        return rateio;
    }

    private static BigDecimal totalTaxasRateio(ConciliacaoRepository.ConciliacaoPendente pendencia) {
        BigDecimal total = BigDecimal.ZERO;
        // Converte valores negativos em positivos (valor absoluto)
        total = total.add(nvl(pendencia.getValorComissao()).abs());
        total = total.add(nvl(pendencia.getValorFrete()).abs());
        total = total.add(nvl(pendencia.getValorTaxas()).abs());
        return total;
    }

    private static BigDecimal nvl(BigDecimal valor) {
        return valor == null ? BigDecimal.ZERO : valor;
    }

    private static String resumoPendencia(ConciliacaoRepository.ConciliacaoPendente pendencia) {
        return String.format("id=%s nunota=%s codvend=%s idtra=%s vlrvend=%s vlrcom=%s vlrfrete=%s vlrtx=%s vrlib=%s dtpag=%s dtlib=%s",
                pendencia.getId(),
                pendencia.getNuNota(),
                pendencia.getCodVenda(),
                pendencia.getIdTransacao(),
                pendencia.getValorVenda(),
                pendencia.getValorComissao(),
                pendencia.getValorFrete(),
                pendencia.getValorTaxas(),
                pendencia.getValorLiberado(),
                pendencia.getDataPagamento(),
                pendencia.getDataLiberacao());
    }



    public static boolean isFinanceiroBaixado(DynamicVO finVO) {
        return (finVO.getProperty("DHBAIXA") != null || finVO.getProperty("NUDEV") != null || finVO.getProperty("NURENEG") != null)
                && (finVO.getProperty("DESDOBDUPL") == null || !"M,V,F,K".contains(finVO.asString("DESDOBDUPL")));
    }
}


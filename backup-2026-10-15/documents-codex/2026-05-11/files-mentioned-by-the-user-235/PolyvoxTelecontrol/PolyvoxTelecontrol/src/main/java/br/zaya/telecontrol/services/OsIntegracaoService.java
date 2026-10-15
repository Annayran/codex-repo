package br.zaya.telecontrol.services;

import br.zaya.telecontrol.models.OsIntegracaoModel;
import br.zaya.telecontrol.models.OsIntegracaoResponseModel;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.infrastructure.http.Api;
import br.zaya.telecontrol.shared.utils.DateUtil;
import br.zaya.telecontrol.shared.utils.LogUtil;
import br.zaya.telecontrol.shared.utils.LockManager;
import br.zaya.telecontrol.interfaces.IOsIntegracaoDAO;
import br.zaya.telecontrol.adapters.OsIntegracaoDAOAdapter;
import br.zaya.telecontrol.osintegracao.application.OsIntegracaoDocumentoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class OsIntegracaoService {

    private static final String EP_OS_VISAO_GERAL = "/os/osvisaogeral";
    private static final int MAX_TENTATIVAS = 3;
    private static final String LOG_PREFIX = "[OS-INTEGRACAO] ";

    /**
     * Sincroniza OS por período de datas
     */
    public static SincronizacaoResultado sincronizarPorPeriodo(Date dataInicio, Date dataFim) throws Exception {
        SincronizacaoResultado resultado = new SincronizacaoResultado();
        LogUtil.SYNC = "OsIntegracao_PorPeriodo";

        System.out.println(LOG_PREFIX + "Entrada sincronizarPorPeriodo | dataInicio=" + DateUtil.formatIsoDate(dataInicio)
                + " | dataFim=" + DateUtil.formatIsoDate(dataFim));

        if (dataInicio == null || dataFim == null) {
            throw new Exception("Datas de início e fim são obrigatórias");
        }

        if (dataInicio.after(dataFim)) {
            throw new Exception("Data de início não pode ser depois da data de fim");
        }

        try {
            String dataInicioStr = DateUtil.formatIsoDate(dataInicio);
            String dataFimStr = DateUtil.formatIsoDate(dataFim);

            System.out.println(LOG_PREFIX + "Periodo formatado | dataInicio=" + dataInicioStr + " | dataFim=" + dataFimStr);

            resultado = sincronizarPeriodo(dataInicioStr, dataFimStr);
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + "Erro em sincronizarPorPeriodo: " + e.getMessage());
            LogUtil.saveLog(e);
            resultado.erro = e.getMessage();
        }

        return resultado;
    }

    /**
     * Sincronização automática D-1
     */
    public static SincronizacaoResultado sincronizacaoDia1() throws Exception {
        Date ontem = DateUtil.getYesterday();
        return sincronizarPorPeriodo(ontem, ontem);
    }

    /**
     * Processa apenas a etapa documental (nota de compra e financeiro)
     * a partir das OS já persistidas na AD_OSTLC.
     *
     * @param criarNotaCompraOverride override opcional para criação da nota
     */
    public static SincronizacaoResultado processarDocumentosOs(Boolean criarNotaCompraOverride) {
        SincronizacaoResultado resultado = new SincronizacaoResultado();
        try {
            OsIntegracaoDocumentoService.ResultadoDocumento doc =
                    OsIntegracaoDocumentoService.processarOsPersistidas(criarNotaCompraOverride);

            if (doc == null) {
                resultado.aviso = "Processamento documental retornou nulo";
                return resultado;
            }

            resultado.osProcessadas = doc.getTotalOsRecebidas();
            resultado.osComErro = doc.getGruposComErro();
            if (doc.getErro() != null) {
                resultado.erro = doc.getErro();
            } else if (doc.getAviso() != null) {
                resultado.aviso = doc.getAviso();
            } else {
                resultado.aviso = "Documentos processados | grupos=" + doc.getTotalGrupos()
                        + " | notasGeradas=" + doc.getNotasGeradas()
                        + " | notasIgnoradasParametro=" + doc.getNotasIgnoradasPorParametro()
                        + " | osIgnoradasOSPAGA=" + doc.getOsIgnoradasPorOsPaga()
                        + " | osIgnoradasStatusNaoFinalizada=" + doc.getOsIgnoradasPorStatus()
                        + " | financeirosGerados=" + doc.getFinanceirosGerados()
                        + " | criarNotaCompra=" + doc.isCriacaoNotaCompraHabilitada();
            }
        } catch (Exception e) {
            resultado.erro = e.getMessage();
            System.out.println(LOG_PREFIX + "Erro em processarDocumentosOs: " + e.getMessage());
            try {
                LogUtil.saveLog(e);
            } catch (Exception ignore) {
            }
        }
        return resultado;
    }

    public static SincronizacaoResultado processarDocumentosOs() {
        return processarDocumentosOs(null);
    }

    /**
     * Sincroniza um período específico
     */
    private static SincronizacaoResultado sincronizarPeriodo(String dataInicio, String dataFim) throws Exception {
        SincronizacaoResultado resultado = new SincronizacaoResultado();
        List<OsIntegracaoModel> osSincronizadas = new ArrayList<OsIntegracaoModel>();

        try {
            ConfigTelecontrol config = new ConfigTelecontrol();
            String baseUrl = config.url + EP_OS_VISAO_GERAL;
            String url = String.format("%s/dataInicio/%s/dataFim/%s", baseUrl, dataInicio, dataFim);

            System.out.println(LOG_PREFIX + "Iniciando consulta de OS | url=" + url);
            System.out.println(LOG_PREFIX + "Parametros consulta | dataInicio=" + dataInicio + " | dataFim=" + dataFim);

            LogUtil.URL = url;
            LogUtil.METHOD = "GET";

            // Buscar dados com retry
            String resposta = buscarComRetry(url, 0);
            String resumoResposta = resposta == null ? "null" : resposta.substring(0, Math.min(resposta.length(), 500));
            System.out.println(LOG_PREFIX + "HTTP responseCode=" + Api.responseCode + " | body(resumo500)=" + resumoResposta);

            // Parsear resposta
            OsIntegracaoResponseModel response = parseResponse(resposta);
            if (response != null) {
                int qtdDados = response.getDados() == null ? -1 : response.getDados().size();
                System.out.println(LOG_PREFIX + "JSON parseado | status=" + response.getStatus()
                        + " | message=" + response.getMensagem()
                        + " | pagina=" + response.getPagina()
                        + " | totalPaginas=" + response.getTotalPaginas()
                        + " | totalItens=" + response.getTotalItens()
                        + " | dados.size=" + qtdDados);
            }

            if (response == null || response.getDados() == null) {
                System.out.println(LOG_PREFIX + "Nenhuma OS encontrada para o periodo | motivo="
                        + (response == null ? "response null" : "response.data null")
                        + " | dataInicio=" + dataInicio + " | dataFim=" + dataFim);
                resultado.aviso = "Nenhuma OS encontrada para o período";
                return resultado;
            }

            if (response.getDados().isEmpty()) {
                System.out.println(LOG_PREFIX + "Nenhuma OS encontrada para o periodo | motivo=response.data vazio"
                        + " | dataInicio=" + dataInicio + " | dataFim=" + dataFim);
                resultado.aviso = "Nenhuma OS encontrada para o período";
                return resultado;
            }

            // Sincronizar cada OS
            for (OsIntegracaoModel osIntegracao : response.getDados()) {
                try {
                    sincronizarOS(osIntegracao);
                    resultado.osProcessadas++;
                    osSincronizadas.add(osIntegracao);
                } catch (Exception e) {
                    System.out.println(LOG_PREFIX + "Erro ao sincronizar OS individual | mensagem=" + e.getMessage());
                    LogUtil.saveLog(e);
                    resultado.osComErro++;
                }
            }

            if (!osSincronizadas.isEmpty()) {
                OsIntegracaoDocumentoService.processarDocumentos(osSincronizadas);
            }

            System.out.println(LOG_PREFIX + "Fim sincronizarPeriodo | osProcessadas=" + resultado.osProcessadas
                    + " | osComErro=" + resultado.osComErro);

        } catch (Exception e) {
            System.out.println(LOG_PREFIX + "Erro em sincronizarPeriodo: " + e.getMessage());
            LogUtil.saveLog(e);
            throw new Exception("Erro ao sincronizar período: " + e.getMessage(), e);
        }

        return resultado;
    }

    /**
     * Sincroniza uma OS com tratamento de duplicidade
     */
    private static void sincronizarOS(OsIntegracaoModel osIntegracao) throws Exception {
        if (osIntegracao == null) {
            System.out.println(LOG_PREFIX + "OS recebida nula, ignorando registro.");
            return;
        }

        String numeroOs = resolverChaveOs(osIntegracao);

        if (numeroOs == null || numeroOs.trim().isEmpty()) {
            throw new Exception("Número da OS não informado");
        }

         System.out.println(LOG_PREFIX + "Processando OS numero=" + numeroOs);

        LogUtil.REGISTRO = numeroOs;

        // Try to acquire an in-memory lock to reduce concurrent processing conflicts
        boolean acquired = LockManager.tryAcquire(numeroOs);
        if (!acquired) {
            throw new Exception("Já existe uma transação em andamento.");
        }

        // Use the adapter-based DAO to allow migration to the new package without
        // changing the legacy static implementation. The adapter delegates to
        // the existing OsIntegracaoDAO implementation.
        IOsIntegracaoDAO dao = new OsIntegracaoDAOAdapter();
        boolean jaExiste = dao.osExiste(numeroOs);
        if (jaExiste) {
            LogUtil.SYNC = "OsIntegracao_JaExiste";
            System.out.println(LOG_PREFIX + "OS já existe, será atualizada no DAO | numero=" + numeroOs);
        }

        // Salvar OS (já em transação gerenciada por Scheduler/Action)
        try {
            dao.salvarOrdemServico(osIntegracao);

            // Salvar peças
            dao.salvarPecasOS(numeroOs, osIntegracao);

            LogUtil.SYNC = jaExiste ? "OsIntegracao_Atualizada" : "OsIntegracao_Sucesso";
            System.out.println(LOG_PREFIX + "OS persistida com sucesso | numero=" + numeroOs);
        } finally {
            // release lock
            LockManager.release(numeroOs);
        }
    }

    private static String resolverChaveOs(OsIntegracaoModel osIntegracao) {
        String numeroOs = osIntegracao.getNumeroOs();
        String os = osIntegracao.getOs();

        if (isNumeric(numeroOs)) {
            return numeroOs.trim();
        }

        if (isNumeric(os)) {
            System.out.println(LOG_PREFIX + "numero_os não numérico ('" + numeroOs + "'), usando fallback 'os'=" + os);
            return os.trim();
        }

        return numeroOs != null ? numeroOs.trim() : null;
    }

    private static boolean isNumeric(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        try {
            new java.math.BigDecimal(valor.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Busca dados da API com retry em caso de erro
     */
    private static String buscarComRetry(String url, int tentativa) throws Exception {
        try {
            System.out.println(LOG_PREFIX + "Tentativa API GET | tentativa=" + (tentativa + 1)
                    + " de " + MAX_TENTATIVAS + " | url=" + url);
            return Api.get(url);
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + "Falha na tentativa " + (tentativa + 1) + " | erro=" + e.getMessage());
            if (tentativa < MAX_TENTATIVAS - 1) {
                // Aguardar antes de tentar novamente
                Thread.sleep(2000L * (tentativa + 1)); // Backoff exponencial
                return buscarComRetry(url, tentativa + 1);
            } else {
                throw new Exception("Erro após " + MAX_TENTATIVAS + " tentativas: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Parse da resposta JSON
     */
    private static OsIntegracaoResponseModel parseResponse(String json) throws Exception {
        if (json == null || json.trim().isEmpty()) {
            System.out.println(LOG_PREFIX + "Resposta JSON vazia ou nula.");
            return null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, OsIntegracaoResponseModel.class);
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + "Erro no parseResponse: " + e.getMessage());
            LogUtil.saveLog(e);
            throw new Exception("Erro ao parsear resposta JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Resultado da sincronização
     */
    public static class SincronizacaoResultado {
        public int osProcessadas = 0;
        public int osComErro = 0;
        public String aviso = null;
        public String erro = null;

        @Override
        public String toString() {
            return "SincronizacaoResultado{" +
                    "osProcessadas=" + osProcessadas +
                    ", osComErro=" + osComErro +
                    ", aviso='" + aviso + '\'' +
                    ", erro='" + erro + '\'' +
                    '}';
        }
    }
}


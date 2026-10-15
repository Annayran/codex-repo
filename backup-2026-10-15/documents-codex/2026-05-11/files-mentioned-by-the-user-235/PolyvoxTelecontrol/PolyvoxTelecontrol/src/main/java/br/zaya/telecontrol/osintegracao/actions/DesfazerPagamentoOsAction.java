package br.zaya.telecontrol.osintegracao.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.osintegracao.application.OsIntegracaoDocumentoService;
import br.zaya.telecontrol.shared.utils.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Desfaz a geracao de pagamento da OS Telecontrol.
 *
 * Para cada OS recebida:
 * - se houver NUNOTAGER, exclui a nota gerada;
 * - se houver NUFINGER, exclui o financeiro gerado;
 * - volta AD_OSTLC.OSPAGA para N;
 * - limpa AD_OSTLC.NUFINGER e AD_OSTLC.NUNOTAGER.
 */
public class DesfazerPagamentoOsAction implements AcaoRotinaJava {
    private static final String LOG_PREFIX = "[DESFAZER-PAGAMENTO-OS]";

    @Override
    public void doAction(ContextoAcao ctx) {
        System.out.println(LOG_PREFIX + " ===== INICIANDO ACAO =====");
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(() -> executar(ctx));
            System.out.println(LOG_PREFIX + " ===== ACAO CONCLUIDA =====");
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + " ERRO: " + e.getMessage());
            e.printStackTrace();
            ctx.setMensagemRetorno("Erro ao desfazer pagamento de OS: " + e.getMessage());
            try {
                LogUtil.saveLog(e);
            } catch (Exception ignore) {
                System.out.println(LOG_PREFIX + " Falha ao salvar log: " + ignore.getMessage());
            }
        } finally {
            JapeSession.close(hnd);
        }
    }

    private void executar(ContextoAcao ctx) throws Exception {
        List<String> nrosOs = coletarNrosOs(ctx);
        if (nrosOs.isEmpty()) {
            ctx.setMensagemRetorno("Nenhuma OS informada para desfazer pagamento.");
            return;
        }

        OsIntegracaoDocumentoService.ResultadoDesfazer resultado =
                OsIntegracaoDocumentoService.desfazerDocumentosGerados(nrosOs);

        ctx.setMensagemRetorno("Desfazer pagamento concluido.\n"
                + "OS recebidas: " + nrosOs.size() + "\n"
                + "OS resetadas: " + resultado.getOsResetadas() + "\n"
                + "Notas excluidas: " + resultado.getNotasExcluidas() + "\n"
                + "Financeiros excluidos: " + resultado.getFinanceirosExcluidos() + "\n"
                + "OS nao encontradas: " + resultado.getOsNaoEncontradas() + "\n"
                + "Notas nao encontradas: " + resultado.getNotasNaoEncontradas() + "\n"
                + "Financeiros nao encontrados: " + resultado.getFinanceirosNaoEncontrados());
    }

    private static List<String> coletarNrosOs(ContextoAcao ctx) throws Exception {
        Set<String> nrosOs = new LinkedHashSet<>();
        adicionarListaParam(nrosOs, ctx.getParam("P_NROOS_LIST"));

        Registro[] linhas = ctx.getLinhas();
        if (linhas != null) {
            for (Registro linha : linhas) {
                if (linha == null) {
                    continue;
                }
                Object nroOs = linha.getCampo("NROOS");
                if (nroOs != null) {
                    String normalizado = normalizarNumero(nroOs.toString());
                    if (!normalizado.isEmpty()) {
                        nrosOs.add(normalizado);
                    }
                }
            }
        }

        return new ArrayList<>(nrosOs);
    }

    private static void adicionarListaParam(Set<String> destino, Object valor) {
        if (valor == null) {
            return;
        }
        String[] partes = valor.toString().split("[,;|\\s]+");
        for (String parte : partes) {
            String normalizado = normalizarNumero(parte);
            if (!normalizado.isEmpty()) {
                destino.add(normalizado);
            }
        }
    }

    private static String normalizarNumero(String valor) {
        if (valor == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char ch = valor.charAt(i);
            if (Character.isDigit(ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}

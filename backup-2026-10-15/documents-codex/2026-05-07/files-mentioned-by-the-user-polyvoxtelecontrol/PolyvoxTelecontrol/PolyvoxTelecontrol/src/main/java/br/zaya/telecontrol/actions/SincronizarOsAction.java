package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.zaya.telecontrol.services.OsIntegracaoService;
import br.zaya.telecontrol.utils.DateUtil;
import br.zaya.telecontrol.utils.LogUtil;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.modelcore.util.ParameterUtils;


import java.util.Date;

/**
 * Action para sincronização manual de OS
 * Permite ao usuário informar período desejado
 * Chamada via tela de Ordem de Serviço no Sankhya
 */
public class SincronizarOsAction implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao ctx) {
        JapeSession.SessionHandle hnd = null;

        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    executarSincronizacao(ctx);
                }
            });
        } catch (Exception e) {
            ctx.setMensagemRetorno("Erro ao sincronizar OS: " + e.getMessage());
            try {
                LogUtil.saveLog(e);
            } catch (Exception logError) {
                ctx.setMensagemRetorno("Erro ao salvar log: " + logError.getMessage());
            }
        } finally {
            JapeSession.close(hnd);
        }
    }

    private void executarSincronizacao(ContextoAcao ctx) throws Exception {
        try {
            // Recuperar parâmetros informados pelo usuário
            Object dataInicioParam = ctx.getParam("P_DATA_INICIO");
            Object dataFimParam = ctx.getParam("P_DATA_FIM");



             if (dataInicioParam == null || dataFimParam == null) {
                 ctx.setMensagemRetorno("Data de Início e Data de Fim são obrigatórias");
                 return;
             }

             Date dataInicio = (Date) dataInicioParam;
             Date dataFim = (Date) dataFimParam;

             ctx.setMensagemRetorno(String.format(
                 "Iniciando sincronização manual de OS de %s a %s",
                 DateUtil.formatBrDate(dataInicio),
                 DateUtil.formatBrDate(dataFim)
             ));

             OsIntegracaoService.SincronizacaoResultado resultado =
                 OsIntegracaoService.sincronizarPorPeriodo(dataInicio, dataFim);

             if (resultado.erro != null) {
                 ctx.setMensagemRetorno(String.format("Erro: %s", resultado.erro));
             } else if (resultado.aviso != null) {
                 ctx.setMensagemRetorno(resultado.aviso);
             } else {
                 ctx.setMensagemRetorno(String.format(
                     "Sincronização concluída com sucesso!\n" +
                     "OS Processadas: %d\n" +
                     "OS com Erro: %d",
                     resultado.osProcessadas, resultado.osComErro
                 ));
             }

        } catch (Exception e) {
            throw new Exception("Erro ao executar sincronização manual: " + e.getMessage(), e);
        }
    }
}


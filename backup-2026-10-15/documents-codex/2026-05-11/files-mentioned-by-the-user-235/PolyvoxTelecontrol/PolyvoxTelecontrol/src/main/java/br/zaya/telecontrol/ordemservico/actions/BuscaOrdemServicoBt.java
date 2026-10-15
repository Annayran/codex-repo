package br.zaya.telecontrol.ordemservico.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.osintegracao.application.LegacyOsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.SincronizacaoResultado;
import br.zaya.telecontrol.shared.utils.DateUtil;
import java.util.Calendar;
import java.util.Date;

public class BuscaOrdemServicoBt implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao ctx) {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    Date dataInicio = primeiroDiaMesAtual();
                    Date dataFim = new Date();
                    SincronizacaoResultado resultado = new LegacyOsIntegracaoUseCase().sincronizarPorPeriodo(dataInicio, dataFim);
                    if (resultado.getErro() != null) {
                        throw new Exception(resultado.getErro());
                    }
                    ctx.setMensagemRetorno("Busca de OS Telecontrol concluida de "
                            + DateUtil.formatBrDate(dataInicio)
                            + " a "
                            + DateUtil.formatBrDate(dataFim)
                            + ". OS processadas: "
                            + resultado.getOsProcessadas()
                            + ". OS com erro: "
                            + resultado.getOsComErro());
                }
            });
        } catch (Exception var4) {
            ctx.setMensagemRetorno(String.valueOf(var4.toString()) + " : " + var4.getLocalizedMessage());
        } finally {
            JapeSession.close(hnd);
        }
    }

    private static Date primeiroDiaMesAtual() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}

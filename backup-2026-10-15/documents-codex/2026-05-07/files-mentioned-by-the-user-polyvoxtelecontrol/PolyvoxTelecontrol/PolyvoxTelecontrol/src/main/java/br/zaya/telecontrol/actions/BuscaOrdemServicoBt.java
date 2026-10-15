package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.services.BuscaOrdemServico;

public class BuscaOrdemServicoBt implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao ctx) {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    BuscaOrdemServico.getGeralNaoIntegradas();
                }
            });
            ctx.setMensagemRetorno("Sincronização de OS Finalizada, examinar os logs");
        } catch (Exception var4) {
            ctx.setMensagemRetorno(String.valueOf(var4.toString()) + " : " + var4.getLocalizedMessage());
        } finally {
            JapeSession.close(hnd);
        }
    }
}

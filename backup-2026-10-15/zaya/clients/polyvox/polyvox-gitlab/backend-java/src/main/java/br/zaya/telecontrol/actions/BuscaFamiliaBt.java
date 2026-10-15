package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.zaya.telecontrol.services.BuscaFamilias;
import br.zaya.telecontrol.shared.utils.LogUtil;

public class BuscaFamiliaBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            BuscaFamilias.getFamilias();
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(String.valueOf(e.toString()) + " : " + e.getLocalizedMessage());
            throw e;
        }
    }
}


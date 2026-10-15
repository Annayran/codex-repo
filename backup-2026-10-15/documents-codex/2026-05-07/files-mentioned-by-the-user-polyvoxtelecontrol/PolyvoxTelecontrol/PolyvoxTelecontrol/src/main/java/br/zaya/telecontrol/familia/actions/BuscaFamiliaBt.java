package br.zaya.telecontrol.familia.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.zaya.telecontrol.familia.application.BuscaFamiliaService;
import br.zaya.telecontrol.utils.LogUtil;

public class BuscaFamiliaBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            BuscaFamiliaService.getFamilias();
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(e.toString() + " : " + e.getLocalizedMessage());
            throw e;
        }
    }
}

package br.zaya.telecontrol.linha.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.zaya.telecontrol.linha.application.BuscaLinhaService;
import br.zaya.telecontrol.utils.LogUtil;

public class BuscaLinhaBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            BuscaLinhaService.getLinhas();
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(e.toString() + " : " + e.getLocalizedMessage());
            throw e;
        }
    }
}

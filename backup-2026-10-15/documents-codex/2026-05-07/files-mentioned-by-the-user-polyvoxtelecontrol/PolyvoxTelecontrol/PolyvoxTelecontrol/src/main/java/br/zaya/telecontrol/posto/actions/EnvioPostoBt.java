package br.zaya.telecontrol.posto.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.posto.application.EnvioPostoService;
import br.zaya.telecontrol.utils.LogUtil;
import java.math.BigDecimal;

public class EnvioPostoBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            String param = (String) ctx.getParam("SELEC");
            if ("S".equals(param)) {
                Registro[] linhas = ctx.getLinhas();
                if (linhas != null) {
                    for (Registro item : linhas) {
                        BigDecimal codparc = (BigDecimal) item.getCampo("CODPARC");
                        EnvioPostoService.postPosto("this.CODPARC = " + codparc.toString());
                    }
                }
            } else {
                EnvioPostoService.postPosto();
            }
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(e.toString() + " : " + e.getLocalizedMessage());
            throw e;
        }
    }
}

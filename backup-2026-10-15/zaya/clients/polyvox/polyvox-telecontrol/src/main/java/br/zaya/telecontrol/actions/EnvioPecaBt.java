package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.services.EnvioPeca;
import br.zaya.telecontrol.shared.utils.LogUtil;
import java.math.BigDecimal;

public class EnvioPecaBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            String param = (String) ctx.getParam("SELEC");
            if ("S".equals(param)) {
                Registro[] linhas = ctx.getLinhas();
                if (linhas != null) {
                    for (Registro item : linhas) {
                        BigDecimal codprod = (BigDecimal) item.getCampo("CODPROD");
                        EnvioPeca.postPeca("this.CODPROD = " + codprod.toString());
                    }
                }
            } else {
                EnvioPeca.postPeca();
            }
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro na rotina Telecontrol", e));
        }
    }
}


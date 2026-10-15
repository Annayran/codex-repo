package br.zaya.telecontrol.listabasica.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.listabasica.application.EnvioListaBasicaService;
import br.zaya.telecontrol.utils.LogUtil;
import java.math.BigDecimal;

public class EnvioListaBasicaBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            String param = (String) ctx.getParam("SELEC");
            if ("S".equals(param)) {
                Registro[] linhas = ctx.getLinhas();
                if (linhas != null) {
                    for (Registro item : linhas) {
                        BigDecimal codprod = (BigDecimal) item.getCampo("CODPROD");
                        BigDecimal codpeca = (BigDecimal) item.getCampo("CODPECA");
                        EnvioListaBasicaService.postListaBasica("this.CODPROD = " + codprod.toString()
                                + " AND this.CODPECA = " + codpeca.toString());
                    }
                }
            } else {
                EnvioListaBasicaService.postListaBasica();
            }
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(e.toString() + " : " + e.getLocalizedMessage());
            throw e;
        }
    }
}

package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.services.EnvioProduto;
import java.math.BigDecimal;

public class EnvioProdutoBt implements AcaoRotinaJava {
	public void doAction(ContextoAcao ctx) throws Exception {
		try {
			String param = (String) ctx.getParam("SELEC");
			if (param != null && param.equals("S")) {
				Registro[] arrayOfRegistro = ctx.getLinhas();
                if (arrayOfRegistro != null) {
                    for (int i = 0; i < arrayOfRegistro.length; i++) {
                        Registro item = arrayOfRegistro[i];
                        BigDecimal codprod = (BigDecimal) item.getCampo("CODPROD");
                        EnvioProduto.postProduto("this.CODPROD = " + codprod.toString());
                    }
                }
			} else {
				EnvioProduto.postProduto();
			}
			ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
		} catch (Exception var4) {
			ctx.setMensagemRetorno(String.valueOf(var4.toString()) + " : " + var4.getLocalizedMessage());
			var4.printStackTrace();
			throw var4;
		}
	}
}

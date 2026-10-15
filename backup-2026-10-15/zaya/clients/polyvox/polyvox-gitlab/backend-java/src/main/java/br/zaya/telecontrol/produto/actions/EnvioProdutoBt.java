package br.zaya.telecontrol.produto.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.produto.application.EnvioProdutoService;
import br.zaya.telecontrol.shared.utils.LogUtil;

import java.math.BigDecimal;

public class EnvioProdutoBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            String param = (String) ctx.getParam("SELEC");
            if (param != null && param.equals("S")) {
                Registro[] arrayOfRegistro = ctx.getLinhas();
                if (arrayOfRegistro != null) {
                    for (Registro item : arrayOfRegistro) {
                        BigDecimal codprod = (BigDecimal) item.getCampo("CODPROD");
                        EnvioProdutoService.postProduto("this.CODPROD = " + codprod.toString());
                    }
                }
            } else {
				EnvioProdutoService.postProduto();
            }
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception var4) {
            LogUtil.saveLog(var4);
            ctx.setMensagemRetorno(var4.toString() + " : " + var4.getLocalizedMessage());
            throw var4;
        }
    }
}


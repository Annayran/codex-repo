package br.zaya.telecontrol.listabasica.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.listabasica.application.EnvioListaBasicaService;
import br.zaya.telecontrol.shared.utils.LogUtil;
import java.math.BigDecimal;

public class EnvioListaBasicaBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            String operacao = getOperacao(ctx);
            if ("GET".equals(operacao) || "BUSCAR".equals(operacao)) {
                new BuscaListaBasicaBt().doAction(ctx);
                return;
            }
            if ("DELETE".equals(operacao) || "EXCLUIR".equals(operacao)) {
                new DeleteListaBasicaProdutoBt().doAction(ctx);
                return;
            }

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
            ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro na rotina Telecontrol", e));
        }
    }

    private String getOperacao(ContextoAcao ctx) {
        Object valor = null;
        try {
            valor = ctx.getParam("OPERACAO");
        } catch (Exception ignored) {
        }
        if (valor == null) {
            try {
                valor = ctx.getParam("ACAO");
            } catch (Exception ignored) {
            }
        }
        return valor == null ? "" : String.valueOf(valor).trim().toUpperCase();
    }
}


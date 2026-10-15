package br.zaya.telecontrol.faturamento.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.faturamento.application.EnvioFaturamentoService;
import br.zaya.telecontrol.shared.utils.LogUtil;

import java.math.BigDecimal;

public class EnviaFaturamentoBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            String param = (String) ctx.getParam("SELEC");
            if ("S".equals(param)) {
                Registro[] linhas = ctx.getLinhas();
                for (Registro reg : linhas) {
                    BigDecimal pedidoId = (BigDecimal) reg.getCampo("PEDIDO");
                    EnvioFaturamentoService.setFaturamento(pedidoId);
                }
            } else {
                EnvioFaturamentoService.setFaturamento();
            }
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception var4) {
            LogUtil.saveLog(var4);
            ctx.setMensagemRetorno(String.valueOf(var4.toString()) + " : " + var4.getLocalizedMessage());
            var4.printStackTrace();
        }
    }
}


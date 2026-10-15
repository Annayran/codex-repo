package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.zaya.telecontrol.services.EnvioPedidoExportado;
import br.zaya.telecontrol.shared.utils.LogUtil;
import java.math.BigDecimal;

public class MarcarExportadoBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            Registro[] linhas = ctx.getLinhas();
            if (linhas != null) {
                for (Registro item : linhas) {
                    EnvioPedidoExportado.postPedidoExportado((BigDecimal) item.getCampo("PEDIDO"));
                }
            }
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro na rotina Telecontrol", e));
        }
    }
}

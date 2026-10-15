package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.zaya.telecontrol.pedidos.application.EnvioPedidoExportadoService;
import br.zaya.telecontrol.shared.utils.LogUtil;

public class EnviaPedidoExportadoBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        try {
            EnvioPedidoExportadoService.postPedidoExportado();
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception e) {
            LogUtil.saveLog(e);
            ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro na rotina Telecontrol", e));
        }
    }
}

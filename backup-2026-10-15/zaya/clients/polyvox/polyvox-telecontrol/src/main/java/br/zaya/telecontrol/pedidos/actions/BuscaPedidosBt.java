package br.zaya.telecontrol.pedidos.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.controls.PedidoControl;
import br.zaya.telecontrol.pedidos.application.BuscaPedidosService;
import br.zaya.telecontrol.pedidos.application.EnvioPedidoExportadoService;

public class BuscaPedidosBt implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao ctx) {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.execWithTX(new JapeSession.TXBlock() {
                public void doWithTx() throws Exception {
                    BuscaPedidosService.getNaoIntegrados();
                    PedidoControl.gravarPedidosNaoGerados();
                    EnvioPedidoExportadoService.postPedidoExportado();
                }
            });
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
        } catch (Exception var4) {
            ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro na rotina Telecontrol", var4));
        } finally {
            JapeSession.close(hnd);
        }
    }
}


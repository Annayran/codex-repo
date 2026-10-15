package br.zaya.telecontrol.actions;

import br.com.sankhya.jape.core.JapeSession;
import br.zaya.telecontrol.controls.PedidoControl;
import br.zaya.telecontrol.services.BuscaPedidos;
import br.zaya.telecontrol.services.EnvioPedidoExportado;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public class BuscaPedidosSc implements ScheduledAction {
	public void onTime(ScheduledActionContext ctx) {
		JapeSession.SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();
			hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
			hnd.execWithTX(new JapeSession.TXBlock() {
				public void doWithTx() throws Exception {
					BuscaPedidos.getNaoIntegrados();
					PedidoControl.gravarPedidosNaoGerados();
					EnvioPedidoExportado.postPedidoExportado();
				}
			});
			ctx.info("Sincronização de Pedidos Concluída: Verificar Logs ");
		} catch (Exception var9) {
			ctx.info("erro: " + var9.toString() + " : " + var9.getLocalizedMessage());
		} finally {
			JapeSession.close(hnd);
		}
	}
}

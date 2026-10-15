package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.core.commons.extensions.Action;

public class CriaPedidoCompraBeneficioTransubeAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();
			
			BigDecimal codCbe = BigDecimalUtil.getBigDecimal(contexto.getParam("CODCBE"));
			
			PedidoCompraBeneficioTransubeHelper helper = new PedidoCompraBeneficioTransubeHelper(action);
			helper.criaPedidoCompra(codCbe);

		} catch (Exception e) {
			action.error(e);

		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}
}

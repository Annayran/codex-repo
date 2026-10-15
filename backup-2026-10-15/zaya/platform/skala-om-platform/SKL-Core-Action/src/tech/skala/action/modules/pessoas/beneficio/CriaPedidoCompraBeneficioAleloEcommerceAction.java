package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class CriaPedidoCompraBeneficioAleloEcommerceAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws SkalaModelException {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			BigDecimal codCbe = BigDecimalUtil.getBigDecimal(contexto.getParam("CODCBE"));

			PedidoCompraBeneficioAleloEcommerceHelper helper = new PedidoCompraBeneficioAleloEcommerceHelper(action);
			helper.criaPedidoCompra(codCbe);

		} catch (SkalaModelException e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
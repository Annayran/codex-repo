package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class PedidoCompraBeneficioAleloEcommerceHelper {

	private Action action;

	public PedidoCompraBeneficioAleloEcommerceHelper(Action action) {
		this.action = action;
	}

	public void criaPedidoCompra(BigDecimal codCbe) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					String observacao = String.format(
							"Pedido de compra calculado a partir do Número Único da Central de Benefício %s.", codCbe);

					PedidoCompraBeneficioAleloEcommerce pedido = new PedidoCompraBeneficioAleloEcommerce(observacao,
							codCbe);
					pedido.buildCabecalhoNotaByTemplate();
					pedido.buildItems();
					action.appendMessage(String.format("Pedido criado com sucesso: %s", pedido.getNuNota()));

				}

			});

		} catch (Exception e) {
			throw new SkalaModelException("Não foi possível criar pedido de compra para o benefício Alelo-Ecommerce.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}
}

package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class PedidoCompraBeneficioAleloHelper {

	private Action action;
	public PedidoCompraBeneficioAleloHelper(Action action) {
		this.action = action;
	}

	public void criaPedidoCompra(BigDecimal codCbe) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					String observacao = String.format(
							"Pedido de compra calculado a partir do Número Único da Central de Benefício %s.", codCbe);

					PedidoCompraBeneficioAlelo pedido = new PedidoCompraBeneficioAlelo(observacao, codCbe);
					pedido.buildCabecalhoNotaByTemplate();
					pedido.buildItems();

					RateioProcessor rateioProcessor = new RateioProcessor(pedido.getNuNota());
					rateioProcessor.geraRateio(codCbe);
					
					action.appendMessageWithLineSeparator(String.format("Pedido criado com sucesso: %s", pedido.getNuNota()));
				}

			});

		} catch (Exception e) {
			throw new SkalaModelException("Não foi possível criar pedido de compra para o benefício Alelo.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}
}

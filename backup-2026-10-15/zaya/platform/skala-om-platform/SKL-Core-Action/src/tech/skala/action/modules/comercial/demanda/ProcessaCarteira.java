package tech.skala.action.modules.comercial.demanda;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.action.modules.comercial.radarproduto.RadarProdutoProcessor;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Extension;

public class ProcessaCarteira {

	
	public void processarCarteira (Extension action, String origemDemanda) {
		SessionHandle hnd = null;
		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			final CarteiraPedidosProcessor processor = new CarteiraPedidosProcessor();
			processor.inicializarCarteira();
			processor.buildItens();
			processor.buildRadar();
			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					String msg = String.format("Qtd. Notas %s.", processor.getCarteiraCabecalhos().size());
					action.appendMessage(msg);

					msg = String.format("%nQtd. total de itens carregados %s.", processor.getItensDemanda().size());
					action.appendMessage(msg);

					processor.atualizaMeta();
					msg = "\nMetas Atualizadas.";
					action.appendMessage(msg);
					
					processor.gerarDemanda(origemDemanda);
					msg = "\nDemanda Gerada.";
					action.appendMessage(msg);
					
					RadarProdutoProcessor radarProcessor = new RadarProdutoProcessor();
					radarProcessor.atualizarRadar(processor.getRadarFamilias());
					
					msg = "\nRadar Atualizado.";
					action.appendMessage(msg);
				}
			});

		} catch (Exception e) {
			String msg = "Não foi possível inicializar a Carteira.";
			msg = SkalaModelException.buildErrorMessage(msg, e);
		} finally {
			hnd = null;
		}
	}
}

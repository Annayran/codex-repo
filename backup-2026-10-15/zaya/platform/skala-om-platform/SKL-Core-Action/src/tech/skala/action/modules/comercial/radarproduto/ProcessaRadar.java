package tech.skala.action.modules.comercial.radarproduto;

import java.util.List;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.action.beans.radarproduto.RadarFamilia;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProcessaRadar {

	public void atualizarRadarWithTX(List<RadarFamilia> familias) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					RadarProdutoProcessor processor = new RadarProdutoProcessor();
					processor.atualizarRadar(familias);

				}

			});

		} catch (Exception e) {
			String msg = "Não foi possível inicializar a Carteira.";
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}
	}

}

package tech.skala.action.modules.controladoria;

import java.math.BigDecimal;

import com.cbmsoft.modelcore.CbmModelException;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;

public class ReprocessaRateioProduto {
	private ReprocessaRateioProduto() {
		throw new IllegalStateException("Utility class");
	}

	public static void geraRateio(BigDecimal nuNota) throws CbmModelException {
		SessionHandle					hnd = null;
		
		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					RateioProcessor rateio = new RateioProcessor(nuNota);
					rateio.excluirRateio();
					rateio.gerarRateio();
					rateio.atualizarOrigem();
				}
			});
			
		} catch (Exception e) {
			throw new CbmModelException(e);
		} finally {
			hnd = null;
		}		
	}
}

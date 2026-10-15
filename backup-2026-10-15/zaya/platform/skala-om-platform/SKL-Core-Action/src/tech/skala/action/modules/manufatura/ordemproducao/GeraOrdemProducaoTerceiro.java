package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class GeraOrdemProducaoTerceiro {
	private final Logger					logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private String						nuNota;
	private LancamentoProducaoProcessor		processor;
	
	private DynamicVO						cabecalhoNotaVO;
	
	public GeraOrdemProducaoTerceiro(String nuNota) {
		this.nuNota = nuNota;
		this.processor = new LancamentoProducaoProcessor();
	}

	public void vinculaNotaOrdemProducao (BigDecimal nuNota, BigDecimal idiProc) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

				}

			});

		} catch (Exception e) {
		SkalaModelException.throwMe(null, e);
		} finally {
			JapeSession.close(hnd);
		}
	}
}

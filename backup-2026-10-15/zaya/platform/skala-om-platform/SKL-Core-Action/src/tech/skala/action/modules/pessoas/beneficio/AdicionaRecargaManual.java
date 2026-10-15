package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class AdicionaRecargaManual {

	private Registro[]						linhas;
	private BigDecimal						indCre;
	private CalculoBeneficioProcessor		processor;
	
	public AdicionaRecargaManual(Registro[] linhas, BigDecimal indCre) {
		this.linhas = linhas;
		this.indCre = indCre;
		this.processor = new CalculoBeneficioProcessor(linhas);
	}
	
	public void adicionaRecargaManual() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					processor.adicionarRecargaManual(indCre);
				}
			});

		} catch (Exception e) {
			String msg = "Não foi possível adicionar recarga manual.";
			SkalaModelException.throwMe(msg, e);
		} finally {
			hnd = null;
		}

	}
}

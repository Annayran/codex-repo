package tech.skala.action.modules.pessoas.beneficio;

import java.sql.Timestamp;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class CalculaPreBeneficio {

	private Timestamp						dtRef;
	private Timestamp						dtPon;
	private CalculoBeneficioProcessor		processor;
	
	public CalculaPreBeneficio(Action action, Timestamp dtRef, Timestamp dtPon) {
		this.dtRef = dtRef;
		this.dtPon = dtPon;
		this.processor = new CalculoBeneficioProcessor();
	}
	
	public void calculaPreBeneficio() throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					processor.calcularPreBeneficio(dtRef, dtPon);
				}
			});

		} catch (Exception e) {
			String msg = "Não foi possível processar o Pré Benefício.";
			  SkalaModelException.throwMe(msg, e);
		} finally {
			hnd = null;
		}

	}

	public void adicionaRecargaSemanal() throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					processor.processarRecargaSemanal(dtRef, dtPon);
				}
			});

		} catch (Exception e) {
			String msg = "Não foi possível recarregar Pré Benefício Semanal.";
			SkalaModelException.throwMe(msg, e);
		} finally {
			hnd = null;
		}
		
	}

}

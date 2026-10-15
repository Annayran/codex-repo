package tech.skala.action.integration.transport.callcenter;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.action.modules.centrais.CabecalhoNotaDAO;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class ProcessarCTeAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			for(Registro linha : linhas) {
				String chaveCte = (String) linha.getCampo("CHAVECTE");

				processaCTe(chaveCte);
			}
			
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

	private void processaCTe(String chaveCte) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws SkalaModelException {					
					
					for(DynamicVO vo : CabecalhoNotaDAO.getCabecalhoFromChaveCTe(chaveCte))
						CallCenterProcessor.processarCTe(vo);
					
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar o Ct-e", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

}
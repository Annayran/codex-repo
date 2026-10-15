package br.com.zaya.like.Actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.zaya.like.Actions.services.CancelarNegociacaoService;

public class CancelarNegociacaoAction  implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		EntityFacade dwfFacade = JapeFactory.getEntityFacade();
		JapeSession.SessionHandle hnd = null;
		
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			hnd.execWithTX(new JapeSession.TXBlock() {
				public void doWithTx() throws Exception {
					Registro[] linhas = contexto.getLinhas();
					
					boolean executar = false;
					
					String motivoCancelamento = (String) contexto.getParam("P_MOTIVO");
					
					for(Registro linha : linhas) {
						BigDecimal nunegociacao = (BigDecimal) linha.getCampo("NUNEGOCIACAO");
						CancelarNegociacaoService.processarCancelamento(dwfFacade, motivoCancelamento, nunegociacao);
					}
				}
			});
		} catch (Exception e) {
			contexto.mostraErro("<p align=\"left\"><font size=\"12\" face=\"arial\" color=\"#000000\"><br></font><b>Atencão:  </b>Erro ao cancelar negociação!<br><br>"
							+ "<b>Motivo: </b>" + e.getMessage());
		} finally {
			JapeSession.close(hnd);
		}
		
		}

}

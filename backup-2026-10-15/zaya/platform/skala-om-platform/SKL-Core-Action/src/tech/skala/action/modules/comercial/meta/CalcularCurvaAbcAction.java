package tech.skala.action.modules.comercial.meta;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.beans.meta.MetaPorFamilia;
import tech.skala.core.commons.extensions.Action;

public class CalcularCurvaAbcAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		
		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			MetaPorFamilia meta;
			MetasOrcamentoProcessor processor;
			for (Registro linha : linhas) {
				meta = new MetaPorFamilia(false, false);
				meta.loadByRecord(linha);
				
				processor = new MetasOrcamentoProcessor();
				processor.calculaCurvaABC(meta);
				
			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
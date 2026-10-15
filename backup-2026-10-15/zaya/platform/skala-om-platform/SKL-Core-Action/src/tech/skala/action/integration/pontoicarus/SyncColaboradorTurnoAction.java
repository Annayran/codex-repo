package tech.skala.action.integration.pontoicarus;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class SyncColaboradorTurnoAction implements AcaoRotinaJava {
	Action action = new Action(this.getClass().getCanonicalName());

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {

		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			for (Registro linha : linhas)
				Sync.incluiColaboradorTurno(BigDecimalUtil.getBigDecimal(linha.getCampo("CODCARGAHOR")));
			
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}

	}

}

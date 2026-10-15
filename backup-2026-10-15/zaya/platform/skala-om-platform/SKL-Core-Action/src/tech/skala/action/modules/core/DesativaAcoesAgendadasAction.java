package tech.skala.action.modules.core;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class DesativaAcoesAgendadasAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		BigDecimal sklModulo;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			for (Registro linha : linhas) {
				sklModulo = BigDecimalUtil.getBigDecimal(linha.getCampo("CODMODULO"));

				ModelHelper helper = new ModelHelper();
				helper.inativaAcoesAgendadas(sklModulo);
			}

			/* adiciona uma mensagem na mensagem de retorno ao usuario */
			msg = "As Ações Agendadas que utilizam este Módulo foram desativadas.";
			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
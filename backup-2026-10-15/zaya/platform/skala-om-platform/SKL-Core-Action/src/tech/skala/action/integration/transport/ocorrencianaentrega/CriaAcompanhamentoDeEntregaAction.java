package tech.skala.action.integration.transport.ocorrencianaentrega;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class CriaAcompanhamentoDeEntregaAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			OcorrenciaDeEntregaProcessor processor = new OcorrenciaDeEntregaProcessor();
			
			for (Registro registro : linhas) {
				BigDecimal nuNota = BigDecimalUtil.getBigDecimal(registro.getCampo(SklFieldName.NUNOTA));
				processor.criaAcompanhamentoDeEntrega(nuNota);
				
			}
			
			/* adiciona uma mensagem na mensagem de retorno ao usuario */
//			msg = "";
//			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
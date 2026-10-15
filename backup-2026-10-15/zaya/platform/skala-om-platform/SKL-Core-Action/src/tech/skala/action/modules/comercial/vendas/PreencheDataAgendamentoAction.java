package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class PreencheDataAgendamentoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		Timestamp dhAgendamento = (Timestamp) contexto.getParam("DHAGENDAMENTO");
		String observacao = (String) contexto.getParam(SklFieldName.OBSERVACAO);
		BigDecimal nuNota;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			ComercialHelper helper = new ComercialHelper();
			for (Registro linha : linhas) {
				nuNota = (BigDecimal) linha.getCampo(SklFieldName.NUNOTA);
				
				helper.preencheDataAgendamento(nuNota, dhAgendamento, observacao);
			}
			

			msg = "A Data de Agendamento foi atualizada para os registros selecionados.";
			action.appendMessageWithLineSeparator(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
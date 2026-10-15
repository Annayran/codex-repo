package tech.skala.action.modules.recebimento;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class AlteraQtdConferidaAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		
		BigDecimal newQtdConf = BigDecimalUtil.getBigDecimal(contexto.getParam("NEWQTDCONF"));
		
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			BigDecimal nuConferencia;
			BigDecimal sequencia;
			
			RecebimentoMercadoriaProcessor processor = new RecebimentoMercadoriaProcessor();
			
			for (Registro linha : linhas) {
				nuConferencia = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUCONFERENCIA));
				sequencia = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.SEQUENCIA));
				
				processor.alteraQtdConferida(nuConferencia, sequencia, newQtdConf);
				
			}
			
			
			/* adiciona uma mensagem na mensagem de retorno ao usuario */
			msg = "";
			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
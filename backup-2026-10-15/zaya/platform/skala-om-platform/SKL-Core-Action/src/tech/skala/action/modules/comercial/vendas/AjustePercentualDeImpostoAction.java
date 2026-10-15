package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class AjustePercentualDeImpostoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		
		BigDecimal fator = BigDecimalUtil.getBigDecimal(contexto.getParam(SklFieldName.FATOR));
		BigDecimal codGrupoProd = BigDecimalUtil.getBigDecimal(contexto.getParam(SklFieldName.CODGRUPOPROD));
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			RecalcularImpostoProcessor processor;
			for (Registro linha : linhas) {
				processor = new RecalcularImpostoProcessor();
				processor.recalculaImposto(BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUNOTA)), fator, codGrupoProd);
			}
			
			
			msg = String.format("%s registros tiveram seus Itens Recalculados.", linhas.length);
			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
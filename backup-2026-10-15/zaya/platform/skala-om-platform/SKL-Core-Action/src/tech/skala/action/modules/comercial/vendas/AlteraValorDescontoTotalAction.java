package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class AlteraValorDescontoTotalAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao context) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		
		try {
			action.start();

			Registro[] rows = context.getLinhas();
			action.validateRows(rows);
			
			if (rows.length > 1)
				throw new IllegalArgumentException("Apenas 1 registro deve ser selecionado.");
			
			BigDecimal nuNota = BigDecimalUtil.getBigDecimal(rows[0].getCampo(SklFieldName.NUNOTA));
			BigDecimal vlrDescTot = BigDecimalUtil.getBigDecimal(context.getParam("VLRDESCTOT"));
			
			AlterarValorDescontoTotalDAO.alteraValorDescontoTotal(nuNota, vlrDescTot);
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			context.setMensagemRetorno(action.getMensagemRetorno());
		}
		
	}

}

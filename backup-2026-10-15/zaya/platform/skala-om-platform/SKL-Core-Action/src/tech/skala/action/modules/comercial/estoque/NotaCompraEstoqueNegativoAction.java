package tech.skala.action.modules.comercial.estoque;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.modules.comercial.estoque.helpers.NotaCompraEstoqueNegativoHelper;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class NotaCompraEstoqueNegativoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao context) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		
		try {
			action.start();
			
			Registro[] rows = context.getLinhas();
			
			action.validateRows(rows);
			
			BigDecimal codEmp = BigDecimal.ZERO;
			BigDecimal codParc = BigDecimal.ZERO;
			BigDecimal codLocal = BigDecimal.ZERO;
			String tipo;
			
			for (Registro row : rows) {
				codEmp = BigDecimalUtil.getBigDecimal(row.getCampo(SklFieldName.CODEMP) );
				codParc = BigDecimalUtil.getBigDecimal(row.getCampo(SklFieldName.CODPARC));
				tipo = String.valueOf(row.getCampo("TIPO"));
				codLocal = BigDecimalUtil.getBigDecimal(row.getCampo(SklFieldName.CODLOCAL));
				
				NotaCompraEstoqueNegativoHelper helper = new NotaCompraEstoqueNegativoHelper(action, codEmp, codParc, codLocal, tipo);
				helper.process();
			}
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			context.setMensagemRetorno(action.getMensagemRetorno());
		}
	}
}

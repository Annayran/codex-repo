package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class SetReajusteAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String reajuste = (String) contexto.getParam(SklFieldName.TEMREAJUSTE);
		
		BigDecimal nuNota;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			ComercialHelper helper = new ComercialHelper();
			for (final Registro linha : linhas) {
				nuNota = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUNOTA));
				helper.setCampoReajuste(nuNota, reajuste);
			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
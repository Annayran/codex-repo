package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class RecalculaQuantidadeVolumesAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			BigDecimal nuNota;
			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			for (Registro linha : linhas) {
				
				nuNota = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUNOTA));
				ComercialHelper.recalculaQuantidadeVolumes(nuNota);
				
				msg = String.format("[NUNOTA %s] Qtd. Volume do lançamento foi corrigida.", nuNota);
				action.appendMessageWithLineSeparator(msg);
			}
			
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
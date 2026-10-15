package tech.skala.action.modules.comercial.meta;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.beans.meta.MetaPorFamilia;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class DuplicarMetaPorFamiliaAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			BigDecimal codFam;
			Timestamp dtRef;
			
			MetaPorFamilia meta;
			MetasOrcamentoProcessor processor;
			for (Registro linha : linhas) {

				codFam = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODFAM));
				dtRef = (Timestamp) linha.getCampo(SklFieldName.DTREF);
				
				meta = new MetaPorFamilia(false, false);
				meta.loadByRecord(linha);
				
				processor = new MetasOrcamentoProcessor();
				processor.duplicaMeta(meta);

				msg = String.format("[CODFAM %s][DTREF %s] Meta duplicada para o mês <b>%s</b>.%n", codFam,
						TimeUtils.formataDDMMYYYY(dtRef),
						TimeUtils.formataDDMMYYYY(TimeUtils.dataAdd(dtRef, 1, Calendar.MONTH)));
				
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
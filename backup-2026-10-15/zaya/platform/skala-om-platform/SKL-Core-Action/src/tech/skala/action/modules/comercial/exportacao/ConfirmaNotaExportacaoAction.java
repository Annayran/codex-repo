package tech.skala.action.modules.comercial.exportacao;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class ConfirmaNotaExportacaoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			action.validateUniqueRowSelected(linhas);

			BigDecimal vlrDescTot = BigDecimalUtil.getBigDecimal(contexto.getParam("VLRDESCTOT"));
			BigDecimal nuNota = BigDecimalUtil.getBigDecimal(linhas[0].getCampo(SklFieldName.NUNOTA));

			ConfirmaNotaExportacaoProcessor processor = new ConfirmaNotaExportacaoProcessor(nuNota, vlrDescTot);
			processor.confirmarNotaExportacao();
		
			action.appendMessageWithLineSeparator(String.format("Processo de Exportação para a NF %s <b>foi finalizado.</b>", nuNota));
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class OrdemProducaoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();
			
			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			OrdemProducao ordemProducao = null;
			for (Registro linha : linhas) {
				BigDecimal idiProc = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.IDIPROC));
				
				ordemProducao = OrdemProducaoBuilder.buildBYPk(idiProc);
				
				String msg = String.format("[IDIPROC %s] %S", idiProc, ordemProducao);
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

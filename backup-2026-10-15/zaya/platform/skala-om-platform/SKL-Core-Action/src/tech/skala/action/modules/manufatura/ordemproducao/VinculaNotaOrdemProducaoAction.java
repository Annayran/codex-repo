package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class VinculaNotaOrdemProducaoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		BigDecimal nuNota = BigDecimalUtil.getBigDecimal(contexto.getParam(SklFieldName.NUNOTA));
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			GeraOrdemProducaoTerceiro op3Helper = new GeraOrdemProducaoTerceiro(SklFieldName.NUNOTA);
			BigDecimal idiProc;
			for (final Registro linha : linhas) {
				idiProc = BigDecimalUtil.getBigDecimal(linha.getCampo("IDIPROC"));
				op3Helper.vinculaNotaOrdemProducao(nuNota, idiProc);
			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
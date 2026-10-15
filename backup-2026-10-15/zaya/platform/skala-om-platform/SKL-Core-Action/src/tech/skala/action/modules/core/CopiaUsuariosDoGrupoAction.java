package tech.skala.action.modules.core;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class CopiaUsuariosDoGrupoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		BigDecimal grupoOrig = BigDecimalUtil.getBigDecimal(contexto.getParam("CODGRUPOORIG"));
		BigDecimal grupoDest;

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			for (Registro linha : linhas) {
				grupoDest = BigDecimalUtil.getBigDecimal(linha.getCampo("CODGRUPO"));
				ModelHelper helper = new ModelHelper();
				helper.copiaUsuariosDoGrupo(grupoOrig, grupoDest);
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
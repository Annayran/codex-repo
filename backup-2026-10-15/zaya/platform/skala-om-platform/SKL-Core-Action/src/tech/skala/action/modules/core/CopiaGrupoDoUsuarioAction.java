package tech.skala.action.modules.core;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class CopiaGrupoDoUsuarioAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		BigDecimal codUsuOrig = BigDecimalUtil.getBigDecimal(contexto.getParam("CODUSUORIG"));
		BigDecimal codUsuDest;

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			for (Registro linha : linhas) {
				codUsuDest = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODUSU));
				ModelHelper helper = new ModelHelper();
				helper.copiaGrupoDoUsuario(codUsuOrig, codUsuDest);
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
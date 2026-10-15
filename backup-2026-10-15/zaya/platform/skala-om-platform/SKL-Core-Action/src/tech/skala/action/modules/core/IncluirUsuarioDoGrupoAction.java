package tech.skala.action.modules.core;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class IncluirUsuarioDoGrupoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		
		BigDecimal codUsu = BigDecimalUtil.getBigDecimal(contexto.getParam("CODUSU"));
		BigDecimal codGrupo;
		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			for (Registro linha : linhas) {
				codGrupo = BigDecimalUtil.getBigDecimal(linha.getCampo("CODGRUPO"));
				
				ModelHelper helper = new ModelHelper();
				helper.incluiUsuarioNoGrupo(codGrupo, codUsu);
			}

			/* adiciona uma mensagem na mensagem de retorno ao usuario */
			msg = "Usuário incluído com sucesso";
			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
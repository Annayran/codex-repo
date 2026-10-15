package tech.skala.action.modules.core;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class ExcluirUsuarioDoGrupoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			BigDecimal codGrupo;
			BigDecimal codUsu;
			String tipo;

			for (Registro linha : linhas) {

				codGrupo = BigDecimalUtil.getBigDecimal(linha.getCampo("CODGRUPO"));
				codUsu = BigDecimalUtil.getBigDecimal(linha.getCampo("CODUSU"));
				tipo = (String) linha.getCampo("TIPO");

				if ("P".equals(tipo)) {
					msg = "Não é possível excluir o usuário de seu grupo principal";
					throw new SkalaModelException(msg);
				}

				ModelHelper helper = new ModelHelper();
				helper.excluiUsuarioDoGrupo(codGrupo, codUsu);

			}

			/* adiciona uma mensagem na mensagem de retorno ao usuario */
			msg = "Usuário excluído com sucesso";
			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
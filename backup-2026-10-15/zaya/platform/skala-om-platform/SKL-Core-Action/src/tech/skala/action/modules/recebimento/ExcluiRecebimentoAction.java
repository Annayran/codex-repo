package tech.skala.action.modules.recebimento;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class ExcluiRecebimentoAction implements AcaoRotinaJava{

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			// pegar as linhas que estão selecionadas ta tela
			Registro[] linhas = contexto.getLinhas();

			// valida linhas (se for maior que 0 nao da erro)
			action.validateRows(linhas);

			// valida linha (se for maior que 1, da erro)
			action.validateUniqueRowSelected(linhas);

			// variavel nunota da tela
			BigDecimal nuNota = null;

			RecebimentoMercadoriaProcessor processor = null;

			for (Registro linha : linhas) {
				nuNota = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUNOTA));

				processor = new RecebimentoMercadoriaProcessor();

				processor.excluiRecebimento(nuNota);
			}

			/* adiciona uma mensagem na mensagem de retorno ao usuario */
			msg = String.format("[NUNOTA %s] Os recebimentos do lançamento foram excluídos.", nuNota);
			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}
}

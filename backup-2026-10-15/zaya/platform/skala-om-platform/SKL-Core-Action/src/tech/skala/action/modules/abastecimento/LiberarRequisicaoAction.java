package tech.skala.action.modules.abastecimento;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class LiberarRequisicaoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			BigDecimal nuReq = null;
			String status = null;
			
			RequisicaoProcessor processor = null;
			for (Registro linha : linhas) {
				processor = new RequisicaoProcessor();
				
				nuReq = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUREQ));
				status = (String) linha.getCampo(SklFieldName.STATUS);
				
				if (!StatusRequisicaoEnum.CRIADO.getValue().equals(status))
					throw new SkalaModelException("Só é possível liberar Requisição com o <b>status Criado<b>.");

				processor.liberaRequisicao(nuReq);
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
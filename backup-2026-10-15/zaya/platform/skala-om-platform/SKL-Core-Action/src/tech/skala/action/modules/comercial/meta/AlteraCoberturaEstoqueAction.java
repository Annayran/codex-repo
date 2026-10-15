package tech.skala.action.modules.comercial.meta;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.beans.meta.MetaPorFamilia;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class AlteraCoberturaEstoqueAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			BigDecimal coberturaEstoque = BigDecimalUtil.getBigDecimal(contexto.getParam("PERCCOB"));

			String confirmado;
			MetaPorFamilia meta;
			for (Registro linha : linhas) {
				confirmado = (String) linha.getCampo("CONFIRMADO");

				if ("S".equalsIgnoreCase(confirmado)) {
					msg = "Não é possível atualizar o campo Cobertura de Estoque de uma <b>Meta Confirmada</b>.";
					throw new SkalaModelException(msg);
				}

				meta = new MetaPorFamilia(false, false);
				meta.loadByRecord(linha);

				MetasOrcamentoProcessor processor = new MetasOrcamentoProcessor();
				processor.alteraCoberturaEstoque(coberturaEstoque, meta);

			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
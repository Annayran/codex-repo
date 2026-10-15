package tech.skala.action.modules.wms.estoque;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class AlteraDataValidadeDoProdutoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		Timestamp dtVal = (Timestamp) contexto.getParam(SklFieldName.DTVAL);
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			BigDecimal codEmp;
			BigDecimal codLocal;
			String controle;
			BigDecimal codEnd;
			BigDecimal codProd;
			String codVol;
			BigDecimal codParc;

			EstoqueProcessor processor = new EstoqueProcessor();
			for (Registro linha : linhas) {
				codEmp = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODEMP));
				codLocal = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODLOCAL));
				controle = (String) linha.getCampo(SklFieldName.CONTROLE);
				codEnd = BigDecimalUtil.getBigDecimal(linha.getCampo("CODEND"));
				codProd = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODPROD));
				codVol = (String) linha.getCampo(SklFieldName.CODVOL);
				codParc = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODPARC));

				processor.alteraDataValidadeDoProduto(codEmp, codLocal, controle, codEnd, codProd, codVol, codParc,
						dtVal);
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
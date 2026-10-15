package tech.skala.action.modules.wms.endereco;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.modelcore.comercial.nfse.enuns.SimNao;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class AtivarInativarEnderecoArmazenagemAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao context) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			Registro[] rows = context.getLinhas();

			action.validateRows(rows);

			String ativo = (String) context.getParam(SklFieldName.ATIVO);

			BigDecimal codEnd;
			for (Registro row : rows) {
				codEnd = BigDecimalUtil.getBigDecimal(row.getCampo("CODENDORIGEM"));

				EnderecoArmazenagemProcessor.ativaInativaEndereco(codEnd, ativo);

			}
			String msg = String.format("%s endereços foram <b>%s com sucesso</b>.", rows.length,
					SimNao.SIM.getValue().equals(ativo) ? "Ativados" : "Inativados");
			action.appendMessageWithLineSeparator(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			context.setMensagemRetorno(action.getMensagemRetorno());
		}

	}

}

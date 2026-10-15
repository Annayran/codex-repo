package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class ValidaTarifaTerceiroAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			BigDecimal nuNota;
			BigDecimal codEmp;
			OrdemProducaoProcessor processor;
			
			for (final Registro linha : linhas) {
				nuNota = (BigDecimal) linha.getCampo(SklFieldName.NUNOTA);
				codEmp = (BigDecimal) linha.getCampo(SklFieldName.CODEMP);
				processor = new OrdemProducaoProcessor();
				
				processor.validaTarifaTerceiro(nuNota, codEmp);
			}
			
			msg = "Foi encontrado uma tarifa de terceiro.";
			action.appendMessageWithLineSeparator(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class SyncUsuarioAction implements AcaoRotinaJava {
	Action action = new Action(this.getClass().getCanonicalName());

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			BigDecimal codEmp;
			BigDecimal codFunc;
			for (Registro linha : linhas) {
				codEmp = BigDecimalUtil.getBigDecimal(linha.getCampo("CODEMP"));
				codFunc = BigDecimalUtil.getBigDecimal(linha.getCampo("CODFUNC"));
				
				Sync.alteraUsuario(codEmp, codFunc);
			}
			
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}

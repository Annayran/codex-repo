package tech.skala.action.modules.comercial.estoque.terceiros;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class ProcessProductCountWorksheetAttachmentAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao context) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		
		try {
			action.start();
			
			Registro[] rows = context.getLinhas();
			
			action.validateRows(rows);
			action.validateUniqueRowSelected(rows);
				
			ProcessProductCountWorksheetAttachmentHelper helper = new ProcessProductCountWorksheetAttachmentHelper(rows);
			helper.processAttachedSpreadsheet();
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			context.setMensagemRetorno(action.getMensagemRetorno());
		}
		
	}

}

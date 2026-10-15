package tech.skala.action.modules.inventario;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;
import tech.skala.modules.inventario.ContagemInventario;

public class ProcessaContagemAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws SkalaModelException {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			ContagemInventario contagemInventario;
			ContagemProcessor processor = new ContagemProcessor();
			for(Registro linha : linhas) {
				contagemInventario = new ContagemInventario();
				contagemInventario.loadByRecord(linha);
				
				processor.validarContagem(contagemInventario);
				processor.processaContagem(contagemInventario);
			}
			
		action.appendMessageWithLineSeparator("Contagem Processada.");

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
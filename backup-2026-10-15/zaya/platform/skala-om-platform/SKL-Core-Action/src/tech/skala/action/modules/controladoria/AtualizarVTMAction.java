package tech.skala.action.modules.controladoria;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class AtualizarVTMAction implements AcaoRotinaJava {

	private static final String			TITULO_MENSAGEM_CONFIRMACAO = "Confirmação de Atualização de VTM";
	private static final String			MENSAGEM_CONFIRMACAO = "Deseja confirmar a atualização de Valor Tributário Mínimo para os produtos nesta referência?";
	
	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		boolean isConfirmar = contexto.confirmarSimNao(TITULO_MENSAGEM_CONFIRMACAO, MENSAGEM_CONFIRMACAO, 1);
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			action.validateUniqueRowSelected(linhas);
			
			if(isConfirmar) {
				ProcessaPlanilhaVTMAnexo processor = new ProcessaPlanilhaVTMAnexo(linhas);
				processor.atualizaVTM();
				
				msg = "A Atualização do VTM foi finalizada.";
				action.appendMessage(msg);				
			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
package tech.skala.action.modules.manufatura.sequenciamento;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class ConfirmaSequenciamentoAction implements AcaoRotinaJava {
	private static final String			TITULO_MENSAGEM_CONFIRMACAO = "Confirmação de Ordens de Produção";
	private static final String			MENSAGEM_CONFIRMACAO = "Deseja confirmar as Ordens de Produção para os registros selecionados?";

	
	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		String msg;
		
		boolean isConfirmar = contexto.confirmarSimNao(TITULO_MENSAGEM_CONFIRMACAO, MENSAGEM_CONFIRMACAO, 1);
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			if(isConfirmar) {
				SequenciamentoOPInstanciaProcessor confirma = new SequenciamentoOPInstanciaProcessor(linhas);
				confirma.definirConcluido(true);
			}
			
		} catch (Exception e) {
			action.error(e);

		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
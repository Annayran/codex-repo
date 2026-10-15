package tech.skala.action.modules.manufatura.sequenciamento;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

public class RetornarOrdemProducaoParaFilaAction implements AcaoRotinaJava {
	private static final String			TITULO_MENSAGEM_CONFIRMACAO = "Retornar Ordens de Produção Para Fila";
	private static final String			MENSAGEM_CONFIRMACAO = "Deseja retornar as Ordens de Produção selecionadas para a fila?";
	
	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		String msg;
		
		boolean isConfirmar = contexto.confirmarSimNao(TITULO_MENSAGEM_CONFIRMACAO, MENSAGEM_CONFIRMACAO, 1);
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			if (isConfirmar) {
				SequenciamentoOPInstanciaProcessor processor = new SequenciamentoOPInstanciaProcessor(linhas);
				processor.definirConcluido(false);

				msg = "OP retornada para Fila de Ordens de Produção";
				action.appendMessageWithLineSeparator(msg);
			}


		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

	

}
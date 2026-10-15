package tech.skala.action.modules.manufatura.sequenciamento;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class AtualizarObservacaoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			String observacao = (String) contexto.getParam(SklFieldName.OBSERVACAO);
			
			SequenciamentoDeOP sequencia = null;
			SequenciamentoProcessor processor = null;
			for (Registro linha : linhas) {
				sequencia = new SequenciamentoDeOP();
				sequencia.loadByRecord(linha);
				
				processor = new SequenciamentoProcessor(sequencia);
				processor.atualizaObservacao(observacao);
			}
			
			msg = "Campo de Observação Atualizado.";
			action.appendMessageWithLineSeparator(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
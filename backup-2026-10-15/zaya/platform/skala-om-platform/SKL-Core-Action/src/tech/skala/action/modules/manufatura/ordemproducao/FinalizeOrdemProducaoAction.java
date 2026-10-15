package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import tech.skala.core.commons.extensions.Action;

public class FinalizeOrdemProducaoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		String titulo = "Finalizar OP";
		StringBuilder sbTexto = new StringBuilder();
		sbTexto.append("As Ordens de Produção selecionadas serão finalizadas<br>");
		sbTexto.append("<b>Sem movimentações de estoque</b>. <br>");
		sbTexto.append("Deseja continuar? <br>");
		boolean isConfirmar = contexto.confirmarSimNao(titulo, sbTexto.toString(), 1);
		
		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			if (isConfirmar) {

				BigDecimal userId = AuthenticationInfo.getCurrent().getUserID();
				String userName = AuthenticationInfo.getCurrent().getName();

				Collection<BigDecimal> ordensGeradas = new ArrayList<>();
				for (final Registro linha : linhas) {
					BigDecimal idiProc = (BigDecimal) linha.getCampo("IDIPROC");
					
					OrdemProducaoProcessor.finalizaOrdemProducao(idiProc);
					ordensGeradas.add(idiProc);
				}
				msg = String.format("Ordens de Produção finalizadas:<br>%s<br>%s-%s", ordensGeradas, userId, userName);
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

package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import tech.skala.core.commons.extensions.Action;

public class ReabreOrdemProducaoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		String titulo = "Reabrir OP";
		StringBuilder sbTexto = new StringBuilder();
		sbTexto.append("As Ordens de Produção selecionadas serão reabertas <br>");
		sbTexto.append("podendo gerar inconsistências nos lançamentos. <br>");
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
					ordensGeradas.add(idiProc);

					OrdemProducaoProcessor.reabrirOrdemProducao(idiProc);
				}
				msg = String.format("Ordens de Produção reabertas sem movimentação de estoque:<br>%s<br>%s-%s",
						ordensGeradas, userId, userName);
			} else {
				msg = "Processamento Cancelado";
			}
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
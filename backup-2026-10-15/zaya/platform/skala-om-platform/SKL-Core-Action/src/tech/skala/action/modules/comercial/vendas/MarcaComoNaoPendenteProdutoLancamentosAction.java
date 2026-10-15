package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.sql.NativeSql;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class MarcaComoNaoPendenteProdutoLancamentosAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		BigDecimal codProd = BigDecimalUtil.getBigDecimal(contexto.getParam(SklFieldName.CODPROD));
		BigDecimal codTipOper = BigDecimalUtil.getBigDecimal(contexto.getParam(SklFieldName.CODTIPOPER));
		BigDecimal codParc = BigDecimalUtil.getBigDecimal(contexto.getParam(SklFieldName.CODPARC));

		ExcluiProdutoLancamentoProcessor processor = new ExcluiProdutoLancamentoProcessor(codProd, codTipOper, codParc);

		boolean excluirProdutos = showMensagemDeConfirmacao(contexto);
		try {
			action.start();

			if (excluirProdutos) {
				processor.marcaProdutoComoNaoPendente();

				msg = "Os itens foram marcados como Não Pendente, com sucesso.";
				action.appendMessage(msg);
			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			processor.truncateTable();
			
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

	private boolean showMensagemDeConfirmacao(ContextoAcao contexto) throws SkalaModelException {
		BigDecimal codProd = BigDecimalUtil.getBigDecimal(contexto.getParam(SklFieldName.CODPROD));

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();

			BigDecimal qtdLancamentos = NativeSql.getBigDecimal("COALESCE(SUM(1), 0)", "EXC_ITEM_LANCAMENTOS",
					null);

			String tituloConfirmacao = "Marcar Produto como Não Pendente nos Lançamentos";
			String mensagemConfirmacao = "O Produto %s será <b>marcado como NÃO PENDENTE em %s lançamentos</b>.%nDeseja prosseguir com o processo?";
			
			return contexto.confirmarSimNao(tituloConfirmacao,
					String.format(mensagemConfirmacao, codProd, qtdLancamentos), 1);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível marcar produto como não pendente.", e);
		} finally {
			JapeSession.close(hnd);
		}

		return false;
	}

}
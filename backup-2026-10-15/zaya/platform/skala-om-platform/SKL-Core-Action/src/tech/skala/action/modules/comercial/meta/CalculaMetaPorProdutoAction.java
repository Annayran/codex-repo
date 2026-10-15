package tech.skala.action.modules.comercial.meta;

import java.util.LinkedList;
import java.util.List;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.beans.meta.MetaPorFamilia;
import tech.skala.core.commons.extensions.Action;

/**
 * Esta acao permite efetuar o calculo de valores de metas de venda para produtos de uma familia.
 * <p>
 * 
 * Esta acao permite efetuar o calculo de valores de metas de venda para produtos de uma familia
 * considerando a proporcao do produto em relacao aa carteira e aa media de venda do trimestre anterior.<br>
 * Iremos sempre considerar a carteira do dia anterior ao dia do calculo ({@code SYSDATE})<br>
 * e o trimestre anterior ao mes do calculo.
 * 
 * @author hiagoalexandre
 * @author cassiomenezes
 * @since 2023-04-27
 *
 */
public class CalculaMetaPorProdutoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		boolean usaCarteiraParaQtdPrev = "S".equalsIgnoreCase((String) contexto.getParam("CONFIRMACAO"));
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			MetasOrcamentoProcessor helper = new MetasOrcamentoProcessor(action);

			MetaPorFamilia metaPorFamilia;
			for (Registro linha: linhas) {
				metaPorFamilia = new MetaPorFamilia(true, usaCarteiraParaQtdPrev);
				metaPorFamilia.loadByRecord(linha);
				
				msg = String.format("%s produtos carregados dentro da família %s", metaPorFamilia.getProdutos().size(), metaPorFamilia.getCodFam());
				action.appendMessageWithLineSeparator(msg);
				
				helper.calculaMetaPorProduto(metaPorFamilia);
			}
			

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
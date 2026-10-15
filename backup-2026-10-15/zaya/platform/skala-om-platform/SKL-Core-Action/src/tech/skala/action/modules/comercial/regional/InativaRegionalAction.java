package tech.skala.action.modules.comercial.regional;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

/**
 * A classe {@code InativaRegionalAction} permite inativar uma estrutura de Regional de Vendas.
 * 
 * <p>
 * Utilizar esta classe para inativar uma Regional de Vendas ({@code AD_PLTGFREGVEN}) e todas sua estrutura, incluindo os
 * Centros de Resultado vinculados ({@code AD_PLTGFREGVENCR}).<br>
 * Basta selecionar um registro na tela e executar a ação.
 * </p>
 * 
 * <p>
 * <b>Importante</b>: esta acao <b>Nao</b> altera o cadastro padrao de Centro de Resultado ({@code TSICUS}).
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-06-30
 *
 */
public final class InativaRegionalAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());
		
		try {
			action.start();
			
			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);
			
			InativaRegionalModel model = new InativaRegionalModel(linhas);
			model.inativar();
			
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}

	}

}

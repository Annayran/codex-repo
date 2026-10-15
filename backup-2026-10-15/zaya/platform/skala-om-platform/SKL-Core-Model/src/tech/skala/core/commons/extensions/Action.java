package tech.skala.core.commons.extensions;

import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.util.time.Timer;

/**
 * Classe utilitaria utilizada para implementacao de customizacoes <b>Sankhya OM</b>.
 * <p>
 * 
 * Esta classe eh utilizada para implementar acoes do tipo <b>Rotina Java</b> e  
 * contem ferramentas comuns e uteis para o desenvolvimento.
 * <p>
 * 
 * @author cassiomenezes
 * @since 2022-05-07
 * 
 * @see Timer
 * @see Extension
 */
public final class Action extends Extension implements ActionButton {

	public Action(String className) {
		super(className);
	}

	@Override
	public void validateRows(Registro[] linhas) {
		if (linhas.length == 0)
			throw new IllegalArgumentException("Nenhum registro selecionado.");
		
	}

	@Override
	public void validateUniqueRowSelected(Registro[] linhas) {
		if (linhas.length > 1)
			throw new IllegalArgumentException("Somente 1 (um) registro deve ser selecionado.");
		
	}

}

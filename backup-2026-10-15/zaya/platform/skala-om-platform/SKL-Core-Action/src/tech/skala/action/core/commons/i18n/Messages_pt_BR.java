package tech.skala.action.core.commons.i18n;

import java.util.ListResourceBundle;

/**
 * A classe {@code Messages_pt_BR} eh uma implementacao da classe abstrata 
 * {@code java.util.ListResourceBundle}.
 * <p>
 * 
 * Esta classe implementa o metodo @getContents() que retorna uma mensagem 
 * conforme a chave informada.
 * 
 * @author cassiomenezes
 * @since 2022-06-05
 * @see java.util.ListResourceBundle
 *
 */
public class Messages_pt_BR extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { 
			{ "welcome", "Bem vindo ao ERP Grupo Skala" },
			{ "processing-canceled", "Processamento cancelado." }
			};
	}

}

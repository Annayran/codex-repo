package tech.skala.core.commons.i18n;

import java.util.ResourceBundle;

import br.com.sankhya.modelcore.util.I18nManager;

/**
 * A classe {@code I18nMessageFactory} prepara uma instancia de 
 * {@link I18nMessage} e implementa o metodo {@code getMessage(String key)}.
 * <p>
 * 
 * Esta classe implementa o padrao <i>Factory</i> para simplificar a criacao de 
 * um objeto que retorna <i>tokens</i> de internacionalizacao.<br>
 * Para utilizar basta utilizar o metodo {@code getInstance(String baseName)} 
 * informando o <i>baseName</i> com uma classe ou arquivo <i>{@code .properties}</i> 
 * i18n validos.
 * 
 * <pre>
 * {@code 
 * I18nMessage messages = I18nMessageFactory.getInstance(I18nMessage.MESSAGES_BASENAME);
 * System.out.println(messages.getMessage("processing"));
 * }
 * </pre>
 * 
 * @author cassiomenezes
 * @since 2022-06-05
 * 
 * @see tech.skala.core.commons.i18n.Messages_pt_BR
 * @see java.util.ListResourceBundle
 *
 */
public class I18nMessageFactory implements I18nMessage {
	private ResourceBundle					bundle;
	
	private I18nMessageFactory() {
		throw new IllegalStateException("Utility class");
	}
	
	private I18nMessageFactory(String baseName) {
		try {
			this.bundle = ResourceBundle.getBundle(baseName, I18nManager.getLocale());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private I18nMessageFactory(String baseName, ClassLoader loader) {
		try {
			this.bundle = ResourceBundle.getBundle(baseName, I18nManager.getLocale(), loader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static I18nMessage getInstance(String baseName) {
		return new I18nMessageFactory(baseName);
	}
	
	public static I18nMessage getInstance(String baseName, ClassLoader loader) {
		return new I18nMessageFactory(baseName, loader);
	}

	@Override
	public String getMessage(String key) {
		return this.bundle.getString(key);
	}

}

package tech.skala.action.core.commons.i18n;

import java.util.ResourceBundle;

import br.com.sankhya.modelcore.util.I18nManager;
import tech.skala.core.commons.i18n.I18nMessage;

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

	public static I18nMessage getInstance(String baseName) {
		return new I18nMessageFactory(baseName);
	}

	@Override
	public String getMessage(String key) {
		return this.bundle.getString(key);
	}

}

package tech.skala.action.modules.manufatura.produtoacabadoaproduzir;

import tech.skala.action.core.commons.i18n.I18nMessageFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProdutoAcabadoAProduzirExceptions {
public static final String				EXCEPTION_BASE_NAME = "tech.skala.action.modules.manufatura.i18n.ExceptionMessages";
	
	private ProdutoAcabadoAProduzirExceptions() {
		throw new IllegalStateException("Utility class");
	}

	public static class ProductNotFoundException extends SkalaModelException {

		private static final long serialVersionUID = 2579899157636755762L;

		public ProductNotFoundException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("product-not-found"));
		}
		
		public ProductNotFoundException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("product-not-found"), error);
		}
	}
}

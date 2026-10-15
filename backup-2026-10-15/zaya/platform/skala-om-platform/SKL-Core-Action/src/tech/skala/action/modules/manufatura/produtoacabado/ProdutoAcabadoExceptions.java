package tech.skala.action.modules.manufatura.produtoacabado;

import tech.skala.action.core.commons.i18n.I18nMessageFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProdutoAcabadoExceptions {
	public static final String				EXCEPTION_BASE_NAME = "tech.skala.action.modules.manufatura.i18n.ExceptionMessages";
	

	private ProdutoAcabadoExceptions() {
		throw new IllegalStateException("Utility class");
	}

	public static class MultipleStandardProductiveProcessesFoundException extends SkalaModelException {

		private static final long serialVersionUID = -7442020995351730373L;

		public MultipleStandardProductiveProcessesFoundException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("multiple-standard-productive-processes-found"), args), error);
		}
		
		public MultipleStandardProductiveProcessesFoundException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("multiple-standard-productive-processes-found"), args));
		}

	}
	
	public static class StandardProductiveProcessNotFoundException extends SkalaModelException {

		private static final long serialVersionUID = -7442020995351730373L;

		public StandardProductiveProcessNotFoundException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("standard-productive-process-not-found"), args), error);
		}
		
		public StandardProductiveProcessNotFoundException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("standard-productive-process-not-found"), args));
		}

	}
}

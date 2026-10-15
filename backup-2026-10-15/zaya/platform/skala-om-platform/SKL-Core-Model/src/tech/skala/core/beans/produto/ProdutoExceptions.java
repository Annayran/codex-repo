package tech.skala.core.beans.produto;

import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.i18n.I18nMessageFactory;

public class ProdutoExceptions {
	public static final String				EXCEPTION_BASE_NAME = "tech.skala.core.beans.produto.Messages";

	private ProdutoExceptions() {
		throw new IllegalStateException("Utility class");
	}

	public static class AlternativeVolumeNotFoundException extends SkalaModelException {


		private static final long serialVersionUID = 4048314440898634062L;

		public AlternativeVolumeNotFoundException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("alternative-volume-not-found"), args), error);
		}

	}

}

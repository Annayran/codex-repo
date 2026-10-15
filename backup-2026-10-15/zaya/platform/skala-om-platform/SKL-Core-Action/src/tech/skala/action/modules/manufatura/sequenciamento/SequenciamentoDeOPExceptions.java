package tech.skala.action.modules.manufatura.sequenciamento;

import tech.skala.action.core.commons.i18n.I18nMessageFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class SequenciamentoDeOPExceptions {
public static final String				EXCEPTION_BASE_NAME = "tech.skala.action.modules.manufatura.i18n.ExceptionMessages";
	

	private SequenciamentoDeOPExceptions() {
		throw new IllegalStateException("Utility class");
	}

	public static class SequenciamentoNaoInicializadoException extends SkalaModelException {

		private static final long serialVersionUID = -6202921561135485258L;

		public SequenciamentoNaoInicializadoException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("uninitialized-sequencing"), error);
		}
		
		public SequenciamentoNaoInicializadoException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("uninitialized-sequencing"));
		}

	}
	
	public static class SequenciamentoNaoAtualizadoException extends SkalaModelException {

		private static final long serialVersionUID = 2803257958041255805L;

		public SequenciamentoNaoAtualizadoException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("sequencing-not-updated"), args), error);
		}
		
		public SequenciamentoNaoAtualizadoException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("sequencing-not-updated"), args));
		}

	}
	
	public static class SequenciamentoNaoIncluidoException extends SkalaModelException {

		private static final long serialVersionUID = 7189005365879011038L;

		public SequenciamentoNaoIncluidoException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("sequencing-not-included"), error);
		}
		
		public SequenciamentoNaoIncluidoException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("sequencing-not-included"));
		}

	}
}

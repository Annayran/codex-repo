package tech.skala.action.modules.manufatura.pcp.programacaosimplificada;

import tech.skala.action.core.commons.i18n.I18nMessageFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProgramacaoProducaoSimplificadaExceptions {
	public static final String				EXCEPTION_BASE_NAME = "tech.skala.action.modules.manufatura.i18n.ExceptionMessages";

	private ProgramacaoProducaoSimplificadaExceptions() {
		throw new IllegalStateException("Utility class");
	}

	public static class StandardCapacityNotFoundException extends SkalaModelException {

		private static final long serialVersionUID = 2579899157636755762L;

		public StandardCapacityNotFoundException(Object... args) {
			super(String.format(
					I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("standard-capacity-not-found"),
					args));
		}

		public StandardCapacityNotFoundException(Throwable error, Object... args) {
			super(String.format(
					I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("standard-capacity-not-found"),
					args), error);
		}
	}

	public static class ProgramacaoNaoLocalizadaException extends SkalaModelException {

		private static final long serialVersionUID = -4999058897531765841L;

		public ProgramacaoNaoLocalizadaException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("schedule-not-found"),
					args));
		}

		public ProgramacaoNaoLocalizadaException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("schedule-not-found"),
					args), error);
		}
	}

	public static class OrdensProducaoNaoIncluidasNaProgramacaoException extends SkalaModelException {

		private static final long serialVersionUID = -7788251218492715178L;

		public OrdensProducaoNaoIncluidasNaProgramacaoException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("schedule-not-included"), error);
		}

		public OrdensProducaoNaoIncluidasNaProgramacaoException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("schedule-not-included"));
		}
	}

	public static class ProgramacaoNaoAtualizadaException extends SkalaModelException {

		private static final long serialVersionUID = 2353793268745370603L;

		public ProgramacaoNaoAtualizadaException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("schedule-not-update"), error);
		}

		public ProgramacaoNaoAtualizadaException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("schedule-not-update"));
		}
	}

}

package tech.skala.action.modules.manufatura.ordemproducao;

import tech.skala.action.core.commons.i18n.I18nMessageFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class OrdemProducaoExceptions {
	
	public static final String				EXCEPTION_BASE_NAME = "tech.skala.action.modules.manufatura.i18n.ExceptionMessages";
	
	private OrdemProducaoExceptions() {
		throw new IllegalStateException();
	}
	
	public static class StatusOrdemProducaoInvalidException extends SkalaModelException {

		private static final long serialVersionUID = -6100801789473184797L;

		public StatusOrdemProducaoInvalidException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("status-not-valid"), error, args));
		}
		
		public StatusOrdemProducaoInvalidException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("status-not-valid"), args));
		}
	}
	
	public static class OrdemProducaoNaoCriadaException extends SkalaModelException {

		private static final long serialVersionUID = 3997307115915583979L;

		public OrdemProducaoNaoCriadaException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("production-order-not-created"), error, args));
		}
		
		public OrdemProducaoNaoCriadaException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("production-order-not-created"), args));
		}
	}
	
	public static class OrdemProducaoNaoCanceladaException extends SkalaModelException {

		private static final long serialVersionUID = 5364719687803067169L;

		public OrdemProducaoNaoCanceladaException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("production-order-not-canceled"), error, args));
		}
		
		public OrdemProducaoNaoCanceladaException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("production-order-not-canceled"), args));
		}
	}
	
	public static class LoteMaximoJaUltilizadoException extends SkalaModelException {
		
		private static final long serialVersionUID = 843188850029497730L;
		
		public LoteMaximoJaUltilizadoException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("lote-max-for-product-utilized"), error, args));
		}
		
		public LoteMaximoJaUltilizadoException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("lote-max-for-product-utilized"), args));
		}
	}
	
	public static class LoteMaiorQueUltimoNumeroUltilizadoException extends SkalaModelException {
		
		private static final long serialVersionUID = -8443444056334642402L;

		public LoteMaiorQueUltimoNumeroUltilizadoException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("lote-max-bigger-then-last-number"), error, args));
		}
		
		public LoteMaiorQueUltimoNumeroUltilizadoException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("lote-max-bigger-then-last-number"), args));
		}
	}
	
	public static class LoteNaoAtualizadoException extends SkalaModelException {
		
		private static final long serialVersionUID = 1313492221251836327L;

		public LoteNaoAtualizadoException(Throwable error, Object... args) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("lote-not-updated"), error);
		}
		
		public LoteNaoAtualizadoException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("lote-not-updated"));
		}
	}
}

package tech.skala.action.modules.manufatura.lancamentodeop;

import tech.skala.action.core.commons.i18n.I18nMessageFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * 
 * @author hiagoalexandre
 * @author cassiomenezes
 * @since 2022-07-29
 *
 */
public class LancamentoDeOPExceptions {
public static final String				EXCEPTION_BASE_NAME = "tech.skala.action.modules.manufatura.i18n.ExceptionMessages";
	

	private LancamentoDeOPExceptions() {
		throw new IllegalStateException("Utility class");
	}

	public static class ProgramacaoNaoEstaEmElaboracaoException extends SkalaModelException {

		private static final long serialVersionUID = -9039771226224701428L;

		public ProgramacaoNaoEstaEmElaboracaoException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("schedule-not-in-elaboration"), args), error);
		}
		
		public ProgramacaoNaoEstaEmElaboracaoException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("schedule-not-in-elaboration"), args));
		}

	}

	public static class QuantidadeDeLotesMenorQueUmException extends SkalaModelException {

		private static final long serialVersionUID = -4133078722208541970L;

		public QuantidadeDeLotesMenorQueUmException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("batch-less-than-one"));
		}

	}
	
	public static class LancamentoProducaoNaoGeradoException extends SkalaModelException {

		private static final long serialVersionUID = -1922763547011933376L;

		public LancamentoProducaoNaoGeradoException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("unable-generate-production-launch"), error);
		}

	}
	
	public static class ProductionOrderNotIncludedForProductException extends SkalaModelException {

		private static final long serialVersionUID = 3465894547705477011L;
		
		public ProductionOrderNotIncludedForProductException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("production-order-not-included-for-product"), args), error);
		}
		
	}
	
	public static class ProductionOrderNotIncludedException extends SkalaModelException {

		private static final long serialVersionUID = 3465894547705477011L;
		
		public ProductionOrderNotIncludedException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("production-order-not-included"), error);
		}
		
	}

	public static class WorkCenterNotSetException extends SkalaModelException {

		private static final long serialVersionUID = 1379198116328333813L;

		public WorkCenterNotSetException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("workcenter-not-set"), args), error);
		}

	}

	public static class DeleteProductionOrderLaunchNotPossibleException extends SkalaModelException {

		private static final long serialVersionUID = 2371113324928691531L;

		public DeleteProductionOrderLaunchNotPossibleException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("delete-production-order-launch-not-possible"), args), error);			
		}

	}

	public static class ProductionOrderCreationNotPossibleException extends SkalaModelException {

		private static final long serialVersionUID = 8199414956945753835L;

		public ProductionOrderCreationNotPossibleException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("production-order-creation-not-possible"), args), error);			
		}

	}

}

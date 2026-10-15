package tech.skala.action.modules.wms.extrato;

import tech.skala.action.core.commons.exceptions.ActionException;
import tech.skala.action.core.commons.i18n.I18nMessageFactory;

public class ExtratoWmsExceptions {
	public static final String				EXCEPTION_BASE_NAME = "tech.skala.action.modules.wms.i18n.ExceptionMessages";
	
	private ExtratoWmsExceptions() {
		throw new IllegalStateException("Utility class");
	}

	public static class ReceivingNotFoundException extends ActionException {

		private static final long serialVersionUID = 4331426414370889144L;

		public ReceivingNotFoundException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("receiving-not-found"), error);
		}
	}

	public static class TaskNotFoundException extends ActionException {

		private static final long serialVersionUID = 1642648334978640719L;

		public TaskNotFoundException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("task-not-found"), error);
		}
	}

	public static class InvoiceNotFoudException extends ActionException {

		private static final long serialVersionUID = 5329439720482159851L;

		public InvoiceNotFoudException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invoice-not-found"), error);
		}

	}
}

package tech.skala.core.commons.dd;

import tech.skala.core.commons.exceptions.SkalaModelException;

public class DataDictionaryExceptions {
	public static final String				EXCEPTION_BASE_NAME = "tech.skala.core.commons.dd.Messages";
	
	private DataDictionaryExceptions() {
		throw new IllegalStateException("Utility class");
	}
	
	public static class InvalidFieldException extends SkalaModelException {

		private static final long serialVersionUID = 4331426414370889144L;

		public InvalidFieldException() {
			super(EXCEPTION_BASE_NAME, "invalid-field");
		}
	}
	
	public static class NullPrimaryKeyException extends SkalaModelException {

		private static final long serialVersionUID = 6859259261009101632L;
		
		public NullPrimaryKeyException() {
			super(EXCEPTION_BASE_NAME, "null-primary-key");
		}
	}

	public static class NullValueObjectException extends SkalaModelException {

		private static final long serialVersionUID = -3659714949514725589L;

		public NullValueObjectException() {
			super(EXCEPTION_BASE_NAME, "null-value-object");
		}

	}
	
	public static class RecordNotFoundException extends SkalaModelException {

		private static final long serialVersionUID = 2461643915640320849L;
		
		public RecordNotFoundException() {
			super(EXCEPTION_BASE_NAME, "record-not-found");
		}

		public RecordNotFoundException(Throwable error) {
			super(EXCEPTION_BASE_NAME, "record-not-found", error);
		}

	}

	public static class UpdateNotPossibleException extends SkalaModelException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5732782423412640565L;

		public UpdateNotPossibleException() {
			super(EXCEPTION_BASE_NAME, "update-fail");
		}

		public UpdateNotPossibleException(Throwable error) {
			super(EXCEPTION_BASE_NAME, "update-fail", error);
		}

	}
	
	public static class InsertNotPossibleException extends SkalaModelException {


		private static final long serialVersionUID = -8714904311637634103L;

		public InsertNotPossibleException(Throwable error) {
			super(EXCEPTION_BASE_NAME, "insert-fail", error);
		}

	}

}

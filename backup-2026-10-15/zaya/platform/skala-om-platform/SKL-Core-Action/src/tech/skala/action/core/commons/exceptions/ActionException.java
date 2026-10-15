package tech.skala.action.core.commons.exceptions;

import tech.skala.core.commons.exceptions.SkalaModelException;

public class ActionException extends SkalaModelException {

	private static final long serialVersionUID = 5444761268471233736L;

	protected ActionException(String message) {
		super(message);
	}

	protected ActionException(String message, Throwable error) {
		super(message, error);
	}

	public static void throwMe(String message, Throwable error) throws SkalaModelException {
		String errorMsg = buildErrorMessage(message, error);
		
		SkalaModelException exception = new ActionException(errorMsg);
		exception.initCause(error);
		
		throw exception;
	}
}

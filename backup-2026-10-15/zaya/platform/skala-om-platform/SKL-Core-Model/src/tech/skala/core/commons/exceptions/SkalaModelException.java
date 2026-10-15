package tech.skala.core.commons.exceptions;


import com.sankhya.util.StringUtils;
import tech.skala.core.commons.i18n.I18nMessage;
import tech.skala.core.commons.i18n.I18nMessageFactory;
import tech.skala.core.util.string.SkalaStringUtils;

/**
 * A classe {@code SkalaModelException} e suas subclasses sao uma forma de {@code Throwable}.
 * <p>
 * 
 * A classe {@code SkalaModelException} e qualquer subclasse indicam situacoes especificas de 
 * <b>Regras de Negocio</b> que devem ser tratadas.<br>
 * A classe {@code SkalaModelException} podera ser utilizada diretamente, para encapsular 
 * excecoes genericas:
 * <pre>
 * <code>
 * ...
 * } catch (Exception e) {
 * 	SkalaModelException.throwMe("Mensagem especifica");
 * }
 * </code>
 * </pre>
 * 
 * Ou podera ser extendida para excecoes especificas:
 * <pre>
 * <code>
 * public class ChaveNfeDuplicidadeException extends SkalaModelException {
 * 	//seu codigo aqui
 * }
 * </code>
 * </pre>
 * 
 * @author cassiomenezes
 * @since 2022-02-06
 * @see Exception
 * @see Throwable
 *
 */
public class SkalaModelException extends Exception implements SkalaException {

	private static final long serialVersionUID = -1333183498015225048L;
	
	protected SkalaModelException() {
		super();
	}

	public SkalaModelException(String message) {
		super(message);
	}
	
	public SkalaModelException(String message, Throwable error) {
		super(buildErrorMessage(message, error), error);
	}
	
	public SkalaModelException(String baseName, String key) {
		super(I18nMessageFactory.getInstance(baseName).getMessage(key));
	}

	public SkalaModelException(String baseName, String key, Throwable error) {
		super(buildErrorMessage(I18nMessageFactory.getInstance(baseName).getMessage(key), error), error);
	}

	public static void throwMe(ClassLoader loader, String baseName, String key, Throwable error) throws SkalaModelException {
		throwMe(I18nMessageFactory.getInstance(baseName, loader).getMessage(key), error);
	}

	public static void throwMe(String baseName, String key, Throwable error) throws SkalaModelException {
		throwMe(I18nMessageFactory.getInstance(baseName).getMessage(key), error);
	}

	public static void throwMe(String message, Throwable error) throws SkalaModelException {
		String errorMsg = buildErrorMessage(message, error);
		
		SkalaModelException exception = new SkalaModelException(errorMsg);
		exception.initCause(error);
		
		throw exception;
	}

	public static String buildErrorMessage(final String exceptionMessage, Throwable error) {
		final I18nMessage exceptionMessages = I18nMessageFactory.getInstance(I18nMessage.EXCEPTION_MESSAGES_BASENAME);
		
		StringBuilder sbMessage;
		if (exceptionMessage == null) {
			sbMessage = new StringBuilder();
		} else {
			sbMessage = new StringBuilder(exceptionMessage);
			sbMessage.append(SkalaStringUtils.lineSeparator());
		}
		
		String stackTraceMessage = null;
		String errorMessage = error.getMessage();
		if (StringUtils.isEmpty(errorMessage)) {
			if (error instanceof NullPointerException) {
				errorMessage = exceptionMessages.getMessage("npe");
			} else if (StringUtils.isEmpty(exceptionMessage)) {
				errorMessage = exceptionMessages.getMessage("no-error-message");
			} else {
				errorMessage = exceptionMessages.getMessage("unidentified-error");
			}

			StackTraceElement[] stack = error.getStackTrace();
			stackTraceMessage = String.format("%s.%s -> line %d", stack[0].getClassName(), stack[0].getMethodName(),
					stack[0].getLineNumber());
		}
		
		errorMessage = String.format("<b>%s</b>: %s", exceptionMessages.getMessage("cause"), errorMessage);
		sbMessage.append(errorMessage);
		if (stackTraceMessage != null) {
			sbMessage.append(SkalaStringUtils.lineSeparator());
			sbMessage.append(stackTraceMessage);
		}

		return sbMessage.toString();
	}
	
}

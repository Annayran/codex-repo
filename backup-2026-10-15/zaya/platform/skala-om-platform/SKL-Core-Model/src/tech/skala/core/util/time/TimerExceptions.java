package tech.skala.core.util.time;

import tech.skala.core.commons.exceptions.SkalaModelException;

public class TimerExceptions {

	private TimerExceptions() {
		throw new IllegalStateException("Utility class");
	}
	
	public static class TemporizadorNaoIniciadoException extends SkalaModelException {

		private static final long serialVersionUID = -5813178850339039963L;
		
		protected TemporizadorNaoIniciadoException(String baseName, String key) {
			super(baseName, key);
		}

	}

}

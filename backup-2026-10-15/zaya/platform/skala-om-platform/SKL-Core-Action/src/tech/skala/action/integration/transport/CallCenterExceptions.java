package tech.skala.action.integration.transport;

import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.i18n.I18nMessageFactory;

public class CallCenterExceptions {
	
	public static final String				EXCEPTION_BASE_NAME = "tech.skala.action.integration.transport.ExceptionMessages_pt_BR";

	private CallCenterExceptions() {
		throw new IllegalStateException();
	}
	
	public static class ArquivoEDIInvalidoException extends SkalaModelException {

		private static final long serialVersionUID = 6486051629221497248L;

		public ArquivoEDIInvalidoException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invalid-edi-file"), error);
		}
		
		public ArquivoEDIInvalidoException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("invalid-edi-file"));
		}
	}
	
	public static class ArquivoMovidoException extends SkalaModelException {

		private static final long serialVersionUID = -2008371999405421185L;

		public ArquivoMovidoException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("moved-file"), error);
		}
		
		public ArquivoMovidoException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("moved-file"));
		}
	}
	
	public static class OcorrenciaEntradaNaoGeradaException extends SkalaModelException {

		private static final long serialVersionUID = -1729041388047836917L;

		public OcorrenciaEntradaNaoGeradaException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("entry-event-not-saved"), error);
		}
		
		public OcorrenciaEntradaNaoGeradaException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("entry-event-not-saved"));
		}
	}
	
	public static class OcorrenciaEntradaNaoProcessadaException extends SkalaModelException {

		private static final long serialVersionUID = -3214230207602834563L;

		public OcorrenciaEntradaNaoProcessadaException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("unprocessed-occurrence"), error);
		}
		
		public OcorrenciaEntradaNaoProcessadaException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("unprocessed-occurrence"));
		}
	}
	
	public static class CallCenterNaoGeradoException extends SkalaModelException {

		private static final long serialVersionUID = -798923502240062643L;

		public CallCenterNaoGeradoException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("callcenter-not-saved"), error);
		}
		
		public CallCenterNaoGeradoException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("callcenter-not-saved"));
		}
	}
	
	public static class NotaNaoRelacionadaCallCenterException extends SkalaModelException {

		private static final long serialVersionUID = -798923502240062643L;

		public NotaNaoRelacionadaCallCenterException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("unrelated-note"), error);
		}
		
		public NotaNaoRelacionadaCallCenterException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("unrelated-note"));
		}
	}
	
	public static class CallCenterNaoLocalizadoException extends SkalaModelException {

		private static final long serialVersionUID = 2203691149958775796L;

		public CallCenterNaoLocalizadoException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("unable-fetch-callcenter"), error);
		}
		
		public CallCenterNaoLocalizadoException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("unable-fetch-callcenter"));
		}
	}
	
	public static class OcorrenciaCallCenterNaoGeradaException extends SkalaModelException {

		private static final long serialVersionUID = 475914833891004280L;

		public OcorrenciaCallCenterNaoGeradaException(Throwable error) {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("occurrence-callcenter-not-processed"), error);
		}
		
		public OcorrenciaCallCenterNaoGeradaException() {
			super(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("occurrence-callcenter-not-processed"));
		}
	}
}

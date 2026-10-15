package tech.skala.core.commons.extensions;

import java.util.logging.Logger;

import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.i18n.I18nMessage;
import tech.skala.core.commons.i18n.I18nMessageFactory;
import tech.skala.core.util.string.SkalaStringUtils;
import tech.skala.core.util.time.Timer;
import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;

/**
 * A classe abstrata {@code Extension} deve ser extendida para o desenvolvimento 
 * de <b>Botoes de Acao</b> e <b>Acoes Agendadas</b>
 * <p>
 * Extender a classe {@code Extension} para criar classes concretas que irao encapsular 
 * metodos comuns que poderao ser utilizados nas implementacoes.
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-06-16
 * 
 */
public abstract class Extension {
	protected static final String			MESSAGES_BASENAME = "tech.skala.core.commons.extensions.Messages";

	private final Timer						timer;
	private final Logger					logger;
	private final StringBuilder				sbMessage;
	private final I18nMessage				i18nMessages;

	private String							mensagemRetorno;

	protected Extension(final String className) {
		this.timer = new Timer();
		this.i18nMessages = I18nMessageFactory.getInstance(MESSAGES_BASENAME);
		this.logger = Logger.getLogger(className);
		this.sbMessage = new StringBuilder();
	}
	
	protected Logger getLogger() {
		return logger;
	}

	/*
	 * public getters
	 */
	/**
	 * Este metodo retorna uma mensagem construida durante o processamento.
	 * <p>
	 * 
	 * @return Uma mensagem de retorno a ser apresentada ao usuario.
	 * @see #appendMessage(String)
	 * @see #appendMessageWithLineSeparator(String)
	 */
	public String getMensagemRetorno() {
		return mensagemRetorno;
	}

	/*
	 * private methods; control state.
	 */
	private void clearSbMessage() {
		this.sbMessage.setLength(0);
	}
	
	private void stopTimer() throws TemporizadorNaoIniciadoException {
		timer.stop();
		
		String msg = String.format("<b>%s</b>: %s", i18nMessages.getMessage("time-elapsed"), timer.getElapsedTime());
		appendMessageWithLineSeparator(msg);
	}
	
	/*
	 * public methods; control state.
	 */
	/**
	 * Permite adicionar mensagens para montar a mensagem final ao usuario.
	 * <p>
	 * 
	 * A mensagem a ser apresentada ao final do processamento pode ser construida 
	 * durante o processamento.<br>
	 * Este metodo anexa mensagens de forma sequencial e pode ser necessario acrescentar 
	 * um espaco branco ao final.
	 * 
	 * @param message Mensagem ou texto a ser adicionado aa mensagem geral da acao.
	 * @see #appendMessageWithLineSeparator
	 */
	public void appendMessage(String message) {
		this.sbMessage.append(message);
		this.mensagemRetorno = sbMessage.toString();
	}
	
	/**
	 * Permite adicionar mensagens para montar a mensagem final ao usuario.
	 * <p>
	 * 
	 * A mensagem a ser apresentada ao final do processamento pode ser construida 
	 * durante o processamento.<br>
	 * Este metodo anexa uma mensagem e adiciona, juntamente, um separador de linha.
	 * 
	 * @param message Mensagem ou texto a ser adicionado aa mensagem geral da acao.
	 * @see java.lang.System#getProperty(String)
	 * @see #appendMessage
	 */
	public void appendMessageWithLineSeparator(String message) {
		appendMessage(message);
		appendMessage(SkalaStringUtils.lineSeparator());
	}
	
	/**
	 * Retorna o tempo conforme classe utilitaria {@code tech.skala.core.util.time.Timer.getElapsedTime()}
	 * <p>
	 * 
	 * @return {@code String} apresentando o tempo decorrido no formato {@code HH:mm:ss}
	 * @see tech.skala.core.util.time.Timer#getElapsedTime() Timer.getElapsedTime()
	 */
	public String getElapsedTime() {
		return timer.getElapsedTime();
	}

	public void start() {
		clearSbMessage();
		this.timer.start();
		
		this.logger.info(i18nMessages.getMessage("processing"));
		appendMessageWithLineSeparator(i18nMessages.getMessage("successful-processing"));
	}

	public void error(Exception e) {
		clearSbMessage();

		String errMsg = SkalaModelException.buildErrorMessage(i18nMessages.getMessage("record-not-processed"), e);
		appendMessageWithLineSeparator(errMsg);
		
		this.logger.severe(errMsg);
		e.printStackTrace();
	}
	
	public void stop() throws TemporizadorNaoIniciadoException {
		stopTimer();
		
		this.logger.info(mensagemRetorno);
	}

}

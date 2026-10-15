package tech.skala.core.util.time;

import com.sankhya.util.TimeUtils;

import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;

/**
 * A classe {@code Timer} eh utilizada para apresentar o tempo gasto entre inicio e final 
 * de sua ativacao.
 * 
 * <p>
 * A classe {@code Timer} pode ser utilizada em qualquer ponto de um programa quando for necessario 
 * medir um itervalo de tempo de execucao de qualquer tarefa.<br>
 * Para isto esta classe deve ser instanciada e os metodos {@code start()} e {@code stop()} 
 * deverao ser chamados respectivamente antes e depois do ponto de medicao.
 * </p>
 * 
 * <pre>{@code
 * public void exemplo() {
 * 	Timer timer = new Timer();
 * 	timer.start();
 *
 * 	//seu codigo aqui
 *
 * 	timer.stop();
 * 	System.out.println(timer.getElapsedTime());
 * }
 * }</pre>
 * 
 * @author cassiomenezes
 * @since 2022-05-07
 *
 */
public final class Timer {
	private static final String				EXCEPTION_BASE_NAME = "tech.skala.core.util.time.TimerExceptionMessages";
	
	private long							initTime;
	private long							endTime;
	private long							elapsedTime;
	private boolean							running;

	public boolean isRunning() {
		return running;
	}

	/**
	 * Inicia a contagem de tempo.
	 * 
	 */
	public void start() {
		if (!isRunning()) {
			this.initTime = System.currentTimeMillis();
			this.running = true;
		}
	}

	/**
	 * Finaliza a contagem de tempo e formata a apresentacao no formato {@code HH:mm:ss}
	 * @throws TemporizadorNaoIniciadoException Excecao disparada se houver tentativa de parar 
	 * o temporizador sem te-lo iniciado antes.
	 * 
	 */
	public void stop() throws TemporizadorNaoIniciadoException {
		checkTimerNotStarted();
		
		getLapTime();
		reset();
	}

	/**
	 * @throws TemporizadorNaoIniciadoException
	 */
	private void checkTimerNotStarted() throws TemporizadorNaoIniciadoException {
		if (!isRunning())
			throw new TimerExceptions.TemporizadorNaoIniciadoException(EXCEPTION_BASE_NAME, "timer-not-started");
	}

	/*
	 * 
	 */
	private void getLapTime() {
		if (isRunning()) {
			if (initTime == 0L)
				this.initTime = System.currentTimeMillis();
			
			this.endTime = System.currentTimeMillis();
			this.elapsedTime = endTime - initTime;
		}
	}
	
	public void reset() {
		this.running = false;
		this.initTime = 0L;
		this.endTime = 0L;
	}
	
	/**
	 * Retorna o tempo decorrido como {@code String} formatada.
	 * <p>
	 * 
	 * Caso cronometro esteja em execucao <pre>{@code isRunning = true}</pre> retorna 
	 * o tempo decorrido no momento atual.<br>
	 * 
	 * @return {@code String} apresentando o tempo decorrido no formato {@code HH:mm:ss}
	 */
	public String getElapsedTime() {
		String elapsedTimeStr;
		try {
			getLapTime();
			elapsedTimeStr = TimeUtils.milisegundos2HHmmss(elapsedTime);
		} catch (Exception e) {
			elapsedTimeStr = "00:00:00";
		}
		
		return elapsedTimeStr;
	}
	
	/**
	 * Retorna o tempo <b>parcial</b> decorrido como {@code String} formatada.
	 * <p>
	 * 
	 * Caso cronometro esteja em execucao <pre>{@code isRunning = true}</pre> retorna 
	 * o tempo decorrido no momento atual.
	 * 
	 * @return {@code String} apresentando o tempo decorrido no formato {@code HH:mm:ss}
	 * @throws TemporizadorNaoIniciadoException caso o temporizador nao tenha sido iniciado.
	 */
	public String lap() throws TemporizadorNaoIniciadoException {
		checkTimerNotStarted();
		
		return getElapsedTime();
	}
}

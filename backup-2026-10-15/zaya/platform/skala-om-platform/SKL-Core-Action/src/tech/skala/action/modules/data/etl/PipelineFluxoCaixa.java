package tech.skala.action.modules.data.etl;

import tech.skala.core.util.string.SkalaStringUtils;
import tech.skala.core.util.time.Timer;
import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;

/**
 * 
 * @author hiagoalexandre
 * @since 2023-06-13
 */
public class PipelineFluxoCaixa {
	
	private PipelineFluxoCaixa() {
		throw new IllegalStateException("Utility class");
	}

	public static String doPipeline() throws TemporizadorNaoIniciadoException {
		StringBuilder sbMessage = new StringBuilder();
		String msg;
		
		Timer timer = new Timer();
		timer.start();
		
		sbMessage.append("Atualização de Lançamentos Pendentes.");
		sbMessage.append(SkalaStringUtils.lineSeparator());
		
		msg = PipelineProcessor.atualizaLancamentosPendentesFinanceiro();
		msg = String.format("%s %s", timer.lap(), msg);
		sbMessage.append(msg);
		sbMessage.append(SkalaStringUtils.lineSeparator());
		
		timer.reset();
		timer.start();

		sbMessage.append("Atualização de Lançamentos Baixados.");
		sbMessage.append(SkalaStringUtils.lineSeparator());

		msg = PipelineProcessor.atualizaLancamentosBaixadosFinanceiro();
		msg = String.format("%s %s", timer.lap(), msg);
		sbMessage.append(msg);
		sbMessage.append(SkalaStringUtils.lineSeparator());

		timer.stop();
		
		return sbMessage.toString();
	}

}

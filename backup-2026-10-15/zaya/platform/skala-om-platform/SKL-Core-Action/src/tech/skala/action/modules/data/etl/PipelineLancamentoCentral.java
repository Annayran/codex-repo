package tech.skala.action.modules.data.etl;

import java.util.ArrayList;
import java.util.List;

import tech.skala.core.util.string.SkalaStringUtils;
import tech.skala.core.util.time.Timer;
import tech.skala.core.util.time.TimerExceptions.TemporizadorNaoIniciadoException;

/**
 * 
 * @author cassiomenezes
 * @author hiagoalexandre
 * @since 2023-03-29
 */
public class PipelineLancamentoCentral {
	
	private PipelineLancamentoCentral() {
		throw new IllegalStateException("Utility class");
	}

	public static PipelineReturn doPipeline() throws TemporizadorNaoIniciadoException {
		List<PipelineReturn> retornos = new ArrayList<>();
		
		StringBuilder sbMessage = new StringBuilder();
		String msg;
		
		Timer timer = new Timer();
		timer.start();
		
		sbMessage.append("Atualização de Lançamentos.");
		sbMessage.append(SkalaStringUtils.lineSeparator());
		
		PipelineReturn pipelineReturn = PipelineProcessor.atualizaLancamentosCentral();
		msg = String.format("%s %s", timer.lap(), pipelineReturn.getMessage());
		sbMessage.append(msg);
		sbMessage.append(SkalaStringUtils.lineSeparator());
		retornos.add(pipelineReturn);
		
		timer.reset();
		timer.start();

		sbMessage.append("Atualização de Estoques.");
		sbMessage.append(SkalaStringUtils.lineSeparator());

		pipelineReturn = PipelineProcessor.atualizaEstoque();
		msg = String.format("%s %s", timer.lap(), pipelineReturn.getMessage());
		sbMessage.append(msg);
		sbMessage.append(SkalaStringUtils.lineSeparator());
		retornos.add(pipelineReturn);
		
		timer.reset();
		timer.start();

		sbMessage.append("Atualização de Carteira.");
		sbMessage.append(SkalaStringUtils.lineSeparator());

		pipelineReturn = PipelineProcessor.atualizaCarteira();
		msg = String.format("%s %s", timer.lap(), pipelineReturn.getMessage());
		sbMessage.append(msg);
		sbMessage.append(SkalaStringUtils.lineSeparator());
		retornos.add(pipelineReturn);

		timer.reset();
		timer.start();

		sbMessage.append("Atualização de Entradas Pendentes.");
		sbMessage.append(SkalaStringUtils.lineSeparator());

		pipelineReturn = PipelineProcessor.atualizaEntradaPendente();
		msg = String.format("%s %s", timer.lap(), pipelineReturn.getMessage());
		sbMessage.append(msg);
		sbMessage.append(SkalaStringUtils.lineSeparator());
		retornos.add(pipelineReturn);
		
		timer.stop();
		
		boolean success = retornos.stream().noneMatch(p -> !p.isSuccess());
		return new PipelineReturn(sbMessage.toString(), success);
	}

	public static String doPipelineCarteira() throws TemporizadorNaoIniciadoException {
		StringBuilder sbMessage = new StringBuilder();
		String msg;
		
		Timer timer = new Timer();
		timer.start();
		
		sbMessage.append("Atualização de Carteira.");
		sbMessage.append(SkalaStringUtils.lineSeparator());

		PipelineReturn pipelineReturn = PipelineProcessor.atualizaCarteira();
		msg = String.format("%s %s", timer.lap(), pipelineReturn.getMessage());
		sbMessage.append(msg);
		sbMessage.append(SkalaStringUtils.lineSeparator());

		timer.stop();
		
		return sbMessage.toString();
	}
	
	public static String doPipelineCentral() throws TemporizadorNaoIniciadoException {
		StringBuilder sbMessage = new StringBuilder();
		String msg;
		
		Timer timer = new Timer();
		timer.start();
		
		sbMessage.append("Atualização de Lançamentos.");
		sbMessage.append(SkalaStringUtils.lineSeparator());
		
		PipelineReturn pipelineReturn = PipelineProcessor.atualizaLancamentosCentral();
		msg = String.format("%s %s", timer.lap(), pipelineReturn.getMessage());
		sbMessage.append(msg);
		sbMessage.append(SkalaStringUtils.lineSeparator());

		timer.stop();
		
		return sbMessage.toString();
	}

}

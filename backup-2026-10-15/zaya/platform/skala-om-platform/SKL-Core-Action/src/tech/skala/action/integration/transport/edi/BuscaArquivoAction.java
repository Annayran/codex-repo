package tech.skala.action.integration.transport.edi;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.modelcore.util.SWRepositoryUtils;
import tech.skala.action.integration.transport.ProcessamentoOcorrenciaFactory;
import tech.skala.action.integration.transport.azship.ProcessamentoOcorrenciaAZShipFactory;
import tech.skala.core.commons.extensions.Action;

public class BuscaArquivoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			File logisticFolder = SWRepositoryUtils.getFile("/Logistica");

			File[] transporterFolders = logisticFolder.listFiles();

			for (File folder : transporterFolders) {

				action.appendMessageWithLineSeparator(folder.getName());

				File[] files = folder.listFiles();

				List<File> validFiles = Arrays.asList(files).stream()
						.filter(e -> e.isFile() && e.getName().toUpperCase().startsWith("OCOR")).collect(Collectors.toList());

				ProcessamentoOcorrenciaFactory processamentoOcorrencia;
				for (File file : validFiles) {
					processamentoOcorrencia = new ProcessamentoOcorrenciaEDIFactory(file);
					processamentoOcorrencia.process();
				}

				if (!validFiles.isEmpty()) {
					String msg = String.format("%s arquivos foram processados.", validFiles.size());
					action.appendMessageWithLineSeparator(msg);
				}

				if (validFiles.isEmpty())
					action.appendMessageWithLineSeparator("Não há arquivos para serem processados.");

				action.appendMessageWithLineSeparator("");

			}

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}

	}

}

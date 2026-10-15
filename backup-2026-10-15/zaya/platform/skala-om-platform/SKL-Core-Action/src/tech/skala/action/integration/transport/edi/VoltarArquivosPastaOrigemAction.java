package tech.skala.action.integration.transport.edi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.modelcore.util.SWRepositoryUtils;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class VoltarArquivosPastaOrigemAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		try {
			action.start();

			File amazonFolder = SWRepositoryUtils.getFile("/Logistica/edi-amazon/edi-processado");
			File[] files = amazonFolder.listFiles();

			List<File> validFiles = Arrays.asList(files).stream()
					.filter(e -> e.isFile() && e.getName().toUpperCase().startsWith("OCOREN")).collect(Collectors.toList());

			for (File file : validFiles) {
				try {
					Path source = Paths.get(file.getAbsolutePath());
					Path target = Paths
							.get(SWRepositoryUtils.getBaseFolder() + "/Logistica/edi-amazon/" + file.getName());

					Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					SkalaModelException.throwMe("Não foi possível remanejar arquivo para pasta.", e);
				}

			}

			amazonFolder = SWRepositoryUtils.getFile("/Logistica/edi-amazon/edi-erro");
			files = amazonFolder.listFiles();

			validFiles = Arrays.asList(files).stream().filter(e -> e.isFile() && e.getName().startsWith("OCOREN"))
					.collect(Collectors.toList());

			for (File file : validFiles) {
				try {
					Path source = Paths.get(file.getAbsolutePath());
					Path target = Paths
							.get(SWRepositoryUtils.getBaseFolder() + "/Logistica/edi-amazon/" + file.getName());

					Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					SkalaModelException.throwMe("Não foi possível remanejar arquivo para pasta.", e);
				}

			}

			action.appendMessageWithLineSeparator("Arquivos movidos.");

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
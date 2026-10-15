package tech.skala.action.integration.transport.edi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.sankhya.util.StringUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.action.integration.transport.ProcessamentoOcorrenciaFactory;
import tech.skala.action.integration.transport.callcenter.CallCenter;
import tech.skala.action.integration.transport.hub.HubTransport;
import tech.skala.action.integration.transport.hub.HubTransportDAO;
import tech.skala.action.integration.transport.hub.HubTransportOccurrence;
import tech.skala.action.integration.transport.hub.HubTransportRequestResponse;
import tech.skala.action.integration.transport.ocorrencianaentrega.Entregas;
import tech.skala.action.modules.centrais.CabecalhoNota;
import tech.skala.action.modules.centrais.CabecalhoNotaDAO;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProcessamentoOcorrenciaEDIFactory extends ProcessamentoOcorrenciaFactory {

	private static final BigDecimal 		COD_PARC_TRANSP = BigDecimal.valueOf(25054);
	private File 							file;
	private OcorrenciaEDI 					ocorrenciaEDI;
	private LogTransportadorasStatusEnum 	statusLog = LogTransportadorasStatusEnum.PROCESSADO;
	private StringBuilder 					sbMsg = new StringBuilder();

	public ProcessamentoOcorrenciaEDIFactory(File file) {
		super();

		this.file = file;
		this.ocorrenciaEDI = new OcorrenciaEDI();
	}

	@Override
	public void process() throws SkalaModelException {

		try {
			loadEDIObject();

			for (OcorrenciaNaEntrega ocorrencia : ocorrenciaEDI.getOcorrencias())
				processaOcorrencia(ocorrencia);

		} finally {
			remanejarArquivo();
		}

	}

	private void loadEDIObject() {
		try (BufferedReader bufReader = new BufferedReader(new FileReader(file))) {

			String linha = bufReader.readLine();
			while (linha != null) {
				ocorrenciaEDI.loadByEDI(linha);
				linha = bufReader.readLine();
			}

		} catch (Exception e) {
			statusLog = LogTransportadorasStatusEnum.ERRO;
			sbMsg.append(e.getMessage());
		}
	}

	private void processaOcorrencia(OcorrenciaNaEntrega ocorrencia) {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(() -> processarOcorrencia(ocorrencia));

		} catch (Exception e) {
			sbMsg.append(e.getMessage());
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void processarOcorrencia(OcorrenciaNaEntrega ocorrencia) throws SkalaModelException {

		try {
			createHubTransport(ocorrencia);

			if (hubTransport != null) {
				HubTransportOccurrence transportOccurrence = buildOcorrenciaHub(ocorrencia);

				CallCenter callCenter = createCallCenter(hubTransport.getNuNota());
				createOcorrenciaCallCenter(callCenter, transportOccurrence, COD_PARC_TRANSP);

				Entregas entrega = createEntrega();
				createOcorrenciaEntrega(entrega, transportOccurrence, COD_PARC_TRANSP);
			}

		} catch (Exception e) {
			statusLog = LogTransportadorasStatusEnum.ERRO;
			sbMsg.append(e.getMessage());
		} finally {
			if (hubTransport != null)
				createRequestResponse();
		}

	}

	private HubTransport createHubTransport(OcorrenciaNaEntrega ocorrencia) throws SkalaModelException {

		HubTransport hubTransport = null;
		try {
			String cgc = ocorrencia.getCgcEmpresaEmissora();
			BigDecimal numNota = ocorrencia.getNumeroNF();
			BigDecimal serieNota = ocorrencia.getSerieNF();

			CabecalhoNota cabecalho = CabecalhoNotaDAO.getCabecalhoPorNumNotaSerieNotaECGC(numNota, serieNota, cgc);

			if (cabecalho != null) {

				BigDecimal codEmp = cabecalho.getCodEmp();
				BigDecimal ocPai = cabecalho.getOrdemCargaPai();
				BigDecimal nuNota = cabecalho.getNuNota();
				BigDecimal ordemCarga = cabecalho.getOrdemCarga();
				String chaveNFe = cabecalho.getChaveNFe();

				hubTransport = HubTransportDAO.findHubTransportPeloNuNota(nuNota);

				if (hubTransport == null) {
					hubTransport = new HubTransport();
					hubTransport.setCodEmp(codEmp);
					hubTransport.setNuNota(nuNota);
					hubTransport.setNumNota(numNota);
					hubTransport.setChaveNfe(chaveNFe);
					hubTransport.setOrdemCarga(ocPai);
					hubTransport.setOrdemCargaFilha(ordemCarga != null ? ordemCarga.toString() : "");
					hubTransport.persists();
				}

			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível construir HubTransportadora.", e);
		}

		setHubTransport(hubTransport);

		return hubTransport;
	}

	private HubTransportOccurrence buildOcorrenciaHub(OcorrenciaNaEntrega ocorrencia) throws SkalaModelException {

		HubTransportOccurrence occurrence = null;
		try {
			occurrence = new HubTransportOccurrence();
			StringBuilder dhOco = new StringBuilder(ocorrencia.getDataOcorrencia());

			String horaOco = ocorrencia.getHoraOcorrencia();
			dhOco.append(StringUtils.isNotEmpty(horaOco) ? ocorrencia.getHoraOcorrencia() : "0000");

			DateTimeFormatter parserData = DateTimeFormatter.ofPattern("ddMMuuuuHHmm");
			LocalDateTime dhOcorrencia = LocalDateTime.parse(dhOco.toString(), parserData);

			occurrence.setCodEmp(hubTransport.getCodEmp());
			occurrence.setOrdemCarga(hubTransport.getOrdemCarga());
			occurrence.setNuNota(hubTransport.getNuNota());
			occurrence.setChaveNfe(hubTransport.getChaveNfe());

			occurrence.setNuOccurrenceType(ocorrencia.getCodigoOcorrencia());
			occurrence.setDhOcorrencia(Timestamp.valueOf(dhOcorrencia));
			occurrence.setObservacao(ocorrencia.getTextoComplementar());

			if (!hubTransport.getOccurrences().contains(occurrence)) {
				occurrence.persists();
				hubTransport.getOccurrences().add(occurrence);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível persistir ocorrência no Hub Transportadoras.", e);
		}

		return occurrence;
	}

	@Override
	public void createRequestResponse() throws SkalaModelException {
		try {
			String nomeProcesso = String.format("%s.%s", getClass().getCanonicalName(),
					Thread.currentThread().getStackTrace()[1].getMethodName());

			HubTransportRequestResponse requestResponse = new HubTransportRequestResponse();
			requestResponse.setCodEmp(hubTransport.getCodEmp());
			requestResponse.setOrdemCarga(hubTransport.getOrdemCarga());
			requestResponse.setNuNota(hubTransport.getNuNota());
			requestResponse.setChaveNfe(hubTransport.getChaveNfe());

			requestResponse.setNomeProcesso(nomeProcesso);
			requestResponse.setRequest(ocorrenciaEDI.toString());

			requestResponse
					.setResponse(LogTransportadorasStatusEnum.PROCESSADO.equals(statusLog) ? "Ocorrência Processada "
							: "Ocorrência não Processada " + sbMsg.toString());
			requestResponse.setDtReq(TimeUtils.getNow());

			requestResponse.persists();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void remanejarArquivo() throws SkalaModelException {
		try {
			Path source = Paths.get(file.getAbsolutePath());
			Path successPath = Paths.get(file.getParent() + "/edi-processado/" + file.getName());
			Path failedPath = Paths.get(file.getParent() + "/edi-erro/" + file.getName());

			Path target = LogTransportadorasStatusEnum.PROCESSADO.equals(statusLog) ? successPath : failedPath;

			Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			SkalaModelException.throwMe("Não foi possível mover o Arquivo EDI.", e);
		}
	}

}

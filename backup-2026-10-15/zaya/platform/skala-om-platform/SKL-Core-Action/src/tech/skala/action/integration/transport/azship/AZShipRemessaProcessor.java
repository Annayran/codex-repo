package tech.skala.action.integration.transport.azship;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import tech.skala.action.core.http.ApiResponse;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class AZShipRemessaProcessor {

	private static final Logger LOGGER = Logger
			.getLogger("tech.skala.action.integration.transport.azship.AZShipRemessaProcessor");

	private List<AZShipRemessa> remessas;

	public AZShipRemessaProcessor(List<AZShipRemessa> remessas) {
		this.remessas = remessas;
	}

	public String enviarRemessa() throws SkalaModelException {
		String remessasJSON = geraJSONRemessa();

		LOGGER.info(remessasJSON);
		ApiResponse apiResponse = AZShipConnector.postRemessaPedido(remessasJSON);

		String msg = String.format((HttpURLConnection.HTTP_OK == apiResponse.getResponseCode())
				? "<b>Remessas Enviadas com sucesso.</b> %s"
				: "<b>Remessas não processadas.</b> %s %s", apiResponse.getResponse(), remessasJSON);

		if (HttpURLConnection.HTTP_OK == apiResponse.getResponseCode())
			trocarStatusPedido();

		return msg;

	}

	private void trocarStatusPedido() throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(this::trocaStatusPedido);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível enviar a(s) Remessa(s) para a Plataforma AZShip.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void trocaStatusPedido() throws SkalaModelException {
		AZShipRemessaPedidoLog remessaPedidoLog;

		for (AZShipRemessa remessa : remessas) {
			remessaPedidoLog = new AZShipRemessaPedidoLog(remessa.getNuNota());
			remessaPedidoLog.persists();
		}

	}

	private String geraJSONRemessa() {
		List<RemessaPedidoDTO> remessaDTOs = new ArrayList<>();

		for (AZShipRemessa remessa : remessas) {
			String dhAgendamento = TimeUtils.formataDDMMYYYYHHMMSS(remessa.getDhAgendamento());
			
			String treatment = String.format("Tipo de Carga: %s. Tipo de Entrega: %s. Exigência do cliente: %s. Dh. Agendamento: %s",
					remessa.getDescrExigCliente(), remessa.getDescrTipoEntrega(), remessa.getDescrTipoCarga(),
					dhAgendamento);
			
			String observacao = String.format("%s - Data da Agenda: %s", remessa.getObservacao(), dhAgendamento) ;
			remessa.setObservacao(observacao);
			
			RemessaPedidoDTO remessaDTO = (RemessaPedidoDTO) AZShipMapper.remessa2RemessaPedidoDtoMapper(remessa);
			remessaDTO.setTreatment(ConversorUtils.semAcento(treatment));

			remessaDTOs.add(remessaDTO);
		}

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
		String remessasJSON = gson.toJson(remessaDTOs, remessaDTOs.getClass());
		return remessasJSON;
	}

}

package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.action.integration.transport.ProcessamentoOcorrenciaFactory;
import tech.skala.action.integration.transport.hub.HubTransport;
import tech.skala.action.integration.transport.hub.HubTransportDAO;
import tech.skala.action.integration.transport.hub.HubTransportEvent;
import tech.skala.action.integration.transport.hub.HubTransportStatus;
import tech.skala.action.integration.transport.ocorrencianaentrega.Entregas;
import tech.skala.action.modules.centrais.CabecalhoNota;
import tech.skala.action.modules.centrais.CabecalhoNotaDAO;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProcessamentoOcorrenciaAZShipFactory extends ProcessamentoOcorrenciaFactory {

	private static final BigDecimal 	COD_PARC_TRANSP = BigDecimal.valueOf(25772);

	private BigDecimal 					codEmp;
	private BigDecimal 					ordemCarga;
	private ShippingDTO 				shippingDTO;

	public ProcessamentoOcorrenciaAZShipFactory(BigDecimal codEmp, BigDecimal ordemCarga, ShippingDTO shippingDTO) {
		super();

		this.codEmp = codEmp;
		this.ordemCarga = ordemCarga;
		this.shippingDTO = shippingDTO;
	}

	@Override
	public void process() throws SkalaModelException {

		List<OrderDTO> ordersDTO = AZShipDAO.buscaInformacoesPedido(codEmp, ordemCarga);

		for (OrderDTO orderDTO : ordersDTO) {
			SessionHandle hnd = null;

			try {
				hnd = JapeSession.open();
				hnd.setCanTimeout(false);

				hnd.execWithTX(() -> processarOcorrencia(orderDTO));

			} catch (Exception e) {
				SkalaModelException.throwMe("Não foi possível realizar o processamento das ocorrências.", e);
			} finally {
				JapeSession.close(hnd);
			}
		}

	}

	private void processarOcorrencia(OrderDTO orderDTO) throws SkalaModelException {
		String status = shippingDTO.getStatus();
		List<EventDTO> events = shippingDTO.getEvents();

		List<String> statusInvalidos = Arrays.asList("Entrega finalizada", "Motorista Avaliado");

		DynamicVO nfVO;
//		CallCenter callCenter;
		if (!statusInvalidos.contains(status)) {

			createHubTransport(orderDTO);

			if (hubTransport != null) {
				createHubStatus(status);

				HubTransportEvent transportEvent;
				BigDecimal nuNota = hubTransport.getNuNota();

				for (EventDTO eventDTO : events) {
					transportEvent = createHubEvents(eventDTO);

					nfVO = CabecalhoNotaDAO.buscaProximoLancamentoFaturadoPelaNuNota(nuNota);
					if (nfVO != null) {
						//callCenter = createCallCenter(nfVO.asBigDecimal(SklFieldName.NUNOTA));

						HubTransportDAO.excluirEventosHubTransport(hubTransport);

						//createOcorrenciaCallCenter(callCenter, transportEvent, COD_PARC_TRANSP);

						Entregas entrega = createEntrega();
						createOcorrenciaEntrega(entrega, transportEvent, COD_PARC_TRANSP);

					}
				}

			}
		}
	}

	private HubTransportEvent createHubEvents(EventDTO eventDTO) throws SkalaModelException {

		HubTransportEvent eventHub;
		eventHub = new HubTransportEvent(hubTransport);
		eventHub.setDtEvento(eventDTO.getDate());
		eventHub.setNomeEvento(eventDTO.getName());

		if (!hubTransport.getEvents().contains(eventHub)) {
			eventHub.setObservacao(eventDTO.getObservation());
			eventHub.setUsuario(eventDTO.getOwner());

			eventHub.persists();
			hubTransport.getEvents().add(eventHub);
		}
		
		return eventHub;
	}

	private HubTransport createHubStatus(String status) throws SkalaModelException {
		HubTransportStatus hubTransportStatus = new HubTransportStatus(hubTransport);
		hubTransportStatus.setDescricao(status);
		hubTransportStatus.setDtStatus(TimeUtils.getNow());
		hubTransportStatus.persists();

		hubTransport.getStatus().add(hubTransportStatus);

		return hubTransport;

	}

	private HubTransport createHubTransport(OrderDTO orderDTO) throws SkalaModelException {
		BigDecimal nuNota = null;
		HubTransport hubTransport = null;
		nuNota = BigDecimalUtil.getBigDecimal(BigDecimalUtil.getBigDecimal(orderDTO.getCode()));

		CabecalhoNota cabecalho = CabecalhoNotaDAO.getCabecalhoPorNuNota(nuNota);

		if (cabecalho != null) {
			AZShipRemessaOrdemCarga remessaOC = new AZShipRemessaOrdemCarga();
			remessaOC.loadByPk(codEmp, ordemCarga);

			BigDecimal numNota = cabecalho.getNumNota();
			BigDecimal ocPai = cabecalho.getOrdemCargaPai();
			String chaveNFe = cabecalho.getChaveNFe();

			hubTransport = HubTransportDAO.findHubTransportPeloNuNota(nuNota);

			if (hubTransport == null) {
				hubTransport = new HubTransport();
				hubTransport.setCodEmp(codEmp);
				hubTransport.setNuNota(nuNota);
				hubTransport.setNumNota(numNota);
				hubTransport.setChaveNfe(chaveNFe);
				hubTransport.setOrdemCarga(ocPai);
				hubTransport.setOrdemCargaFilha(remessaOC.getOrdemFilha());
				hubTransport.persists();

			}

		}

		setHubTransport(hubTransport);

		return hubTransport;
	}

	@Override
	public void createRequestResponse() throws SkalaModelException {
		// TODO document why this method is empty
	}

}

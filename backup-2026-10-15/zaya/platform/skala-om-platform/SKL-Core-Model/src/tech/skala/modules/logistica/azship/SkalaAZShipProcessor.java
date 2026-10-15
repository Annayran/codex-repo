package tech.skala.modules.logistica.azship;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.TimeUtils;
import com.sankhya.util.TimeUtils.TipoPeriodo;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.parceiro.ParceiroFinder;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.logistica.callcenter.CallCenter;
import tech.skala.modules.logistica.callcenter.OcorrenciaCallCenter;
import tech.skala.modules.logistica.commons.OcorrenciaTransportadoraFinder;
import tech.skala.modules.logistica.hub.HubTransport;
import tech.skala.modules.logistica.hub.HubTransportDAO;
import tech.skala.modules.logistica.hub.HubTransportEvent;
import tech.skala.modules.logistica.ocorrenciaentrega.Entregas;
import tech.skala.modules.logistica.ocorrenciaentrega.EntregasDAO;
import tech.skala.modules.logistica.ocorrenciaentrega.OcorrenciaDeEntrega;
import tech.skala.modules.logistica.ocorrenciaentrega.OcorrenciaOrigemEnum;
import tech.skala.modules.logistica.ordemcarga.OrdemCarga;
import tech.skala.modules.logistica.ordemcarga.OrdemCargaDAO;
import tech.skala.modules.logistica.ordemcarga.OrdemCargaPedidoDTO;
import tech.skala.modules.logistica.ordemcarga.SituacaoCargaEnum;
import tech.skala.modules.logistica.ordemcarga.StatusCargaEnum;
import tech.skala.modules.logistica.ordemcarga.TipoCargaEnum;
import tech.skala.modules.logistica.ordemcarga.TipoEmbalagemClienteEnum;
import tech.skala.modules.logistica.ordemcarga.TipoEmbalagensOrdemCargaEnum;
import tech.skala.modules.logistica.ordemcarga.TipoOrdemCargaEnum;
import tech.skala.modules.vendas.CabecalhoNotaDAO;

public class SkalaAZShipProcessor {

	private static final Logger LOGGER = new LoggerFactory(SkalaAZShipProcessor.class.getCanonicalName()).getLogger();

	/**
	 * Método responsável por realizar a criação das Ordens de
	 * Carga no Sankhya, com informações enviadas da AZShip 
	 * 
	 * @param shippingDTO
	 * @see ShippingDTO
	 * @throws SkalaModelException
	 */
	public void processaOrdemCarga(ShippingDTO shippingDTO) throws SkalaModelException {
		LOGGER.info("processaOrdemCarga");

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					LOGGER.info(shippingDTO.getDeliveryType());

					if ("FECHADO".equalsIgnoreCase(shippingDTO.getDeliveryType())) {
						criaOCFechada(shippingDTO);
					}

				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar as informações.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	/**
	 * Particularidades referente à formação das cargas
	 * 
	 * <ol>
	 * <li> Caso não seja encontrado o CNPJ da Transportadora no Sankhya, deve-se considerar o <b>Parceiro 59 - "Contratando"</b></li>
	 * <li> O campo {@code CODEMP} da Ordem de Carga será definido sempre como <b>2 - Platina Logística</b></li>
	 * <li> O campo {@code DTINI} da Ordem de Carga será definido sempre como o <b>Primeiro dia do ano corrente</b></li>
	 * <li> O campo {@code DTPREVSAIDA} da Ordem de Carga deverá ser a data da Coleta enviada na AZShip, caso não seja preenchido, deverá considerar o último dia da referência atual.</li>
	 * <li> O campo {@code TIPCARGA} da Ordem de Carga será definido sempre como <b>F - Fechada</b></li>
	 * <li> O campo {@code ENTSAI} da Ordem de Carga será definido sempre como <b>S - Saída</b></li>
	 * <li> O campo {@code AD_STATUSCARGA} da Ordem de Carga será definido sempre como <b>CA - Programado Expedição</b></li>
	 * <li> O campo {@code ROTEIRO} da Ordem de Carga será definido com a informação do campo {@code OBSERVATION}  <b>recebido pela Integração AZShip</b></li>
	 * <li> O campo {@code SITUACAO} da Ordem de Carga será definido sempre como <b>A - Aberto</b></li>
	 * <li> O campo {@code AD_TIPEMBALAGEM} da Ordem de Carga será definido conforme <b>o cadastro do campo {@code AD_EXIGCLIENTE} do cadastro do Cliente {@code TGFPAR} </b></li>
	 * </ol>
	 * @param shippingDTO
	 * @see ShippingDTO
	 * @throws SkalaModelException
	 */
	private void criaOCFechada(ShippingDTO shippingDTO) throws SkalaModelException {

		LOGGER.info("criaOCFechada");

		try {
			String cnpjTransportadora = shippingDTO.getTransporter().getCnpj();
			DynamicVO transportadoraVO = ParceiroFinder.buscaParceiroPorCgcCPF(cnpjTransportadora);

			BigDecimal codParcTransp = (transportadoraVO == null) ? BigDecimal.valueOf(59)
					: transportadoraVO.asBigDecimal(SklFieldName.CODPARC);

			OrdemCarga ocPai = new OrdemCarga();
			ocPai.setCodEmp(BigDecimal.valueOf(2));
			ocPai.setCodParcTransp(codParcTransp);

			Timestamp dtRef = TimeUtils.getInicioPeriodo(TimeUtils.getNow(), TipoPeriodo.MES);
			Timestamp dtRefMesSeguinte = TimeUtils.dataAdd(dtRef, 1, Calendar.MONTH);
			ocPai.setDtInic(TimeUtils.getInicioPeriodo(TimeUtils.getNow(), TipoPeriodo.ANO));

			Timestamp dtColeta = shippingDTO.getOrigins().get(0).getCollectDate();
			Timestamp dtPrevSaida = (dtColeta == null ? TimeUtils.dataAdd(dtRefMesSeguinte, -1, Calendar.DAY_OF_MONTH)
					: dtColeta);

			ocPai.setDtPrevSaida(dtPrevSaida);
			ocPai.setTipCarga(TipoCargaEnum.FECHADA.getValue());
			ocPai.setEntSai(TipoOrdemCargaEnum.SAIDA.getValue());
			ocPai.setStatusCarga(StatusCargaEnum.PROGRAMADO_EXPEDICAO.getValue());
			ocPai.setRoteiro(shippingDTO.getObservation());
			ocPai.setSituacao(SituacaoCargaEnum.ABERTO.getValue());
			ocPai.setHoraSaida(dtPrevSaida);

			String cnpjCpfCliente = shippingDTO.getDestinations().get(0).getPlace().getDocument();
			DynamicVO clienteVO = ParceiroFinder.buscaParceiroPorCgcCPF(cnpjCpfCliente);

			if (clienteVO != null) {

				String tipoEmbalagem = clienteVO.asString("AD_EXIGCLIENTE");

				Map<String, String> mapeamentoTipoEmbalagem = new HashMap<>();
				mapeamentoTipoEmbalagem.put(TipoEmbalagemClienteEnum.PALETIZADA.getValue(),
						TipoEmbalagensOrdemCargaEnum.PALETIZADO.getValue());
				mapeamentoTipoEmbalagem.put(TipoEmbalagemClienteEnum.ESTIVADA.getValue(),
						TipoEmbalagensOrdemCargaEnum.ESTIVADO.getValue());
				mapeamentoTipoEmbalagem.put(TipoEmbalagemClienteEnum.PALETIZADO_POR_ITEM.getValue(),
						TipoEmbalagensOrdemCargaEnum.PALETIZADO_SKU.getValue());
				mapeamentoTipoEmbalagem.put(TipoEmbalagemClienteEnum.INDIFERENTE.getValue(),
						TipoEmbalagensOrdemCargaEnum.PALETIZADO_ESTIVADO.getValue());

				ocPai.setTipoEmbalagens(mapeamentoTipoEmbalagem.get(tipoEmbalagem));

				ocPai.setCodReg(clienteVO.asBigDecimal(SklFieldName.CODREG));
			}

			ocPai.persists();

			OrdemCarga ocFilha = ocPai.clone();
			ocFilha.setOrdemCarga(null);
			ocFilha.setOrdemCargaPai(ocPai.getOrdemCarga());
			ocFilha.persists();

			relacionarPedidosNaOrdemCarga(ocFilha, shippingDTO);

			String msg = String.format("Ordens de Cargas criadas: %s %s", ocFilha.getOrdemCarga(),
					ocFilha.getOrdemCargaPai());
			LOGGER.info(msg);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível criar Ordem de Carga.", e);
		}

	}

	/**
	 * Método responsável por relacionar as ORs recebidas pela integração à Ordem de
	 * carga criada.
	 * 
	 * @param ocFilha
	 * @param shippingDTO
	 * @throws SkalaModelException
	 */
	private void relacionarPedidosNaOrdemCarga(OrdemCarga ocFilha, ShippingDTO shippingDTO) throws SkalaModelException {

		LOGGER.info("relacionarPedidosNaOrdemCarga");

		JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

		for (DestinationDTO destinationDTO : shippingDTO.getDestinations()) {

			for (BigDecimal nuNota : destinationDTO.getOrderIds()) {

				try {
					if (nuNota != null) {

						DynamicVO cabVO = cabDAO.findByPK(nuNota);

						EntityUpdate entityUpdate = new EntityUpdate(DynamicEntityNames.CABECALHO_NOTA, cabVO);
						entityUpdate.setField(SklFieldName.ORDEMCARGA, ocFilha.getOrdemCarga());
						entityUpdate.setField(SklFieldName.AD_ORDEMCARGA, ocFilha.getOrdemCargaPai());

						entityUpdate.fluidUpdate();

						String msg = String.format(
								"[ORDEMCARGA %s][NUNOTA %s] Ordem de Carga relacionada ao Lançamento.",
								ocFilha.getOrdemCarga(), nuNota);
						LOGGER.info(msg);

					}
				} catch (Exception e) {
					String msg = String.format(
							"[NUNOTA %s][ORDEMCARGA %s] Não foi possível relacionar Ordem de Carga à Ordem de Reserva.",
							nuNota, ocFilha.getOrdemCarga());
					LOGGER.info(SkalaModelException.buildErrorMessage(msg, e));

				}

			}

		}

	}

	/**
	 * Método responsável por atualizar o Parceiro Motorista da Ordem de Carga
	 * @param dto
	 * @see UpdateDriverVehicleDTO
	 * @throws SkalaModelException
	 */
	public void updateDriverVehicle(UpdateDriverVehicleDTO dto) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(() -> {

				for (BigDecimal nuNota : dto.getOrderIds())
					updatingDriverVehicle(dto, OrdemCargaDAO.getOrdemCargaByNuNota(nuNota));

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar Ordens de Cargas com informações do Motorista.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	/**
	 * Particularidades referente à atualização do motorista do veículo
	 * 
	 * <ol>
	 * <li>Caso não seja encontrado o CNPJ do Motorista no Sankhya, deve-se
	 * considerar o <b>Parceiro 59 - "Contratando"</b></li>
	 * </ol>
	 * 
	 * @param shippingDTO
	 * @see ShippingDTO
	 * @throws SkalaModelException
	 */
	private void updatingDriverVehicle(UpdateDriverVehicleDTO dto, OrdemCargaPedidoDTO ordemCargaDTO)
			throws SkalaModelException {
		DynamicVO parceiroVO = ParceiroFinder.buscaParceiroPorCgcCPF(dto.getCpf());

		BigDecimal codParcMotorista = (parceiroVO == null) ? BigDecimal.valueOf(59)
				: parceiroVO.asBigDecimal(SklFieldName.CODPARC);

		EntityPrimaryKey pk = new EntityPrimaryKey(
				new Object[] { BigDecimal.valueOf(2), ordemCargaDTO.getOrdemCarga() });
		EntityUpdate entityUpdate = new EntityUpdate(DynamicEntityNames.ORDEM_CARGA, pk);
		entityUpdate.setField(SklFieldName.CODPARCMOTORISTA, codParcMotorista);
		entityUpdate.setField(SklFieldName.CODVEICULO, BigDecimal.ZERO);
		entityUpdate.fluidUpdate();

		pk = new EntityPrimaryKey(new Object[] { BigDecimal.valueOf(2), ordemCargaDTO.getOrdemCargaPai() });
		entityUpdate = new EntityUpdate(DynamicEntityNames.ORDEM_CARGA, pk);
		entityUpdate.setField(SklFieldName.CODPARCMOTORISTA, codParcMotorista);
		entityUpdate.setField(SklFieldName.CODVEICULO, BigDecimal.ZERO);
		entityUpdate.fluidUpdate();

		String msg = String.format("[ORDEMCARGA %s][CODPARCMOTORISTA %s] Motorista atribuído a Ordem de Carga.",
				ordemCargaDTO.getOrdemCarga(), codParcMotorista);
		LOGGER.info(msg);

	}

	/**
	 * Método responsável por realizar o processamento dos eventos recebidos pela
	 * Integração AZShip, transformando e registrando as ocorrências dentro do
	 * Sankhya.
	 * 
	 * @param dto
	 * @see UpdateEventDTO
	 * @throws SkalaModelException
	 */
	public void atualizaEventos(UpdateEventDTO dto) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(() -> atualizarEventos(dto));

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar eventos", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void atualizarEventos(UpdateEventDTO dto) throws SkalaModelException {

		DynamicVO notaFiscalVO = null;
		HubTransport hubTransport = null;

		for (BigDecimal nuNota : dto.getOrderIds()) {

			notaFiscalVO = localizarNotaFiscalFaturadaEConfirmada(nuNota);

			if (notaFiscalVO != null) {

				hubTransport = buildHubTransport(notaFiscalVO);

				if (hubTransport != null) {
					for (EventDTO event : dto.getEvents())
						gravaOcorrencia(notaFiscalVO, hubTransport, event);
				}
			}

		}

	}

	private void gravaOcorrencia(DynamicVO notaFiscalVO, HubTransport hubTransport, EventDTO event) {

		String msg;
		try {
			BigDecimal codOcoAZShip;
			persistEventsHubTransport(hubTransport, event);

			codOcoAZShip = EventAZShipDAO.buscaCodOcoPeloNomeEvento(event.getName());
			if (codOcoAZShip != null) {
				
				BigDecimal codOcoPlatina = OcorrenciaTransportadoraFinder.buscaOcorrenciaPlatina(codOcoAZShip,
						BigDecimal.valueOf(25772));

				if (codOcoPlatina != null) {

					msg = String.format("[EVENTO %s] Evento recebido.", event.getName());
					LOGGER.info(msg);

//					CallCenter callCenter = CallCenterDAO.buscaCallCenterNoSistema(notaFiscalVO);
//					buildOcorrenciaCallCenter(callCenter, event);
					
					buildOcorrenciaEntrega(notaFiscalVO, event);

				}

			}

		} catch (Exception e) {
			BigDecimal nuNota = notaFiscalVO.asBigDecimal(SklFieldName.NUNOTA);
			msg = String.format("[NUNOTA %s][EVENT %s] Nao foi possivel gravar ocorrencia para a nota.", nuNota,
					event.getName());

			String error = SkalaModelException.buildErrorMessage(msg, e);
			LOGGER.info(error);
		}

	}

	private void persistEventsHubTransport(HubTransport hubTransport, EventDTO event) throws SkalaModelException {

		try {
			HubTransportEvent hubTransportEvent = new HubTransportEvent(hubTransport);
			hubTransportEvent.setDtEvento(event.getDate());
			hubTransportEvent.setNomeEvento(event.getName());
			hubTransportEvent.setObservacao(event.getObservation());
			hubTransportEvent.setUsuario(event.getOwner());

			if (!hubTransport.getEvents().contains(hubTransportEvent)) {
				hubTransportEvent.persists();

				hubTransport.getEvents().add(hubTransportEvent);
			}

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] [EVENT %s] Não foi possível salvar evento no HubTransport.",
					hubTransport.getNuNota(), event.getName());
			SkalaModelException.throwMe(msg, e);
		}

	}

	private HubTransport buildHubTransport(DynamicVO notaFiscalVO) throws SkalaModelException {
		BigDecimal nuNota = notaFiscalVO.asBigDecimal(SklFieldName.NUNOTA);

		HubTransport hubTransport = HubTransportDAO.findHubTransportPeloNuNota(nuNota);

		String chaveNFe = notaFiscalVO.asString(SklFieldName.CHAVENFE);
		BigDecimal ordemCargaPai = notaFiscalVO.asBigDecimal(SklFieldName.AD_ORDEMCARGA);
		try {
			if (hubTransport == null) {
				hubTransport = new HubTransport();
				hubTransport.setCodEmp(BigDecimal.valueOf(2));
				hubTransport.setChaveNfe(chaveNFe == null ? "0" : chaveNFe);
				hubTransport.setNumNota(notaFiscalVO.asBigDecimal(SklFieldName.NUMNOTA));
				hubTransport.setNuNota(nuNota);
				hubTransport.setOrdemCarga(ordemCargaPai == null ? BigDecimal.ZERO : ordemCargaPai);
				hubTransport.setOrdemCargaFilha(notaFiscalVO.asBigDecimal(SklFieldName.ORDEMCARGA).toString());
				hubTransport.persists();
			}

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível criar HubTransport.", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

		return hubTransport;
	}

	private void buildOcorrenciaCallCenter(CallCenter callCenter, EventDTO event) throws SkalaModelException {

		BigDecimal codOcoAZShip = EventAZShipDAO.buscaCodOcoPeloNomeEvento(event.getName());
		BigDecimal codOcoPlatina = OcorrenciaTransportadoraFinder.buscaOcorrenciaPlatina(codOcoAZShip,
				BigDecimal.valueOf(25772));

		try {

			OcorrenciaCallCenter ocorrenciaCallCenter = new OcorrenciaCallCenter();
			ocorrenciaCallCenter.setCodCal(callCenter.getCodCal());
			ocorrenciaCallCenter.setCodOco(codOcoPlatina);
			ocorrenciaCallCenter.setDhOcorrencia(event.getDate());

			if (!callCenter.getOcorrencias().contains(ocorrenciaCallCenter)) {
				ocorrenciaCallCenter.setObservacao(event.getObservation());
				ocorrenciaCallCenter.setUsuarioExt(event.getOwner());
				ocorrenciaCallCenter.persists();

				callCenter.getOcorrencias().add(ocorrenciaCallCenter);
			}

		} catch (Exception e) {
			String msg = String.format("[CODCAL %s] [EVENT %s] Não foi possível salvar Ocorrência no Call Center.",
					callCenter.getCodCal(), event.getName());
			SkalaModelException.throwMe(msg, e);
		}

	}

	private void buildOcorrenciaEntrega(DynamicVO notaFiscalVO, EventDTO event) throws SkalaModelException {

		BigDecimal nuNota = notaFiscalVO.asBigDecimal(SklFieldName.NUNOTA);
		BigDecimal codOcoAZShip = EventAZShipDAO.buscaCodOcoPeloNomeEvento(event.getName());
		BigDecimal codOcoPlatina = OcorrenciaTransportadoraFinder.buscaOcorrenciaPlatina(codOcoAZShip,
				BigDecimal.valueOf(25772));

		Entregas entrega = EntregasDAO.buscaCabecalhoEntregaPorNuNota(nuNota);

		try {

			if (entrega == null) {
				entrega = new Entregas();
				entrega.setCodEmp(BigDecimal.valueOf(2));
				entrega.setNuNota(nuNota);
				entrega.setOrdemCarga(notaFiscalVO.asBigDecimal(SklFieldName.ORDEMCARGA));
				entrega.persists();
			}

			OcorrenciaDeEntrega ocorrencia = new OcorrenciaDeEntrega();
			ocorrencia.setNuNota(nuNota);
			ocorrencia.setCodOco(codOcoPlatina);
			ocorrencia.setDhOcorrencia(event.getDate());
			ocorrencia.setObservacao(event.getObservation());
			ocorrencia.setOrigem(OcorrenciaOrigemEnum.INTEGRACAO.getValue());

			if (!entrega.getOcorrencias().contains(ocorrencia)) {
				ocorrencia.persists();

				entrega.getOcorrencias().add(ocorrencia);
			}

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível criar Ocorrência de Entrega.", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

	}

	private DynamicVO localizarNotaFiscalFaturadaEConfirmada(BigDecimal nuNota) throws SkalaModelException {
		DynamicVO notaFiscalVO = CabecalhoNotaDAO.buscaProximoLancamentoFaturadoPelaNuNota(nuNota);

		if ((notaFiscalVO != null) && (!"L".equals(notaFiscalVO.asString(SklFieldName.STATUSNOTA))))
			notaFiscalVO = null;

		return notaFiscalVO;
	}
}

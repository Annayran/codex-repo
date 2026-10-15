package tech.skala.action.integration.transport;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.integration.transport.azship.AZShipDAO;
import tech.skala.action.integration.transport.azship.ProcessamentoOcorrenciaAZShipFactory;
import tech.skala.action.integration.transport.callcenter.CallCenter;
import tech.skala.action.integration.transport.callcenter.CallCenterProcessor;
import tech.skala.action.integration.transport.callcenter.OcorrenciaCallCenter;
import tech.skala.action.integration.transport.edi.ProcessamentoOcorrenciaEDIFactory;
import tech.skala.action.integration.transport.hub.HubTransport;
import tech.skala.action.integration.transport.hub.HubTransportEvent;
import tech.skala.action.integration.transport.hub.HubTransportOccurrence;
import tech.skala.action.integration.transport.ocorrencianaentrega.Entregas;
import tech.skala.action.integration.transport.ocorrencianaentrega.EntregasDAO;
import tech.skala.action.integration.transport.ocorrencianaentrega.OcorrenciaDeEntrega;
import tech.skala.action.integration.transport.ocorrencianaentrega.OcorrenciaOrigemEnum;
import tech.skala.action.modules.centrais.CabecalhoNota;
import tech.skala.action.modules.centrais.CabecalhoNotaDAO;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.logistica.commons.OcorrenciaTransportadoraFinder;

public abstract class ProcessamentoOcorrenciaFactory {

	protected HubTransport hubTransport;

	public void setHubTransport(HubTransport hubTransport) {
		this.hubTransport = hubTransport;
	}

	public HubTransport getHubTransport() {
		return hubTransport;
	}

	/**
	 * Método que deverá ser implementado na classe abstrata.
	 * 
	 * 
	 * <p>
	 * Este método será o ponto de partida para o processamento da ocorrência,
	 * <b>portanto deverá ter uma transação ativa dentro dele.</b>
	 * </p>
	 * 
	 * Para mais exemplos, verifique as classes concretas:
	 * 
	 * @see ProcessamentoOcorrenciaEDIFactory
	 * @see ProcessamentoOcorrenciaAZShipFactory
	 * @throws SkalaModelException
	 */
	public abstract void process() throws SkalaModelException;

	/* Ocorrência na Entrega */

	/**
	 * Método responsável por realizar a criação do Cabeçalho de Entrega
	 * {@link Entregas}
	 * 
	 * @return Objeto da classe {@code Entregas}
	 * @throws SkalaModelException
	 */
	public Entregas createEntrega() throws SkalaModelException {
		Entregas entrega = null;
		try {
			CabecalhoNota cabecalhoNota = CabecalhoNotaDAO.getCabecalhoPorNuNota(hubTransport.getNuNota());
			entrega = EntregasDAO.buscaCabecalhoEntregaPorNuNota(hubTransport.getNuNota());

			if (entrega == null) {
				entrega = new Entregas();
				entrega.setNuNota(hubTransport.getNuNota());
				entrega.setCodEmp(hubTransport.getCodEmp());
				entrega.setOrdemCarga(hubTransport.getOrdemCarga());
				entrega.setNumNota(hubTransport.getNumNota());
				entrega.setCodParc(cabecalhoNota == null ? BigDecimal.ZERO : cabecalhoNota.getCodParc());

				entrega.persists();
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível construir Cabeçalho da Entrega.", e);
		}

		return entrega;
	}

	/**
	 * Método responsável por realizar a criação de uma Ocorrência de Entrega a
	 * partir da classe {@link HubTransportOccurrence} que foi informada como
	 * parâmetro.
	 * 
	 * @param entrega
	 * @param transportOccurrence
	 * @param codParcTransp
	 * @throws SkalaModelException
	 */
	public Entregas createOcorrenciaEntrega(Entregas entrega, HubTransportOccurrence transportOccurrence,
			BigDecimal codParcTransp) throws SkalaModelException {
		try {
			BigDecimal codOcoPlatina;
			OcorrenciaDeEntrega ocorrenciaDeEntrega = new OcorrenciaDeEntrega();

			ocorrenciaDeEntrega.setNuNota(transportOccurrence.getNuNota());
			codOcoPlatina = OcorrenciaTransportadoraFinder
					.buscaOcorrenciaPlatina(transportOccurrence.getNuOccurrenceType(), codParcTransp);

			ocorrenciaDeEntrega.setCodOco(codOcoPlatina);
			ocorrenciaDeEntrega.setDhOcorrencia(transportOccurrence.getDhOcorrencia());
			if (!entrega.getOcorrencias().contains(ocorrenciaDeEntrega) && codOcoPlatina != null) {
				ocorrenciaDeEntrega.setObservacao(transportOccurrence.getObservacao());
				ocorrenciaDeEntrega.setOrigem(OcorrenciaOrigemEnum.INTEGRACAO.getValue());
				ocorrenciaDeEntrega.persists();

				entrega.getOcorrencias().add(ocorrenciaDeEntrega);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar Ocorrência na Entrega.", e);
		}
		return entrega;
	}

	/**
	 * Método responsável por realizar a criação de uma Ocorrência de Entrega a
	 * partir da classe {@link HubTransportEvent} que foi informada como parâmetro.
	 * 
	 * @param entrega
	 * @param transportEvent
	 * @param codParcTransp
	 * @return
	 * @throws SkalaModelException
	 */
	public Entregas createOcorrenciaEntrega(Entregas entrega, HubTransportEvent transportEvent,
			BigDecimal codParcTransp) throws SkalaModelException {
		try {
			BigDecimal codOcoPlatina;
			BigDecimal codOcoTransp = AZShipDAO.buscaCodOcoPeloNomeEvento(transportEvent.getNomeEvento());
			OcorrenciaDeEntrega ocorrenciaDeEntrega = new OcorrenciaDeEntrega();

			ocorrenciaDeEntrega.setNuNota(entrega.getNuNota());
			codOcoPlatina = OcorrenciaTransportadoraFinder.buscaOcorrenciaPlatina(codOcoTransp, codParcTransp);

			ocorrenciaDeEntrega.setCodOco(codOcoPlatina);
			ocorrenciaDeEntrega.setDhOcorrencia(transportEvent.getDtEvento());
			if (!entrega.getOcorrencias().contains(ocorrenciaDeEntrega) && codOcoPlatina != null) {
				ocorrenciaDeEntrega.setObservacao(transportEvent.getObservacao());
				ocorrenciaDeEntrega.setOrigem(OcorrenciaOrigemEnum.INTEGRACAO.getValue());
				ocorrenciaDeEntrega.persists();

				entrega.getOcorrencias().add(ocorrenciaDeEntrega);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar Ocorrência na Entrega.", e);
		}

		return entrega;
	}

	/* Hub de Transportadoras */

	public abstract void createRequestResponse() throws SkalaModelException;

	/* Call Center */

	/**
	 * Método responsável por realizar a criação de um CallCenter a partir de um
	 * {@code NUNOTA} informado.
	 * 
	 * Método descontinuado devido à substituição da estrutura de Call Center
	 * 
	 * @param nuNota Nro Único do Lançamento.
	 * @return Objeto da Classe {@code CallCenter}
	 * @throws SkalaModelException
	 */
	@Deprecated
	public CallCenter createCallCenter(BigDecimal nuNota) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

		DynamicVO nfeVO;
		CallCenter callCenter = null;
		try {
			nfeVO = dao.findByPK(nuNota);

			callCenter = CallCenterProcessor.localizaOuCriaNovoCallCenter(nfeVO);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar Call Center.", e);
		}

		return callCenter;

	}

	/**
	 * Método descontinuado devido à substituição da estrutura de Call Center
	 * 
	 * @param callCenter
	 * @param codOcoPlatina
	 * @return booleano
	 */
	@Deprecated
	public CallCenter createOcorrenciaCallCenter(CallCenter callCenter, HubTransportOccurrence transportOccurrence,
			BigDecimal codParcTransp) throws SkalaModelException {

		try {
			BigDecimal codOcoPlatina;
			OcorrenciaCallCenter ocorrenciaCallCenter = new OcorrenciaCallCenter();

			ocorrenciaCallCenter.setCodCal(callCenter.getCodCal());
			codOcoPlatina = OcorrenciaTransportadoraFinder
					.buscaOcorrenciaPlatina(transportOccurrence.getNuOccurrenceType(), codParcTransp);

			ocorrenciaCallCenter.setCodOco(codOcoPlatina);
			ocorrenciaCallCenter.setDhOcorrencia(transportOccurrence.getDhOcorrencia());

			if (!callCenter.getOcorrencias().contains(ocorrenciaCallCenter) && codOcoPlatina != null) {
				ocorrenciaCallCenter.setObservacao(transportOccurrence.getObservacao());
				ocorrenciaCallCenter.setUsuarioExt("Integração Via EDI");

				ocorrenciaCallCenter.persists();

				callCenter.getOcorrencias().add(ocorrenciaCallCenter);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar Ocorrencia Call Center.", e);
		}

		return callCenter;

	}

	/**
	 * Método descontinuado devido à substituição da estrutura de Call Center
	 * 
	 * @param callCenter
	 * @param codOcoPlatina
	 * @return booleano
	 */
	@Deprecated
	public CallCenter createOcorrenciaCallCenter(CallCenter callCenter, HubTransportEvent transportEvent,
			BigDecimal codParcTransp) throws SkalaModelException {
		OcorrenciaCallCenter ocorrencia = new OcorrenciaCallCenter();

		BigDecimal codOcoTransp = AZShipDAO.buscaCodOcoPeloNomeEvento(transportEvent.getNomeEvento());
		BigDecimal codOcoPlatina = OcorrenciaTransportadoraFinder.buscaOcorrenciaPlatina(codOcoTransp, codParcTransp);

		ocorrencia.setCodCal(callCenter.getCodCal());
		ocorrencia.setCodOco(codOcoPlatina);
		ocorrencia.setDhOcorrencia(transportEvent.getDtEvento());

		if (!callCenter.getOcorrencias().contains(ocorrencia) && codOcoPlatina != null) {

			ocorrencia.setObservacao((2L == codOcoPlatina.longValue()) ? transportEvent.getNomeEvento()
					: transportEvent.getObservacao());
			ocorrencia.setUsuarioExt(transportEvent.getUsuario());

			if (!buscaOcorrenciaEntrega(callCenter, codOcoPlatina)) {
				ocorrencia.persists();
				callCenter.getOcorrencias().add(ocorrencia);
			}

		}

		return callCenter;
	}

	/**
	 * Método descontinuado devido à substituição da estrutura de Call Center
	 * 
	 * @param callCenter
	 * @param codOcoPlatina
	 * @return booleano
	 */
	@Deprecated
	private boolean buscaOcorrenciaEntrega(CallCenter callCenter, BigDecimal codOcoPlatina) {

		if (2L != codOcoPlatina.longValue())
			return false;

		boolean encontrei = false;
		for (OcorrenciaCallCenter ocorrencia : callCenter.getOcorrencias()) {

			if (2L == ocorrencia.getCodOco().longValue()) {
				encontrei = true;
				break;
			}
		}
		return encontrei;
	}

}

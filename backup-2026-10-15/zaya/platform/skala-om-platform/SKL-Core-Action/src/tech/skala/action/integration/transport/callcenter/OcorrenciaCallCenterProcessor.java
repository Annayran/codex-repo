package tech.skala.action.integration.transport.callcenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.integration.transport.CallCenterExceptions;
import tech.skala.action.integration.transport.CallCenterExceptions.OcorrenciaCallCenterNaoGeradaException;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * Classe utilitária contendo métodos para manipulação de dados da classe
 * {@code OcorrenciaCallCenter}, que representa a tabela {@code AD_TMSCALOCO}
 * 
 * @author hiagoalexandre
 * @since 2023-02-21
 */
public class OcorrenciaCallCenterProcessor {

	private OcorrenciaCallCenterProcessor() {
		
	}
	
	/**
	 * Método cuja responsabilidade é criar um registro de Ocorrência Base no Call
	 * Center, corresponde à tabela {@code AD_TMSCALOCO}.
	 * 
	 * <b>Observação:</b>
	 * 
	 * Uma ocorrência base, corresponde à uma ocorrência de acompanhamento de
	 * entrega. Código da Ocorrência = 1. <br>
	 * 
	 * <p>
	 * A Ocorrência 1, só pode ser preenchida uma vez por registro, uma vez que ela
	 * é utilizada para demonstrar que o processo foi iniciado. <br>
	 * 
	 * Após a criação da ocorrência, a mesma é inserida dentro da Lista de
	 * Ocorrências presente no objeto {@code callCenter} informado no parâmetro.
	 * </p>
	 * @param callCenter Representa uma instância da Classe {@code CallCenter}
	 *                   referente à um registro da tabela {@code AD_TMSCAL}.
	 */
	public static void criarOcorrenciaBase(CallCenter callCenter) throws OcorrenciaCallCenterNaoGeradaException {
		JapeWrapper dao = JapeFactory.dao(SklEntityName.OCORRENCIA_CALL_CENTER);
		Collection<DynamicVO> ocorrenciasVO = new ArrayList<>();
		try {
			ocorrenciasVO = dao.find("this.CODCAL = ? AND this.CODOCO = 1", callCenter.getCodCal());

			// permitir que crie somente uma ocorrência desse tipo para esse call center
			if (ocorrenciasVO.isEmpty()) {
				OcorrenciaCallCenter ocorrencia = new OcorrenciaCallCenter();
				ocorrencia.setCodCal(callCenter.getCodCal());
				ocorrencia.setCodOco(BigDecimal.ONE);
				ocorrencia.setDhOcorrencia(TimeUtils.getNow());

				ocorrencia.persists();

				callCenter.getOcorrencias().add(ocorrencia);
			}

		} catch (Exception e) {
			throw new CallCenterExceptions.OcorrenciaCallCenterNaoGeradaException(e);
		}
	}
	
	/**
	 * Método cuja responsabilidade é mesclar os registro de Ocorrências lançadas nos
	 * Call Centers que a nota está sendo carregada, criando as mesmas em um Call
	 * Center destino, informado como parâmetro.
	 * 
	 * <p>
	 * Após a criação da ocorrência, a mesma é inserida dentro da Lista de
	 * Ocorrências presente no objeto {@code callCenter} informado no parâmetro.
	 * </p>
	 * 
	 * @param callCenterDestino Representa uma instância da Classe
	 *                          {@code CallCenter} referente à um registro da tabela
	 *                          {@code AD_TMSCAL}.
	 * @param notasCallCenter   Representa uma lista de instâncias da Classe
	 *                          {@code NotaCallCenter} referente à um registro da
	 *                          tabela {@code AD_TMSCALNOTA}.
	 * @throws SkalaModelException
	 */
	public static void mesclarOcorrencias(CallCenter callCenterDestino, Collection<NotaCallCenter> notasCallCenter)
			throws SkalaModelException {
		CallCenter callCenterOrigem;
		OcorrenciaCallCenter ocorrenciaCallCenter;

		callCenterDestino.loadOcorrencias();

		for (NotaCallCenter nota : notasCallCenter) {
			callCenterOrigem = new CallCenter();
			callCenterOrigem.loadByPk(nota.getCodCal());
			
			for (OcorrenciaCallCenter oco : callCenterOrigem.getOcorrencias()) {

				if (!callCenterDestino.getOcorrencias().contains(oco)) {
					ocorrenciaCallCenter = new OcorrenciaCallCenter();
					ocorrenciaCallCenter.setCodCal(callCenterDestino.getCodCal());
					ocorrenciaCallCenter.setSequencia(oco.getSequencia());
					ocorrenciaCallCenter.setDhOcorrencia(oco.getDhOcorrencia());
					ocorrenciaCallCenter.setCodOco(oco.getCodOco());
					ocorrenciaCallCenter.setDhEnvTransp(oco.getDhEnvTransp());
					ocorrenciaCallCenter.setUsuarioExt(oco.getUsuarioExt());
					ocorrenciaCallCenter.setCodParcCausador(oco.getCodParcCausador());
					ocorrenciaCallCenter.setObservacao(oco.getObservacao());
					ocorrenciaCallCenter.setSetoRresp(oco.getSetoRresp());
					ocorrenciaCallCenter.setVlrOcorrencia(oco.getVlrOcorrencia());
					ocorrenciaCallCenter.setDhAgend(oco.getDhAgend());
					ocorrenciaCallCenter.setNuNota(oco.getNuNota());

					ocorrenciaCallCenter.persists();
					
					callCenterDestino.getOcorrencias().add(ocorrenciaCallCenter);
				}

			}
		}

	}
}

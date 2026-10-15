package tech.skala.modules.logistica.callcenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.modules.logistica.callcenter.CallCenterExceptions.OcorrenciaCallCenterNaoGeradaException;



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
	
}

package tech.skala.action.integration.transport.callcenter;

import java.math.BigDecimal;

import tech.skala.action.integration.transport.CallCenterExceptions;
import tech.skala.action.integration.transport.CallCenterExceptions.NotaNaoRelacionadaCallCenterException;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * Classe utilitária contendo métodos para manipulação de dados da classe
 * {@code NotaCallCenter}, que representa a tabela {@code AD_TMSCALNOTA}
 * 
 * @author hiagoalexandre
 * @since 2023-02-21
 */
public class NotaCallCenterProcessor {

	private NotaCallCenterProcessor() {

	}
	
	/**
	 * Método cuja responsabilidade é relacionar um determinado {@code NUNOTA} em um
	 * Call Center destino, informado como parâmetro.
	 * 
	 * <p>
	 * Após a relação da NF-e com o Call Center, a mesma é inserida dentro da Lista
	 * de Notas presente no objeto {@code callCenter} informado no parâmetro.
	 * </p>
	 * 
	 * @param callCenter Representa uma instância da Classe {@code CallCenter}
	 *                   referente à um registro da tabela {@code AD_TMSCAL}.
	 * @param nuNota     Representa o Número Único da Nota Fiscal, que será
	 *                   relacionada ao Call Center.
	 * @throws NotaNaoRelacionadaCallCenterException
	 */
	public static void relacionaNotaCallCenter(CallCenter callCenter, BigDecimal nuNota)
			throws NotaNaoRelacionadaCallCenterException {
	
		try {
			NotaCallCenter nota = new NotaCallCenter();
			nota.setCodCal(callCenter.getCodCal());
			nota.setNuNota(nuNota);
			nota.persists();

			callCenter.getNotas().add(nota);
		} catch (SkalaModelException e) {
			throw new CallCenterExceptions.NotaNaoRelacionadaCallCenterException(e);
		}
	}
	
}

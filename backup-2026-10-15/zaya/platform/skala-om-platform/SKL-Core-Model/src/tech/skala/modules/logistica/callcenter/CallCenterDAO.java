package tech.skala.modules.logistica.callcenter;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.logistica.ordemcarga.OrdemCarga;

public class CallCenterDAO {

	private CallCenterDAO() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Método cuja responsabilidade é buscar um Call Center que transporte a NF-e
	 * informada como parâmetro.
	 * 
	 * <p>
	 * Caso o sistema <b>encontre</b> um Call Center que carregue a NF-e informada,
	 * um objeto da Classe {@code Call Center} será retornado. <br>
	 * Caso o sistema <b>não encontre</b> será criado um Call Center com informações
	 * básicas, e a NF-e informada como parâmetro, será relacionada ao mesmo. Após
	 * este processo, o objeto da Classe {@code Call Center} será retornado.
	 * </p>
	 * 
	 * @param nfeVO Representa uma instância da interface {@code DynamicVO}
	 *              referente à um registro da tabela {@code TGFCAB}.
	 * @return callCenter Retorna uma instância da Classe {@code Call Center}
	 * 
	 * @throws SkalaModelException
	 * @see criaCallCenterBasico
	 * @see relacionaNotaCallCenter
	 */
	public static CallCenter buscaCallCenterNoSistema(DynamicVO nfeVO) throws SkalaModelException {

		CallCenter callCenter = null;

		BigDecimal codEmp = nfeVO.asBigDecimal(SklFieldName.CODEMP);
		BigDecimal nuNota = nfeVO.asBigDecimal(SklFieldName.NUNOTA);
		BigDecimal ordemCargaNFe = nfeVO.asBigDecimal(SklFieldName.ORDEMCARGA);

		try {
			OrdemCarga ordemCarga = new OrdemCarga();
			ordemCarga.loadByPk(codEmp, ordemCargaNFe);

			BigDecimal codParcTransp = ordemCarga.getCodParcTransp();

			Collection<DynamicVO> callNotasVO = NotasCallCenterDAO.buscarNotasCallCenterPorNuNotaParcTransp(nuNota,
					codParcTransp);

			if (callNotasVO.isEmpty()) {
				callCenter = CallCenterProcessor.criaCallCenterBasico(nfeVO, codParcTransp);
				NotaCallCenterProcessor.relacionaNotaCallCenter(callCenter, nuNota);
			} else {
				BigDecimal codCal = callNotasVO.iterator().next().asBigDecimal(SklFieldName.CODCAL);

				callCenter = new CallCenter();
				callCenter.loadByPk(codCal);
			}

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível buscar/gerar um Call Center para a NF-e.", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

		return callCenter;
	}
}

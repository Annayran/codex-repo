package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;
import java.util.Collection;

import com.cbmsoft.modelcore.CbmModelException;
import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;

public class ProcessoProdutivoHelper {

	private ProcessoProdutivoHelper() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Retorna o identificador único Identificador único do <b>Processo Produtivo Padrão</b> 
	 * para o <b>Produto Acabado</b> informado no parâmetro.
	 * <p>
	 * Caso exista mais de um produto acabado padrão uma exceção é disparada.
	 * 
	 * @author cassiomenezes
	 * @since 2021-12-16
	 * 
	 * @param codProdPA - Código do Produto Acabado
	 * @return idProc - Identificador único do Processo Produtivo padrão para o Produto Acabado
	 * @throws CbmModelException
	 * 
	 */
	public static BigDecimal getProcessoProdutivoPadrao(final BigDecimal codProdPA) throws CbmModelException {
		String msg;
		BigDecimal idProc = null;
		
		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append("this.CODPRODPA = ? ");
		sbWhere.append("AND this.IDPROC IN (SELECT A.IDPROC FROM (SELECT T.CODPRC, MAX(T.IDPROC) IDPROC, MAX(T.VERSAO) VERSAO FROM TPRPRC T WHERE T.PADRAO = 'S' GROUP BY T.CODPRC) A)");
		try {
			JapeWrapper produtoAcabadoDAO = JapeFactory.dao(DynamicEntityNames.PRODUTO_ACABADO);
			Collection<DynamicVO> produtosAcabadosVO = produtoAcabadoDAO.find(sbWhere.toString(), codProdPA);
			
			if (produtosAcabadosVO.size() > 1) {
				msg = String.format("Existe mais de um processo produtivo padrão para o Produto %s.", codProdPA);
				throw new CbmModelException(msg);
			}
			
			idProc = produtosAcabadosVO.iterator().next().asBigDecimal("IDPROC");
		} catch (Exception e) {
			msg = String.format("Não foi possível encontrar um Processo Produtivo para o Produto %s", codProdPA);
			msg = CbmModelException.buildErrorMessage(e, msg);

			throw new CbmModelException(msg);
		}
		
		return idProc;
	}
	
	/**
	 * Retorna capacidade padrão registrada para o <b>Centro de Trabalho</b> 
	 * informado no parâmetro.
	 * <p>
	 * Caso não encontre o registro uma exceção é disparada.
	 * Caso a capacidade padrão seja zero uma exceção é disparada.
	 * 
	 * @author cassiomenezes
	 * @since 2021-12-16
	 * 
	 * @param codWcp - Código do Centro de Trabalho
	 * @return Capacidade padrão
	 * @throws CbmModelException
	 */
	public static BigDecimal getCapacidadePadraoWorkCenter(BigDecimal codWcp) throws CbmModelException {
		JapeWrapper workCenterDAO = JapeFactory.dao(DynamicEntityNames.WORK_CENTER);
		DynamicVO workCenterVO;
		try {
			workCenterVO = workCenterDAO.findByPK(codWcp);
		} catch (Exception e) {
			String msg = String.format("[Centro de Trabalho %s] Não foi possível localizar o registro a partir da chave.",
					codWcp);
			msg = CbmModelException.buildErrorMessage(e, msg);
			
			throw new CbmModelException(msg);
		}
		
		BigDecimal qtdCapacidadePad = BigDecimalUtil.getValueOrZero(workCenterVO.asBigDecimal("QTDCAPACIDADEPAD"));
		if (qtdCapacidadePad.longValue() == 0L) {
			String msg = String.format("[Centro de Trabalho %s] A capacidade padrão deve ser <b>maior que zero</b>",
					codWcp);
			
			throw new CbmModelException(msg);
		}
		
		return qtdCapacidadePad;
	}
}

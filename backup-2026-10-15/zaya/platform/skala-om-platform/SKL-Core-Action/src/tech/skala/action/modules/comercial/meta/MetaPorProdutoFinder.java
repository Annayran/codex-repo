package tech.skala.action.modules.comercial.meta;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.beans.meta.MetaPorProduto;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class MetaPorProdutoFinder {

	private static final Logger LOGGER = Logger.getLogger("tech.skala.action.modules.comercial.meta.MetaPorProdutoFinder");
	
	private MetaPorProdutoFinder() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Método responsável por retornar uma lista de Meta Por Produtos, buscando dos
	 * produtos já calculados em uma determinada Meta. Ou seja, busca diretamente da
	 * própria tabela de Meta por Produto.
	 * 
	 * @param codFam
	 * @param codEmp
	 * @param dtRef
	 * @return
	 * @throws SkalaModelException
	 */
	public static List<MetaPorProduto> buscaProdutosPorMetaPorProduto(BigDecimal codFam, BigDecimal codEmp,
			Timestamp dtRef) throws SkalaModelException {

		List<MetaPorProduto> produtos = new LinkedList<>();
		try {
			JapeWrapper metaProdutoDAO = JapeFactory.dao(MetaPorProduto.ENTITY_NAME);

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("this.CODFAM = ? AND this.CODEMP = ? AND this.DTREF = ? ");
			sbWhere.append(
					"AND NOT EXISTS(SELECT 1 FROM TGFITR ITR WHERE ITR.CODREGRA = 36 AND(this.CODPROD BETWEEN ITR.CODINSTPRINC AND ITR.CODINSTPRINCFIN)) ");

			Collection<DynamicVO> vos = metaProdutoDAO.find(sbWhere.toString(), codFam, codEmp, dtRef);

			LOGGER.info(String.format("%s produtos encontrados.", vos.size()));
			
			MetaPorProduto metaPorProduto;
			for (DynamicVO vo : vos) {
				metaPorProduto = new MetaPorProduto(codFam, codEmp, dtRef, vo.asBigDecimal(SklFieldName.CODPROD));
				metaPorProduto.loadByVO(vo);

				produtos.add(metaPorProduto);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar produtos para a Família.", e);
		}

		return produtos;
	}

	/**
	 * Método responsável por retornar uma lista de Meta Por Produto, a partir de
	 * uma determinada Família de Produto. Este método é usado para construir a
	 * Lista de Metas por Produtos, e não apenas carregar.
	 * 
	 * @param codFam
	 * @param codEmp
	 * @param dtRef
	 * @return
	 * @throws SkalaModelException
	 */
	public static List<MetaPorProduto> buscaProdutosPorFamilia(BigDecimal codFam, BigDecimal codEmp, Timestamp dtRef)
			throws SkalaModelException {

		List<MetaPorProduto> produtos = new LinkedList<>();
		try {
			JapeWrapper produtoFamiliaDAO = JapeFactory.dao("AD_PLVGFFAMPRO");

			StringBuilder where = new StringBuilder();
			where.append("this.CODFAM = ? ");
			where.append(
					"AND this.ATIVO = 'S' AND (this.MARCA = 'EXPORTAÇÃO' OR (this.MARCA != 'EXPORTAÇÃO' AND COALESCE(this.AD_SUSPENSO, 'N') = 'N')) ");
			where.append(
					"AND NOT EXISTS(SELECT 1 FROM TGFITR ITR WHERE ITR.CODREGRA = 36 AND(this.CODPROD BETWEEN ITR.CODINSTPRINC AND ITR.CODINSTPRINCFIN)) ");

			Collection<DynamicVO> produtosFamiliaVO = produtoFamiliaDAO.find(where.toString(), codFam);

			MetaPorProduto metaPorProduto;
			for (DynamicVO vo : produtosFamiliaVO) {
				metaPorProduto = new MetaPorProduto(codFam, codEmp, dtRef, vo.asBigDecimal(SklFieldName.CODPROD));
				metaPorProduto.initCampos();

				produtos.add(metaPorProduto);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar produtos para a Família.", e);
		}

		return produtos;
	}

	/**
	 * Método responsável por retornar a quantidade para o Campo Estoque Final
	 * 
	 * @param codFam
	 * @param codEmp
	 * @param codProd
	 * @param dtRef
	 * @return
	 * @throws SkalaModelException
	 */
	public static BigDecimal getEstoqueFinalReferenciaAnterior(BigDecimal codFam, BigDecimal codEmp, BigDecimal codProd,
			Timestamp dtRef) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFFAMMETSKU");
		DynamicVO metaAnteriorVO;

		BigDecimal estoqueFinal = BigDecimal.ZERO;
		Timestamp dtRefAnterior = TimeUtils.dataAdd(dtRef, -1, Calendar.MONTH);
		try {
			metaAnteriorVO = dao.findByPK(codFam, codEmp, dtRefAnterior, codProd);

			if (metaAnteriorVO != null)
				estoqueFinal = metaAnteriorVO.asBigDecimalOrZero("ESTOQUE_FINAL");

		} catch (Exception e) {
			String msg = String.format(
					"[CODPROD %s][DTREF %s] Não foi possível obter informações de estoque final da referência informada.",
					codProd, TimeUtils.formataDDMMYYYY(dtRefAnterior));
			SkalaModelException.throwMe(msg, e);
		}

		return estoqueFinal;
	}
}

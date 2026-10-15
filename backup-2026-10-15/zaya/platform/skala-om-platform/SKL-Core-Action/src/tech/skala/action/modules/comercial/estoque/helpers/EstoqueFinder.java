package tech.skala.action.modules.comercial.estoque.helpers;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class EstoqueFinder {

	/**
	 * Método responsável por retornar <b>UM</b> registro da tabela de estoque
	 * {@code TGFEST} que possui as informações de Data de Validade e Data de
	 * Fabricação preenchidas corretamente.
	 * 
	 * @param codProd
	 * @param controle
	 * @return DynamicVO retorna um DynamicVO contendo as informações deste registro
	 *         encontrado.
	 * @throws SkalaModelException
	 */
	public static DynamicVO buscaEstoqueComDataValidade(BigDecimal codProd, String controle)
			throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ESTOQUE);

		DynamicVO vo = null;

		try {
			vo = dao.findOne(
					"this.CODPROD = ? AND this.CONTROLE = ? AND (this.DTVAL IS NOT NULL AND this.DTFABRICACAO IS NOT NULL)",
					codProd, controle);
		} catch (Exception e) {
			String msg = String.format(
					"[CODPROD: %s CONTROLE: %s] Não foi possível localizar estoque com informações de Validade e Fabricação preenchidas.",
					codProd, controle);

			SkalaModelException.throwMe(msg, e);
		}

		return vo;
	}
	
	/**
	 * Método responsável por retornar uma <b>Coleção</b> de registros da tabela de estoque
	 * {@code TGFEST} que não possuem as informações de Data de Validade e Data de
	 * Fabricação preenchidas corretamente.
	 * 
	 * @param codProd
	 * @param controle
	 * @return Collection<DynamicVO> retorna uma coleção do tipo DynamicVO contendo as informações destes registros
	 *         encontrado.
	 * @throws SkalaModelException
	 */
	public static Collection<DynamicVO> buscaEstoquesSemDataValidade(BigDecimal codProd, String controle)
			throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ESTOQUE);

		Collection<DynamicVO> vos = null;

		try {
			vos = dao.find(
					"this.CODPROD = ? AND this.CONTROLE = ? AND (this.DTVAL IS NULL OR this.DTFABRICACAO IS NULL)",
					codProd, controle);
		} catch (Exception e) {
			String msg = String.format(
					"[CODPROD: %s CONTROLE: %s] Não foi possível localizar estoques sem informações de Validade/Fabricação preenchidas.",
					codProd, controle);

			SkalaModelException.throwMe(msg, e);
		}

		return vos;
	}
}

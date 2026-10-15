package tech.skala.action.beans.produto;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class FamiliaProdutoFinder {

	private FamiliaProdutoFinder() {
		throw new IllegalStateException("Utility class");
	}

	public static FamiliaProduto findByCodProd(BigDecimal codProd) throws RecordNotFoundException {
		JapeWrapper produtoDAO = JapeFactory.dao(DynamicEntityNames.PRODUTO);

		FamiliaProduto familiaProduto = null;
		try {
			DynamicVO vo = produtoDAO.findByPK(codProd);
			familiaProduto = findByCodGrupoProd(vo.asBigDecimal(SklFieldName.CODGRUPOPROD));
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
		
		return familiaProduto;
	}

	public static FamiliaProduto findByCodGrupoProd(BigDecimal codGrupoProd) throws RecordNotFoundException {
		JapeWrapper grupoFamiliaDAO = JapeFactory.dao("AD_PLTGFFAMGRU");

		FamiliaProduto familiaProduto = null;
		try {
			DynamicVO vo = grupoFamiliaDAO.findOne("this.CODGRUPOPROD = ?", codGrupoProd);
			BigDecimal codFam = vo.asBigDecimal(SklFieldName.CODFAM);

			familiaProduto = new FamiliaProduto();
			familiaProduto.loadByPk(codFam);
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

		return familiaProduto;
	}

}

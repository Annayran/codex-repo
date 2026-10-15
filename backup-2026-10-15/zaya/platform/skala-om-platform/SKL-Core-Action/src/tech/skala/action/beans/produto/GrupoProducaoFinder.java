package tech.skala.action.beans.produto;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class GrupoProducaoFinder {

	private GrupoProducaoFinder() {
		throw new IllegalStateException("Utility class");
	}
	
	public static BigDecimal findCodGrupoProducaoPai(BigDecimal codGProd) throws RecordNotFoundException {
		if (codGProd == null)
			return null;
		
		JapeWrapper dao = JapeFactory.dao("GrupoProducaoProduto");

		BigDecimal codGProdPai = null;
		try {
			DynamicVO vo = dao.findByPK(codGProd);
			
			codGProdPai = vo.asBigDecimal("CODGPRODPAI");
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
		
		return codGProdPai;
	}
}

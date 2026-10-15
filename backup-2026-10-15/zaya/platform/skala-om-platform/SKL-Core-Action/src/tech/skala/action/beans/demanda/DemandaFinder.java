package tech.skala.action.beans.demanda;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class DemandaFinder {

	private DemandaFinder() {
		throw new IllegalStateException("Utility class");
	}
	
	public static DynamicVO getUltimaDemanda(Timestamp dtRef) throws SkalaModelException {
		JapeWrapper demandaDAO = JapeFactory.dao("AD_PLTGFDMCAB");
		
		DynamicVO vo = null;
		try {
			vo = demandaDAO.findOne(
					"this.NUDEMANDA = (SELECT MAX(NUDEMANDA) FROM AD_PLTGFDMCAB CAB WHERE CAB.DTDEMANDA <= ?)", dtRef);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Demanda", e);
		}

		return vo;
	}
	
	public static Collection<DynamicVO> getItensDemandaByNuDemanda(BigDecimal nuDemanda) throws SkalaModelException {
		JapeWrapper itemDemandaDAO = JapeFactory.dao("AD_PLTGFDMITE");
		Collection<DynamicVO> itensDemandaVO = new LinkedList<>();

		try {
			itensDemandaVO = itemDemandaDAO.find("this.NUDEMANDA = ?", nuDemanda);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Demanda", e);
		}

		return itensDemandaVO;

	}
	
	public static Collection<DynamicVO> getItensDemandaByReferenciaEProduto(Timestamp dtRef, BigDecimal codProd)
			throws SkalaModelException {
		JapeWrapper itemDemandaDAO = JapeFactory.dao("AD_PLTGFDMITE");
		Collection<DynamicVO> itensDemandaVO = new LinkedList<>();

		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append("this.NUDEMANDA = ");
		sbWhere.append("(SELECT MAX(NUDEMANDA) FROM AD_PLTGFDMCAB CAB WHERE CAB.DTDEMANDA <= ?) ");
		sbWhere.append("AND this.CODPROD = ? ");

		try {
			itensDemandaVO = itemDemandaDAO.find(sbWhere.toString(), dtRef, codProd);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Demanda", e);
		}

		return itensDemandaVO;

	}
	
}

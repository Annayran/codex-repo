package tech.skala.action.modules.manufatura.processoprodutivo;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProcessoProdutivoFinder {
	
	public static ProcessoProdutivoResumo buscaProcessoProdutivoPorCodWcp(BigDecimal codWcp) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao("AD_PLTPRWCXPRC");

		ProcessoProdutivoResumo processoProdutivo = null;
		BigDecimal codPrc = null;
		try {
			DynamicVO vo = dao.findByPK(codWcp);
			
			if(vo != null) {
				codPrc = vo.asBigDecimal("CODPRC");
				processoProdutivo = new ProcessoProdutivoResumo();
				processoProdutivo.loadByPk(codPrc);				
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar o Processo Produtivo.", e);
		}

		return processoProdutivo;
	}
	
	public static BigDecimal buscaCodPrcPorIdProc(BigDecimal idProc) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.PROCESSO_PRODUTIVO);

		BigDecimal codPrc = null;
		try {
			DynamicVO vo = dao.findByPK(idProc);
			
			if(vo != null)
				codPrc = vo.asBigDecimal("CODPRC");
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar o Processo Produtivo.", e);
		}

		return codPrc;
	}
	
	public static ProcessoProdutivoResumo buscaProcessoProdutivoPorCodPrc(BigDecimal codPrc) 
			throws SkalaModelException {
		ProcessoProdutivoResumo processoProdutivo = null;

		try {
			processoProdutivo = new ProcessoProdutivoResumo();
			processoProdutivo.loadByPk(codPrc);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar o Processo Produtivo.", e);
		}

		return processoProdutivo;
	}
}

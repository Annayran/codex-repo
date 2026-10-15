package tech.skala.action.modules.comercial.estoque.helpers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.comercial.estoque.beans.CabecalhoNota;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class CabecalhoNotaEstoqueNegativo extends CabecalhoNota {
	private BigDecimal codEmp;
	private BigDecimal codParcEstoque;
	private BigDecimal codLocal;

	protected CabecalhoNotaEstoqueNegativo(BigDecimal nuNotaModel, BigDecimal codEmp, BigDecimal codParc, BigDecimal codParcEstoque, BigDecimal codLocal, String observacao) throws SkalaModelException {
		super(nuNotaModel, codParc, observacao);
		
		this.codEmp = codEmp;
		this.codParcEstoque = codParcEstoque;
		this.codLocal = codLocal;
	}

	@Override
	public void buildItems() throws SkalaModelException {
		try {
			JapeWrapper negativeStockDAO = JapeFactory.dao("AD_PLVGFESTNEG");
			Collection<DynamicVO> itemsVO = negativeStockDAO.find(
					"this.CODEMP = ? AND this.CODPARC = ? AND this.CODLOCAL = ?", codEmp, codParcEstoque, codLocal);
			Collection<PrePersistEntityState> itensNota = new ArrayList<>();
			CACHelper cacHelper = new CACHelper();
			
			DynamicVO itemVO;
			
			for (DynamicVO vo : itemsVO) {
				itemVO = (DynamicVO) EntityFacadeFactory.getDWFFacade().getDefaultValueObjectInstance(DynamicEntityNames.ITEM_NOTA);
				itemVO.setProperty("CODPROD", vo.getProperty("CODPROD"));
				itemVO.setProperty("CODLOCALORIG", vo.getProperty("CODLOCAL"));
				itemVO.setProperty("CONTROLE", vo.getProperty("CONTROLE"));
				
				Double qtdNeg = Math.abs(vo.asDouble("ESTOQUE"));
				
				itemVO.setProperty("QTDNEG", BigDecimalUtil.getValueOrZero(BigDecimal.valueOf(qtdNeg)));
								
				PrePersistEntityState itePreState = PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(), 
						DynamicEntityNames.ITEM_NOTA, itemVO);
				itensNota.add(itePreState);
			}
			
				cacHelper.incluirAlterarItem(super.getNuNota(), super.authInfo, itensNota, true);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir os itens.", e);
		} 
	}
	
	@Override
	public String toString() {		
		return String.format("<br />[NUNOTA %s] Nota de Compra gerada com sucesso.<br /><br />", getNuNota());
	}
}

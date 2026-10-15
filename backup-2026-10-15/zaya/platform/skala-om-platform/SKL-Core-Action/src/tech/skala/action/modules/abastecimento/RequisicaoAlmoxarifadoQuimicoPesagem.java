package tech.skala.action.modules.abastecimento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.comercial.estoque.beans.CabecalhoNota;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class RequisicaoAlmoxarifadoQuimicoPesagem extends CabecalhoNota {
	private static final BigDecimal MODELO_REQUISICAO_ALMOX_QUIMICO_PESAGEM = BigDecimal.valueOf(2254207);

	private BigDecimal nuReq;
	private BigDecimal codLocalOrig = BigDecimal.valueOf(100101);
	
	public RequisicaoAlmoxarifadoQuimicoPesagem(BigDecimal nuReq) throws SkalaModelException {
		super(MODELO_REQUISICAO_ALMOX_QUIMICO_PESAGEM);

		this.nuReq = nuReq;
	}

	@Override
	public void buildItems() throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(SklEntityName.PRODUTO_REQUISICOES);

		Collection<DynamicVO> vos = null;
		
		try {
			vos = dao.find("this.NUREQ = ?", nuReq);
		
		
		Collection<PrePersistEntityState> itensNota = new ArrayList<>();
		CACHelper cacHelper = new CACHelper();
		
		DynamicVO itemVO;
		
		for (DynamicVO vo : vos) {
			itemVO = (DynamicVO) EntityFacadeFactory.getDWFFacade().getDefaultValueObjectInstance(DynamicEntityNames.ITEM_NOTA);
			itemVO.setProperty(SklFieldName.CODPROD, vo.getProperty(SklFieldName.CODPROD));
			itemVO.setProperty(SklFieldName.QTDNEG, vo.getProperty(SklFieldName.QTDSOLICITADO));
			itemVO.setProperty(SklFieldName.CODLOCALORIG, codLocalOrig);
			itemVO.setProperty(SklFieldName.CODVOL, vo.asString("Produto.CODVOL"));
			
			PrePersistEntityState itePreState = PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(), 
					DynamicEntityNames.ITEM_NOTA, itemVO);
			itensNota.add(itePreState);
		}
		
		cacHelper.incluirAlterarItem(super.getNuNota(), super.authInfo, itensNota, true);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Itens da Requisição", e);
		}
		
	}

}
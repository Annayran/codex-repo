package br.com.zaya.like.Events.validacoes;

import java.math.BigDecimal;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.zaya.like.Repositories.ConsultasUtil;

public class ValidacaoItensPedidoCompraRevenda implements EventoProgramavelJava {

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		
		validarInclusao(vo);
		
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
        DynamicVO iteVO = (DynamicVO) event.getVo();
        
        boolean ehTopPedCompraRevenda = ConsultasUtil.ehTopPedCompraRevenda(iteVO.asBigDecimal("NUNOTA"));
        
        if(!ehTopPedCompraRevenda ) {
        	return;
        }
        throw new Exception("Não é permitido excluir itens/pedidos com origem do Portal de Proposta/Negociação");
	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {

        DynamicVO iteVO = (DynamicVO) event.getVo();
        ModifingFields mdf = event.getModifingFields();
        
        boolean ehTopPedCompraRevenda = ConsultasUtil.topProibeExclusao(iteVO.asBigDecimal("NUNOTA"));
        
        if(!ehTopPedCompraRevenda) {
        	return;
        }
        
        BigDecimal codUsu = AuthenticationInfo.getCurrent().getUserID();
        
        if((mdf.isModifing("CODLOCALORIG") || mdf.isModifing("QTDNEG")) && !(ConsultasUtil.usuarioPodeAlterarPedido(codUsu))) {
        	throw new Exception("Não é permitido alterar o Local e/ou Quantidade em pedidos com origem do Portal de Proposta/Negociação");
        }
		
	}

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeCommit(TransactionContext tranCtx) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private void validarInclusao(DynamicVO vo) throws Exception {
		boolean ehTopPedCompraRevenda = ConsultasUtil.topProibeExclusao(vo.asBigDecimal("NUNOTA"));
		
		BigDecimal codUsu = AuthenticationInfo.getCurrent().getUserID();
		
		if (ehTopPedCompraRevenda &&  !ConsultasUtil.usuarioPodeAlterarPedido(codUsu)) {
			throw new Exception("Não é permitido lançar uma Pedido de compra com este tipo de operação(Pedido de compra para Revenda) ");
		}

	}

}
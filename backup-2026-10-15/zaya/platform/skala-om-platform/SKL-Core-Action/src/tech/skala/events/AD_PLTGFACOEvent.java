package tech.skala.events;

import java.math.BigDecimal;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class AD_PLTGFACOEvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {

	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
		
		DynamicVO vo = (DynamicVO) event.getVo();
		
		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		
		JapeWrapper varDAO = JapeFactory.dao(DynamicEntityNames.COMPRA_VENDA_VARIOS_PEDIDO);
		DynamicVO varVO = varDAO.findOne("this.NUNOTAORIG = ?", nuNota);
		
		if(varVO != null) {
			String msg = String.format("[PEDIDO %s] Não é possível excluir o Acordo do Pedido.<br><b>O Pedido já foi faturado.</b>", nuNota);
			throw new SkalaModelException(msg);
		}
	}

	@Override
	public void beforeInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {

		DynamicVO vo = (DynamicVO) event.getVo();
		DynamicVO oldVO = (DynamicVO) event.getOldVO();
		ModifingFields modifingFields = event.getModifingFields();

		if (modifingFields.isModifing("PERCDESC")) {
			throw new SkalaModelException("Não é possível alterar Percentual Desconto Financeiro após aprovação.");
		}

	}

}

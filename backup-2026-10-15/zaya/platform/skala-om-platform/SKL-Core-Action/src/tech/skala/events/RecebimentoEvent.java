package tech.skala.events;

import java.math.BigDecimal;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityInsert;

public class RecebimentoEvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {
		
	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception {
		
	}

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception {
		
	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		
	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
		
		DynamicVO oldVO = (DynamicVO) event.getVo();
		
		BigDecimal nuRecebimento = oldVO.asBigDecimal(SklFieldName.NURECEBIMENTO);
		BigDecimal codUsu = AuthenticationInfo.getCurrent().getUserID();
		
		EntityInsert insert = new EntityInsert("AD_TGWRECEXC");
		insert.setField(SklFieldName.NURECEBIMENTO, nuRecebimento);
		insert.setField(SklFieldName.CODUSU, codUsu);
		insert.setField("DHEXCLUSAO", TimeUtils.getNow());
		
		insert.fluidInsert();
	}

	@Override
	public void beforeInsert(PersistenceEvent arg0) throws Exception {
		
	}

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception {
		
	}

}

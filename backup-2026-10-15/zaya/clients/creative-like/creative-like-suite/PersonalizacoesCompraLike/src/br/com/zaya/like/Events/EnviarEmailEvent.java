package br.com.zaya.like.Events;

import java.math.BigDecimal;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.like.Actions.services.email.EmailServices;
import br.com.zaya.like.helper.email.EmailHelper;

public class EnviarEmailEvent implements EventoProgramavelJava  {

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		
		EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
		
		BigDecimal codUsu = AuthenticationInfo.getCurrent().getUserID();
		BigDecimal nunota = vo.asBigDecimal("NUNOTA");
		String ocorrencias = vo.asString("OCORRENCIAS");
		BigDecimal sequencia = vo.asBigDecimal("SEQUENCIA");
		
		EmailServices emailServices = new EmailServices();
		
		emailServices.enviarEmailAcompanhamento(nunota, sequencia, ocorrencias, codUsu, dwfFacade);
		
	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeCommit(TransactionContext tranCtx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

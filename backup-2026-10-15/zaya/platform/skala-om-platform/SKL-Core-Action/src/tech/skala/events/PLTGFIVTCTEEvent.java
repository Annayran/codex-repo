package tech.skala.events;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.inventario.ContagemInventarioStatusEnum;

public class PLTGFIVTCTEEvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		
		String statusConferencia = vo.asString("STATUS");
		if(!statusConferencia.equals(ContagemInventarioStatusEnum.PENDENTE.getValue())) {
			throw new SkalaModelException("Só é possível excluir contagens com o Status <b>Pendente</b>.");
		}
		
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		
		ModifingFields modifingFields = event.getModifingFields();
		
		if(!modifingFields.isModifing("STATUS")) {
			String statusConferencia = vo.asString("STATUS");
			if(!statusConferencia.equals(ContagemInventarioStatusEnum.PENDENTE.getValue())) {
				throw new SkalaModelException("Só é possível alterar contagens com o Status <b>Pendente.</b>");
			}			
		}
	}
	
	@Override
	public void beforeInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

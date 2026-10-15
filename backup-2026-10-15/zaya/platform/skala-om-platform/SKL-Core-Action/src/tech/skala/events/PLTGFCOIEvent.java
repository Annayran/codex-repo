package tech.skala.events;

import java.math.BigDecimal;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.main.Skl;

public class PLTGFCOIEvent implements EventoProgramavelJava {

	private DynamicVO findByPK;

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
	public void beforeDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInsert(PersistenceEvent arg0) throws Exception {
		DynamicVO vo = (DynamicVO) arg0.getVo();

		//String controleAtual = vo.asString(SklFieldName.CONTROLECONF);
		
		vo.setProperty(SklFieldName.CONTROLE, vo.asString(SklFieldName.CONTROLE).toUpperCase());
		vo.setProperty(SklFieldName.CONTROLECONF, vo.asString(SklFieldName.CONTROLECONF).toUpperCase());
		
	}

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception {
		DynamicVO vo = (DynamicVO) arg0.getVo();
		
		ModifingFields modifingFields = arg0.getModifingFields();
		
		if (modifingFields.isModifingAny("CONTROLE,CONTROLECONF")) {
			vo.setProperty(SklFieldName.CONTROLE, vo.asString(SklFieldName.CONTROLE).toUpperCase());
			vo.setProperty(SklFieldName.CONTROLECONF, vo.asString(SklFieldName.CONTROLECONF).toUpperCase());					
		}
		
		verificaSituacaoConferencia(vo);
				
	}

	private void verificaSituacaoConferencia(DynamicVO vo) {
		JapeWrapper dao = JapeFactory.dao(SklEntityName.MOBILE_CONFERENCIA);
		
		BigDecimal nuConferencia = vo.asBigDecimal(SklFieldName.NUCONFERENCIA);
		
//		dao.findByPK();
		
		//findByPK = dao.findByPK(vo.asString(SklFieldName.SITUACAO));
		
	}

}

package tech.skala.events;

import java.math.BigDecimal;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class VendedorEvent implements EventoProgramavelJava{

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		JapeWrapper dao = JapeFactory.dao("AD_PLTGFREGVENCR");

		DynamicVO vo = (DynamicVO) event.getVo();

		BigDecimal codCenCus = vo.asBigDecimal(SklFieldName.CODCENCUSPAD);
		BigDecimal codVend = vo.asBigDecimal(SklFieldName.CODVEND);

		try {
			DynamicVO regVenVO = dao.findOne("this.ATIVO = 'S' AND this.CODCENCUS = ?", codCenCus);

			if (regVenVO != null) {
				EntityInsert entityNew = new EntityInsert("AD_PLTGFREGVENCRV");
				entityNew.setField("CODIGO", regVenVO.asBigDecimal("CODIGO"));
				entityNew.setField(SklFieldName.CODCENCUS, codCenCus);
				entityNew.setField(SklFieldName.CODVEND, codVend);
				entityNew.fluidInsert();
			}
		} catch (Exception e) {
			SkalaModelException.throwMe(
					"Não foi possível salvar vendedor. Centro de Resultado não cadastrado na tabela de Regional.", e);
		}

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

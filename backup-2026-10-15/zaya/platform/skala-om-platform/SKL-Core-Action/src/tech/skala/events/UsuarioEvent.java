package tech.skala.events;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityInsert;

public class UsuarioEvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		String cpf = vo.asString("CPF");
		
		BigDecimal codGrupo = vo.asBigDecimalOrZero("CODGRUPO");
		
		if (cpf != null || codGrupo.longValue() != 27) {
			BigDecimal codUsu = vo.asBigDecimal(SklFieldName.CODUSU);
	
			JapeWrapper funcionarioDAO = JapeFactory.dao(DynamicEntityNames.FUNCIONARIO);
			Collection<DynamicVO> funcionarioVOs = funcionarioDAO.find("this.CPF = ? AND NOT EXISTS(SELECT 1 FROM AD_PLTSIUSUXFUN T WHERE T.CODEMP = this.CODEMP AND T.CODFUNC = this.CODFUNC)", cpf);
	
			for (DynamicVO funcionarioVO : funcionarioVOs) {
				EntityInsert insert = new EntityInsert("AD_PLTSIUSUXFUN");
				insert.setField(SklFieldName.CODUSU, codUsu);
				insert.setField(SklFieldName.CODEMP, funcionarioVO.asBigDecimal(SklFieldName.CODEMP));
				insert.setField(SklFieldName.CODFUNC, funcionarioVO.asBigDecimal(SklFieldName.CODFUNC));
				insert.fluidInsert();
			}
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

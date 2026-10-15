package tech.skala.events;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class FuncionarioEvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {

	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {

		DynamicVO vo = (DynamicVO) event.getVo();

		insertRamalFuncionario(vo);

	}

	private void insertRamalFuncionario(DynamicVO vo) throws SkalaModelException {
		try {
			BigDecimal codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
			BigDecimal codFunc = vo.asBigDecimal(SklFieldName.CODFUNC);
			BigDecimal codDep = vo.asBigDecimal("CODDEP");
			BigDecimal codFuncao = vo.asBigDecimal("CODFUNCAO");

			JapeWrapper departamentoDAO = JapeFactory.dao(DynamicEntityNames.DEPARTAMENTO);
			DynamicVO departamentoVO = departamentoDAO.findByPK(codDep);
			
			if(departamentoVO != null) {
				
				BigDecimal codCenCus = departamentoVO.asBigDecimal(SklFieldName.CODCENCUS);
				
				JapeWrapper ramaisDAO = JapeFactory.dao("AD_PLTSIRAMAL");
				DynamicVO ramalVO = ramaisDAO.findOne("this.CODEMP = ? AND this.CODFUNC = ?", codEmp, codFunc);
				
				if (ramalVO == null) {
					
					EntityInsert entityInsert = new EntityInsert("AD_PLTSIRAMAL");
					entityInsert.setField(SklFieldName.CODEMP, codEmp);
					entityInsert.setField(SklFieldName.CODFUNC, codFunc);
					entityInsert.setField("CODFUNCAO", codFuncao);
					entityInsert.setField(SklFieldName.CODCENCUS, codCenCus);
					
					entityInsert.fluidInsert();
				}
				
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir Funcionário da tela de Ramais.", e);
		}
		
		
		
	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {

		DynamicVO vo = (DynamicVO) event.getVo();
		ModifingFields modifingFields = event.getModifingFields();
		
		if(modifingFields.isModifing("DTDEM")) {
			deleteRamalFuncionario(vo);
//			inativaUsuario(vo);
		}
		
		
	}

	@Deprecated
	private void inativaUsuario(DynamicVO vo) throws SkalaModelException {

		try {
			JapeWrapper usuXFunDAO = JapeFactory.dao("AD_PLTSIUSUXFUN");
			Collection<DynamicVO> usuXFunVOs = usuXFunDAO.find("this.CODEMP = ? AND this.CODFUNC = ?",
					vo.asBigDecimal(SklFieldName.CODEMP), vo.asBigDecimal(SklFieldName.CODFUNC));

			JapeWrapper usuarioDAO = JapeFactory.dao("Usuario");
			Timestamp dtDem = vo.asTimestamp("DTDEM");

			for (DynamicVO usuXFunVO : usuXFunVOs) {
				BigDecimal codUsu = usuXFunVO.asBigDecimal(SklFieldName.CODUSU);

				DynamicVO usuarioVO = usuarioDAO.findByPK(codUsu);
				Timestamp dtLimAcesso = usuarioVO.asTimestamp("DTLIMACESSO");

				if (dtLimAcesso == null) {
					String nomeUsu = usuarioVO.asString("NOMEUSU");
					EntityUpdate update = new EntityUpdate("Usuario", usuarioVO);
					update.setField("NOMEUSU", "Z" + nomeUsu);
					update.setField("DTLIMACESSO", TimeUtils.dataAdd(dtDem, -1, Calendar.DAY_OF_MONTH));
					update.fluidUpdate();
				}
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível inativar o usuário.", e);

		}

	}

	private void deleteRamalFuncionario(DynamicVO vo) throws SkalaModelException {
		try {
			
			BigDecimal codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
			BigDecimal codFunc = vo.asBigDecimal(SklFieldName.CODFUNC);
			
			JapeWrapper ramaisDAO = JapeFactory.dao("AD_PLTSIRAMAL");
			DynamicVO ramalVO = ramaisDAO.findOne("this.CODEMP = ? AND this.CODFUNC = ?", codEmp, codFunc);
			if(ramalVO != null) {
				ramaisDAO.deleteByCriteria("this.CODEMP = ? AND this.CODFUNC = ?", codEmp, codFunc);
			}
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível excluir Funcionário da tela de Ramais.", e);
		}
	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {

	}

	@Override
	public void beforeDelete(PersistenceEvent arg0) throws Exception {

	}

	@Override
	public void beforeInsert(PersistenceEvent arg0) throws Exception {

	}

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception {

	}

}

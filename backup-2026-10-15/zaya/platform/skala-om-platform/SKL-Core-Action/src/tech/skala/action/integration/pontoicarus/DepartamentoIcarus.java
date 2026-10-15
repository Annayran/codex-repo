package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class DepartamentoIcarus implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject, Persistable {
	public static final String					ENTITY_NAME = "AD_PLTPILOTACAO";
	
	private BigDecimal							codDep;
	private BigDecimal							codEmp;
	private BigDecimal							idLotacao;
	
	public BigDecimal getCodDep() {
		return codDep;
	}
	public void setCodDep(BigDecimal codDep) {
		this.codDep = codDep;
	}
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getIdLotacao() {
		return idLotacao;
	}
	public void setIdLotacao(BigDecimal idLotacao) {
		this.idLotacao = idLotacao;
	}
	
	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		try {
			DynamicVO vo = dao.findByPK(keyValue);
			loadByVO(vo);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codDep = vo.asBigDecimal("CODDEP");
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.idLotacao = vo.asBigDecimal("IDLOTACAO");
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		DynamicVO vo = null;
		try {
			vo = dao.findByPK(codDep, idLotacao);
			
			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField("CODDEP", codDep);
				entityInsert.setField(SklFieldName.CODEMP, codEmp);
				entityInsert.setField("IDLOTACAO", idLotacao);
				entityInsert.fluidInsert();
			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		return vo;
	}
	
	@Override
	public String toString() {
		return String.format("DepartamentoIcarus [codDep=%s, codEmp=%s, idLotacao=%s]", codDep, codEmp, idLotacao);
	}
	
}

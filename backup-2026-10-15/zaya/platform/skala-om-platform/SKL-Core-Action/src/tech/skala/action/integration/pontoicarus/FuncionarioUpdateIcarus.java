package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;

public class FuncionarioUpdateIcarus implements EntityBean, LoadableByValueObject, Persistable {
	public static final String					ENTITY_NAME = IcarusConstants.PLTPIFUNUPD;

	private BigDecimal							codEmp;
	private BigDecimal							codFunc;
	private BigDecimal							sequencia;
	private Timestamp							dhInteg;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getCodFunc() {
		return codFunc;
	}
	public void setCodFunc(BigDecimal codFunc) {
		this.codFunc = codFunc;
	}
	public BigDecimal getSequencia() {
		return sequencia;
	}
	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}
	public Timestamp getDhInteg() {
		return dhInteg;
	}
	public void setDhInteg(Timestamp dhInteg) {
		this.dhInteg = dhInteg;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.codFunc = vo.asBigDecimal(SklFieldName.CODFUNC);
		this.sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);
		this.dhInteg = vo.asTimestamp("DHINTEG");
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		
		DynamicVO vo = null;
		try {
			
			vo = dao.findByPK(codEmp, codFunc, sequencia);
			if (vo != null) {
				EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, vo);
				entityUpdate.setField("DHINTEG", dhInteg);
				entityUpdate.fluidUpdate();
			}			
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return vo;
	}	
	@Override
	public String toString() {
		return String.format("FuncionarioUpdateIcarus [codEmp=%s, codFunc=%s, sequencia=%s, dhInteg=%s]", codEmp,
				codFunc, sequencia, dhInteg);
	}


}

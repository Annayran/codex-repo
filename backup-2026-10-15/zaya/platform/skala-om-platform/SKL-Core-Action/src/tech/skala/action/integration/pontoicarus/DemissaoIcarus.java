package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class DemissaoIcarus implements EntityBean, Persistable {
	public static final String					ENTITY_NAME = "AD_PLTPIDEM";

	private BigDecimal							codEmp;
	private BigDecimal							codFunc;
	private Timestamp							dtDem;
	
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
	public Timestamp getDtDem() {
		return dtDem;
	}
	public void setDtDem(Timestamp dtDem) {
		this.dtDem = dtDem;
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		DynamicVO vo = null;
		try {
			vo = dao.findByPK(codEmp, codFunc);
			
			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField(SklFieldName.CODEMP, codEmp);
				entityInsert.setField(SklFieldName.CODFUNC, codFunc);
				entityInsert.setField("DTDEM", dtDem);
				entityInsert.fluidInsert();
			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		
		return vo;
	}
	
	@Override
	public String toString() {
		return String.format("DemissaoIcarus [codEmp=%s, codFunc=%s, dtDem=%s]", codEmp, codFunc, dtDem);
	}

}

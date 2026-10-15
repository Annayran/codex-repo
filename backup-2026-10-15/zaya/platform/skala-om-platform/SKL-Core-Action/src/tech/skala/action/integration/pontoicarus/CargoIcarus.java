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

public class CargoIcarus implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject, Persistable {
	public static final String					ENTITY_NAME = "AD_PLTPICARGO";
	
	private BigDecimal							codCargo;
	private BigDecimal							codEmp;
	private BigDecimal							idCargo;
	
	public BigDecimal getCodCargo() {
		return codCargo;
	}
	public void setCodCargo(BigDecimal codCargo) {
		this.codCargo = codCargo;
	}
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getIdCargo() {
		return idCargo;
	}
	public void setIdCargo(BigDecimal idCargo) {
		this.idCargo = idCargo;
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
		this.codCargo = vo.asBigDecimal("CODCARGO");
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.idCargo = vo.asBigDecimal("IDCARGO");
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		DynamicVO vo = null;
		try {
			vo = dao.findByPK(codCargo, idCargo);
			
			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField("CODCARGO", codCargo);
				entityInsert.setField(SklFieldName.CODEMP, codEmp);
				entityInsert.setField("IDCARGO", idCargo);
				entityInsert.fluidInsert();
			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		return vo;
	}
	
	@Override
	public String toString() {
		return String.format("CargoIcarus [codCargo=%s, codEmp=%s, idCargo=%s]", codCargo, codEmp, idCargo);
	}
	
}

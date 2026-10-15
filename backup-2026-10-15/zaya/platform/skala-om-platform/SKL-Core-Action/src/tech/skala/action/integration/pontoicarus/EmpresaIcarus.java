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

public class EmpresaIcarus implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject, Persistable {
	public static final String					ENTITY_NAME = "AD_PLTPIEMP";
	
	private BigDecimal							codEmp;
	private BigDecimal							idCentroCusto;
	private BigDecimal							codigo;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getIdCentroCusto() {
		return idCentroCusto;
	}
	public void setIdCentroCusto(BigDecimal idCentroCusto) {
		this.idCentroCusto = idCentroCusto;
	}	
	public BigDecimal getCodigo() {
		return codigo;
	}
	public void setCodigo(BigDecimal codigo) {
		this.codigo = codigo;
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
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.idCentroCusto = vo.asBigDecimal("IDCENTROCUSTO");
		this.codigo = vo.asBigDecimal("CODIGO");
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		DynamicVO vo = null;
		try {
			vo = dao.findByPK(codEmp, idCentroCusto);
			
			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField(SklFieldName.CODEMP, codEmp);
				entityInsert.setField("IDCENTROCUSTO", idCentroCusto);
				entityInsert.setField("CODIGO", codigo);
				entityInsert.fluidInsert();
			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		return vo;
	}
	
	@Override
	public String toString() {
		return String.format("EmpresaIcarus [codEmp=%s, idCentroCusto=%s, codigo=%s]", codEmp, idCentroCusto, codigo);
	}
}

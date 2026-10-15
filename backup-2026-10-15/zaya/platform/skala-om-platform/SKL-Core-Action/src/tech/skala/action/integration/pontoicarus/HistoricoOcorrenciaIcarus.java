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
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

public class HistoricoOcorrenciaIcarus implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject, Persistable {
	public static final String					ENTITY_NAME = "AD_PLTPIHIS";
	
	private BigDecimal							codEmp;
	private BigDecimal							codHistOcor;
	private BigDecimal							idMotivoAbonoPonto;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getCodHistOcor() {
		return codHistOcor;
	}
	public void setCodHistOcor(BigDecimal codHistOcor) {
		this.codHistOcor = codHistOcor;
	}
	public BigDecimal getIdMotivoAbonoPonto() {
		return idMotivoAbonoPonto;
	}
	public void setIdMotivoAbonoPonto(BigDecimal idMotivoAbonoPonto) {
		this.idMotivoAbonoPonto = idMotivoAbonoPonto;
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
		this.codHistOcor = vo.asBigDecimal("CODHISTOCOR");
		this.idMotivoAbonoPonto = vo.asBigDecimal("IDMOTIVOABONOPONTO");
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		DynamicVO vo = null;
		try {
			vo = dao.findByPK(codEmp, codHistOcor);
			
			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField(SklFieldName.CODEMP, codEmp);
				entityInsert.setField("CODHISTOCOR", codHistOcor);
				entityInsert.setField("IDMOTIVOABONOPONTO", idMotivoAbonoPonto);
				entityInsert.fluidInsert();
			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		return vo;
	}
}

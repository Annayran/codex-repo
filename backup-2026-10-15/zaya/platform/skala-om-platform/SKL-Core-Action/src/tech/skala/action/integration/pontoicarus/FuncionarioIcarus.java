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

public class FuncionarioIcarus implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject, Persistable  {
	public static final String					ENTITY_NAME = "AD_PLTPIFUN";

	private BigDecimal							codEmp;
	private BigDecimal							codFunc;
	private BigDecimal							idPessoa;
	private BigDecimal							idMutuario;
	
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
	public BigDecimal getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(BigDecimal idPessoa) {
		this.idPessoa = idPessoa;
	}
	public BigDecimal getIdMutuario() {
		return idMutuario;
	}
	public void setIdMutuario(BigDecimal idMutuario) {
		this.idMutuario = idMutuario;
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
		this.codFunc = vo.asBigDecimal(SklFieldName.CODFUNC);
		this.idMutuario = vo.asBigDecimal("IDMUTUARIO");
		this.idPessoa = vo.asBigDecimal("IDPESSOA");
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
				entityInsert.setField("IDMUTUARIO", idMutuario);
				entityInsert.setField("IDPESSOA", idPessoa);
				entityInsert.fluidInsert();
			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		
		return vo;
	}
	
	@Override
	public String toString() {
		return String.format("FuncionarioIcarus [codEmp=%s, codFunc=%s, idPessoa=%s, idMutuario=%s]", codEmp, codFunc,
				idPessoa, idMutuario);
	}
	
}

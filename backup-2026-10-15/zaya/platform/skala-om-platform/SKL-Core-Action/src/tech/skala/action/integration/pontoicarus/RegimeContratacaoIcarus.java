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
import tech.skala.core.commons.dd.EntityUpdate;
 /**
  * 
  * @author cassiomenezes
  * @since 2023-04-20
  * 
  */
public class RegimeContratacaoIcarus implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject, Persistable {
	public static final String					ENTITY_NAME = "AD_PLTPIREG";
	
	private BigDecimal							codEmp;
	private String								codigo;
	private String								descricao;
	private BigDecimal							idRegimeContratacao;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getIdRegimeContratacao() {
		return idRegimeContratacao;
	}
	public void setIdRegimeContratacao(BigDecimal idRegimeContratacao) {
		this.idRegimeContratacao = idRegimeContratacao;
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
		this.codigo = vo.asString("CODIGO");
		this.descricao = vo.asString(SklFieldName.DESCRICAO);
		this.idRegimeContratacao = vo.asBigDecimal("IDREGIMECONTRATACAO");
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		DynamicVO vo = null;
		try {
			vo = dao.findByPK(codEmp, codigo);
			
			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField(SklFieldName.CODEMP, codEmp);
				entityInsert.setField("CODIGO", codigo);
				entityInsert.setField(SklFieldName.DESCRICAO, descricao);
				entityInsert.setField("IDREGIMECONTRATACAO", idRegimeContratacao);
				entityInsert.fluidInsert();
			} else {
				EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, vo);
				entityUpdate.setField("IDREGIMECONTRATACAO", idRegimeContratacao);
			}
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		return vo;
	}
	
	@Override
	public String toString() {
		return String.format("RegimeContratacaoIcarus [codEmp=%s, codigo=%s, descricao=%s, idRegimeContratacao=%s]",
				codEmp, codigo, descricao, idRegimeContratacao);
	}
}

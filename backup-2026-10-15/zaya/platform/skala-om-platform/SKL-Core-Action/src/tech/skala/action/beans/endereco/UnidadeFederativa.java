package tech.skala.action.beans.endereco;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class UnidadeFederativa implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = DynamicEntityNames.UNIDADE_FEDERATIVA;

	private BigDecimal							codUf;
	private String								uf;
	private String								descricao;
	private BigDecimal							codIbge;
	
	public BigDecimal getCodUf() {
		return codUf;
	}
	public void setCodUf(BigDecimal codUf) {
		this.codUf = codUf;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getCodIbge() {
		return codIbge;
	}
	public void setCodIbge(BigDecimal codIbge) {
		this.codIbge = codIbge;
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
		this.codUf = vo.asBigDecimal("CODUF");
		this.uf = vo.asString("UF");
		this.descricao = vo.asString(SklFieldName.DESCRICAO);
		this.codIbge = vo.asBigDecimal("CODIBGE");
	}

	@Override
	public String toString() {
		return String.format("UnidadeFederativa [codUf=%s, uf=%s, descricao=%s, codIbge=%s]", codUf, uf, descricao,
				codIbge);
	}

}

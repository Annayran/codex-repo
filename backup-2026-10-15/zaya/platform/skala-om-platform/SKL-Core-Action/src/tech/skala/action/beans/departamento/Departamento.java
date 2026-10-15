package tech.skala.action.beans.departamento;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * Departamentos
 * 
 * @author cassiomenezes
 * @since 2023-04-14
 */
public class Departamento implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = DynamicEntityNames.DEPARTAMENTO;
	
	private BigDecimal							codDep;
	private String								descrDep;
	private boolean								ativo;
	private boolean								analitico;
	
	public Departamento() {
		super();
	}

	public BigDecimal getCodDep() {
		return codDep;
	}
	public void setCodDep(BigDecimal codDep) {
		this.codDep = codDep;
	}
	public String getDescrDep() {
		return descrDep;
	}
	public void setDescrDep(String descrDep) {
		this.descrDep = descrDep;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public boolean isAnalitico() {
		return analitico;
	}
	public void setAnalitico(boolean analitica) {
		this.analitico = analitica;
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
		this.descrDep = ConversorUtils.semAcento(vo.asString("DESCRDEP"));
		this.ativo = vo.asBoolean(SklFieldName.ATIVO);
		this.analitico = vo.asBoolean("ANALITICO");
	}

	@Override
	public String toString() {
		return String.format("Departamento [codDep=%s, descrDep=%s, ativo=%s, analitico=%s]", codDep, descrDep, ativo,
				analitico);
	}
	
}

package tech.skala.action.modules.pessoas.funcionario;

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

public class Cargo implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	private static final String					ENTITY_NAME = DynamicEntityNames.CARGO;
	
	private BigDecimal							codCargo;
	private String								descrCargo;
	private boolean								ativo;
	
	public BigDecimal getCodCargo() {
		return codCargo;
	}
	public void setCodCargo(BigDecimal codCargo) {
		this.codCargo = codCargo;
	}
	public String getDescrCargo() {
		return descrCargo;
	}
	public void setDescrCargo(String descrCargo) {
		this.descrCargo = descrCargo;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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
		this.descrCargo = vo.asString("DESCRCARGO");
		this.ativo = vo.asBoolean(SklFieldName.ATIVO);
	}
	
}

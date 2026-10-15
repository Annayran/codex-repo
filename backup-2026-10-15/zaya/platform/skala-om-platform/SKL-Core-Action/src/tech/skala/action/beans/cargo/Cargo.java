package tech.skala.action.beans.cargo;

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
 * Cargos
 * 
 * @author cassiomenezes
 * @since 2023-04-16 
 */
public class Cargo implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = DynamicEntityNames.CARGO;
	
	private BigDecimal							codCargo;
	private String								descrCargo;
	private BigDecimal							codCbo;
	private boolean								ativo;
	
	public Cargo() {
		super();
	}
	
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
	public BigDecimal getCodCbo() {
		return codCbo;
	}
	public void setCodCbo(BigDecimal codCbo) {
		this.codCbo = codCbo;
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
		this.descrCargo = ConversorUtils.semAcento(vo.asString("DESCRCARGO"));
		this.codCbo = vo.asBigDecimal("CODCBO");
		this.ativo = vo.asBoolean(SklFieldName.ATIVO);
	}

	@Override
	public String toString() {
		return String.format("Cargo [codCargo=%s, descrCargo=%s, codCbo=%s, ativo=%s]", codCargo, descrCargo, codCbo,
				ativo);
	}

}
 
package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class TurnoDiaIcarus implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = "AD_PLTPITURNODIA";

	private BigDecimal							codEmp;
	private BigDecimal							diaSem;
	private String								descansoSem;
	private String								tipoTurnoDia;
	private BigDecimal							idTurnoDia;

	/**
	 * @param codEmp
	 * @param diaSem
	 * @param descansoSem
	 * @param tipoTurnoDia
	 */
	public TurnoDiaIcarus(BigDecimal codEmp, BigDecimal diaSem, String descansoSem, String tipoTurnoDia) {
		this.codEmp = codEmp;
		this.diaSem = diaSem;
		this.descansoSem = descansoSem;
		this.tipoTurnoDia = tipoTurnoDia;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getDiaSem() {
		return diaSem;
	}
	public void setDiaSem(BigDecimal diaSem) {
		this.diaSem = diaSem;
	}
	public String getDescansoSem() {
		return descansoSem;
	}
	public void setDescansoSem(String descansoSem) {
		this.descansoSem = descansoSem;
	}
	public String getTipoTurnoDia() {
		return tipoTurnoDia;
	}
	public void setTipoTurnoDia(String tipoTurnoDia) {
		this.tipoTurnoDia = tipoTurnoDia;
	}
	public BigDecimal getIdTurnoDia() {
		return idTurnoDia;
	}
	public void setIdTurnoDia(BigDecimal idTurnoDia) {
		this.idTurnoDia = idTurnoDia;
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
		this.diaSem = vo.asBigDecimal("DIASEM");
		this.descansoSem = vo.asString("DESCANSOSEM");
		this.tipoTurnoDia = vo.asString("TIPOTURNODIA");
		this.idTurnoDia = vo.asBigDecimal("IDTURNODIA");
	}
	
	@Override
	public String toString() {
		return String.format("TurnoDiaIcarus [codEmp=%s, diaSem=%s, descansoSem=%s, tipoTurnoDia=%s, idTurnoDia=%s]",
				codEmp, diaSem, descansoSem, tipoTurnoDia, idTurnoDia);
	}
}

package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class TurnoPeriodoIcarus implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = "AD_PLTPITURNOPERIODO";

	private BigDecimal							codEmp;
	private BigDecimal							horaEntSai;
	private String								horaTurno;
	private String								tipo;
	private BigDecimal							idturnoPeriodo;
	
	/**
	 * @param codEmp
	 * @param horaEntSai
	 * @param horaTurno
	 * @param tipo
	 */
	public TurnoPeriodoIcarus(BigDecimal codEmp, BigDecimal horaEntSai, String horaTurno, String tipo) {
		super();
		this.codEmp = codEmp;
		this.horaEntSai = horaEntSai;
		this.horaTurno = horaTurno;
		this.tipo = tipo;
	}
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getHoraEntSai() {
		return horaEntSai;
	}
	public void setHoraEntSai(BigDecimal horaEntSai) {
		this.horaEntSai = horaEntSai;
	}
	public String getHoraTurno() {
		return horaTurno;
	}
	public void setHoraTurno(String horaTurno) {
		this.horaTurno = horaTurno;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getIdturnoPeriodo() {
		return idturnoPeriodo;
	}
	public void setIdturnoPeriodo(BigDecimal idturnoPeriodo) {
		this.idturnoPeriodo = idturnoPeriodo;
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
		this.horaEntSai = vo.asBigDecimal("HORAENTSAI");
		this.horaTurno = vo.asString("HORATURNO");
		this.tipo = vo.asString("TIPO");
		this.idturnoPeriodo = vo.asBigDecimal("IDTURNOPERIODO");
	}
	
	@Override
	public String toString() {
		return String.format("TurnoPeriodoIcarus [codEmp=%s, horaEntSai=%s, horaTurno=%s, tipo=%s, idturnoPeriodo=%s]",
				codEmp, horaEntSai, horaTurno, tipo, idturnoPeriodo);
	}
	
}

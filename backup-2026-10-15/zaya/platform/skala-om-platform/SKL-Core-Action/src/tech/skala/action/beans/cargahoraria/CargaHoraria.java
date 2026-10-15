package tech.skala.action.beans.cargahoraria;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class CargaHoraria implements EntityBean, LoadableByValueObject {
	public static final String					ENTITY_NAME = DynamicEntityNames.CARGA_HORARIA;
	
	private BigDecimal							codCargaHor;
	private BigDecimal							diaSem;
	private BigDecimal							turno;
	private boolean								descansoSem;
	private BigDecimal							entrada;
	private BigDecimal							saida;
	
	public BigDecimal getCodCargaHor() {
		return codCargaHor;
	}
	public void setCodCargaHor(BigDecimal codCargaHor) {
		this.codCargaHor = codCargaHor;
	}
	public BigDecimal getDiaSem() {
		return diaSem;
	}
	public void setDiaSem(BigDecimal diaSem) {
		this.diaSem = diaSem;
	}
	public BigDecimal getTurno() {
		return turno;
	}
	public void setTurno(BigDecimal turno) {
		this.turno = turno;
	}
	public boolean isDescansoSem() {
		return descansoSem;
	}
	public void setDescansoSem(boolean descansoSem) {
		this.descansoSem = descansoSem;
	}
	public BigDecimal getEntrada() {
		return entrada;
	}
	public void setEntrada(BigDecimal entrada) {
		this.entrada = entrada;
	}
	public BigDecimal getSaida() {
		return saida;
	}
	public void setSaida(BigDecimal saida) {
		this.saida = saida;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codCargaHor = vo.asBigDecimal("CODCARGAHOR");
		this.diaSem = vo.asBigDecimal("DIASEM");
		this.turno = vo.asBigDecimal("TURNO");
		this.descansoSem = vo.asBoolean("DESCANSOSEM");
		this.entrada = vo.asBigDecimal("ENTRADA");
		this.saida = vo.asBigDecimal("SAIDA");
	}
	
	@Override
	public String toString() {
		return String.format("CargaHoraria [codCargaHor=%s, diaSem=%s, turno=%s, descansoSem=%s, entrada=%s, saida=%s]",
				codCargaHor, diaSem, turno, descansoSem, entrada, saida);
	}
}

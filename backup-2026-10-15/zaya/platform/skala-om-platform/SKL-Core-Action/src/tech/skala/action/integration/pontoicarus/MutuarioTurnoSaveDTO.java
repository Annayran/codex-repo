package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import tech.skala.core.commons.dd.DTO;

public class MutuarioTurnoSaveDTO implements DTO {
	private Timestamp							dataFim;
	private Timestamp							dataInicio;
	private BigDecimal							idMutuarioTurno;
	private MutuarioIdDTO						mutuario;
	private TurnoIdDTO							turno;
	private String								turnoDiaInicio;
	
	public Timestamp getDataFim() {
		return dataFim;
	}
	public void setDataFim(Timestamp dataFim) {
		this.dataFim = dataFim;
	}
	public Timestamp getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Timestamp dataInicio) {
		this.dataInicio = dataInicio;
	}
	public BigDecimal getIdMutuarioTurno() {
		return idMutuarioTurno;
	}
	public void setIdMutuarioTurno(BigDecimal idMutuarioTurno) {
		this.idMutuarioTurno = idMutuarioTurno;
	}
	public MutuarioIdDTO getMutuario() {
		return mutuario;
	}
	public void setMutuario(MutuarioIdDTO mutuario) {
		this.mutuario = mutuario;
	}
	public TurnoIdDTO getTurno() {
		return turno;
	}
	public void setTurno(TurnoIdDTO turno) {
		this.turno = turno;
	}
	public String getTurnoDiaInicio() {
		return turnoDiaInicio;
	}
	public void setTurnoDiaInicio(String turnoDiaInicio) {
		this.turnoDiaInicio = turnoDiaInicio;
	}
	
	@Override
	public String toString() {
		return String.format(
				"MutuarioTurnoSaveDTO [dataFim=%s, dataInicio=%s, idMutuarioTurno=%s, mutuario=%s, turno=%s, turnoDiaInicio=%s]",
				dataFim, dataInicio, idMutuarioTurno, mutuario, turno, turnoDiaInicio);
	}
	
}

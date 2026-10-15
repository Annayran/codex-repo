package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.util.LinkedList;

import tech.skala.core.commons.dd.DTO;

public class TurnoDiaDTO implements DTO {
	private String								folga;
	private BigDecimal							idTurnoDia;
	private String								jornadaFeriado;
	private String								limiteHE50;
	private String								tipoTurnoDia;
	private String								tipoTurnoDiaFmt;
	private LinkedList<TurnoPeriodoDTO>			turnoPeriodos;
	private LinkedList<TurnoPeriodoDTO>			turnoPeriodosOrdenados;
	private String								viradaTurno;
	
	public TurnoDiaDTO() {
		this.turnoPeriodos = new LinkedList<>();
		this.turnoPeriodosOrdenados = new LinkedList<>();
	}
	
	public String getFolga() {
		return folga;
	}
	public void setFolga(String folga) {
		this.folga = folga;
	}
	public BigDecimal getIdTurnoDia() {
		return idTurnoDia;
	}
	public void setIdTurnoDia(BigDecimal idTurnoDia) {
		this.idTurnoDia = idTurnoDia;
	}
	public String getJornadaFeriado() {
		return jornadaFeriado;
	}
	public void setJornadaFeriado(String jornadaFeriado) {
		this.jornadaFeriado = jornadaFeriado;
	}
	public String getLimiteHE50() {
		return limiteHE50;
	}
	public void setLimiteHE50(String limiteHE50) {
		this.limiteHE50 = limiteHE50;
	}
	public String getTipoTurnoDia() {
		return tipoTurnoDia;
	}
	public void setTipoTurnoDia(String tipoTurnoDia) {
		this.tipoTurnoDia = tipoTurnoDia;
	}
	public String getTipoTurnoDiaFmt() {
		return tipoTurnoDiaFmt;
	}
	public void setTipoTurnoDiaFmt(String tipoTurnoDiaFmt) {
		this.tipoTurnoDiaFmt = tipoTurnoDiaFmt;
	}
	public LinkedList<TurnoPeriodoDTO> getTurnoPeriodos() {
		return turnoPeriodos;
	}
	public void setTurnoPeriodos(LinkedList<TurnoPeriodoDTO> turnoPeriodos) {
		this.turnoPeriodos = turnoPeriodos;
	}
	public LinkedList<TurnoPeriodoDTO> getTurnoPeriodosOrdenados() {
		return turnoPeriodosOrdenados;
	}
	public void setTurnoPeriodosOrdenados(LinkedList<TurnoPeriodoDTO> turnoPeriodosOrdenados) {
		this.turnoPeriodosOrdenados = turnoPeriodosOrdenados;
	}
	public String getViradaTurno() {
		return viradaTurno;
	}
	public void setViradaTurno(String viradaTurno) {
		this.viradaTurno = viradaTurno;
	}
	
	@Override
	public String toString() {
		return String.format(
				"TurnoDiaDTO [folga=%s, idTurnoDia=%s, jornadaFeriado=%s, limiteHE50=%s, tipoTurnoDia=%s, tipoTurnoDiaFmt=%s, turnoPeriodos=%s, turnoPeriodosOrdenados=%s, viradaTurno=%s]",
				folga, idTurnoDia, jornadaFeriado, limiteHE50, tipoTurnoDia, tipoTurnoDiaFmt, turnoPeriodos,
				turnoPeriodosOrdenados, viradaTurno);
	}
	
}

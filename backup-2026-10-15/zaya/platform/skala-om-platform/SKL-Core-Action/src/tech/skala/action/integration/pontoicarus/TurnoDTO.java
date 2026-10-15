package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.LinkedList;

import tech.skala.core.commons.dd.DTO;

public class TurnoDTO implements DTO {
	private String								codigo;
	private String								descricao;
	private String								tipoTurno;
	private String								tipoTurnoFormatado;
	private String								aplicarToleranciaIntervalos;
	private String								contabilizarHEFeriados;
	private Timestamp							fimAdicionalNoturno;
	private String								fusoHorario;
	private BigDecimal							horaExtraDSR;
	private BigDecimal							horaExtraDSR150;
	private BigDecimal							horaExtraNormal;
	private Timestamp							horaPadraoDescontoDSR;
	private BigDecimal							id;
	private BigDecimal							idTurno;
	private String								ignoraFeriados;
	private String								inicioAdicionalNoturno;
	private String								intervaloFlexivel;
	private String								intervaloPreAssinalado;
	private String								jornadaFeriado;
	private String								jornadaFlexivel;
	private BigDecimal							qtdColunaPeriodo;
	private BigDecimal							tolerancia;
	private String								toleranciaPorBatida;
	private boolean								turnoCiclico;
	private LinkedList<TurnoDiaDTO>				turnoDias;
	
	public TurnoDTO() {
		this.turnoDias = new LinkedList<>();
	}
	public String getAplicarToleranciaIntervalos() {
		return aplicarToleranciaIntervalos;
	}
	public void setAplicarToleranciaIntervalos(String aplicarToleranciaIntervalos) {
		this.aplicarToleranciaIntervalos = aplicarToleranciaIntervalos;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getContabilizarHEFeriados() {
		return contabilizarHEFeriados;
	}
	public void setContabilizarHEFeriados(String contabilizarHEFeriados) {
		this.contabilizarHEFeriados = contabilizarHEFeriados;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Timestamp getFimAdicionalNoturno() {
		return fimAdicionalNoturno;
	}
	public void setFimAdicionalNoturno(Timestamp fimAdicionalNoturno) {
		this.fimAdicionalNoturno = fimAdicionalNoturno;
	}
	public String getFusoHorario() {
		return fusoHorario;
	}
	public void setFusoHorario(String fusoHorario) {
		this.fusoHorario = fusoHorario;
	}
	public BigDecimal getHoraExtraDSR() {
		return horaExtraDSR;
	}
	public void setHoraExtraDSR(BigDecimal horaExtraDSR) {
		this.horaExtraDSR = horaExtraDSR;
	}
	public BigDecimal getHoraExtraDSR150() {
		return horaExtraDSR150;
	}
	public void setHoraExtraDSR150(BigDecimal horaExtraDSR150) {
		this.horaExtraDSR150 = horaExtraDSR150;
	}
	public BigDecimal getHoraExtraNormal() {
		return horaExtraNormal;
	}
	public void setHoraExtraNormal(BigDecimal horaExtraNormal) {
		this.horaExtraNormal = horaExtraNormal;
	}
	public Timestamp getHoraPadraoDescontoDSR() {
		return horaPadraoDescontoDSR;
	}
	public void setHoraPadraoDescontoDSR(Timestamp horaPadraoDescontoDSR) {
		this.horaPadraoDescontoDSR = horaPadraoDescontoDSR;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public BigDecimal getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(BigDecimal idTurno) {
		this.idTurno = idTurno;
	}
	public String getIgnoraFeriados() {
		return ignoraFeriados;
	}
	public void setIgnoraFeriados(String ignoraFeriados) {
		this.ignoraFeriados = ignoraFeriados;
	}
	public String getInicioAdicionalNoturno() {
		return inicioAdicionalNoturno;
	}
	public void setInicioAdicionalNoturno(String inicioAdicionalNoturno) {
		this.inicioAdicionalNoturno = inicioAdicionalNoturno;
	}
	public String getIntervaloFlexivel() {
		return intervaloFlexivel;
	}
	public void setIntervaloFlexivel(String intervaloFlexivel) {
		this.intervaloFlexivel = intervaloFlexivel;
	}
	public String getIntervaloPreAssinalado() {
		return intervaloPreAssinalado;
	}
	public void setIntervaloPreAssinalado(String intervaloPreAssinalado) {
		this.intervaloPreAssinalado = intervaloPreAssinalado;
	}
	public String getJornadaFeriado() {
		return jornadaFeriado;
	}
	public void setJornadaFeriado(String jornadaFeriado) {
		this.jornadaFeriado = jornadaFeriado;
	}
	public String getJornadaFlexivel() {
		return jornadaFlexivel;
	}
	public void setJornadaFlexivel(String jornadaFlexivel) {
		this.jornadaFlexivel = jornadaFlexivel;
	}
	public BigDecimal getQtdColunaPeriodo() {
		return qtdColunaPeriodo;
	}
	public void setQtdColunaPeriodo(BigDecimal qtdColunaPeriodo) {
		this.qtdColunaPeriodo = qtdColunaPeriodo;
	}
	public String getTipoTurno() {
		return tipoTurno;
	}
	public void setTipoTurno(String tipoTurno) {
		this.tipoTurno = tipoTurno;
	}
	public String getTipoTurnoFormatado() {
		return tipoTurnoFormatado;
	}
	public void setTipoTurnoFormatado(String tipoTurnoFormatado) {
		this.tipoTurnoFormatado = tipoTurnoFormatado;
	}
	public BigDecimal getTolerancia() {
		return tolerancia;
	}
	public void setTolerancia(BigDecimal tolerancia) {
		this.tolerancia = tolerancia;
	}
	public String getToleranciaPorBatida() {
		return toleranciaPorBatida;
	}
	public void setToleranciaPorBatida(String toleranciaPorBatida) {
		this.toleranciaPorBatida = toleranciaPorBatida;
	}
	public boolean isTurnoCiclico() {
		return turnoCiclico;
	}
	public void setTurnoCiclico(boolean turnoCiclico) {
		this.turnoCiclico = turnoCiclico;
	}
	public LinkedList<TurnoDiaDTO> getTurnoDias() {
		return turnoDias;
	}
	public void setTurnoDias(LinkedList<TurnoDiaDTO> turnoDias) {
		this.turnoDias = turnoDias;
	}
	
	@Override
	public String toString() {
		return String.format(
				"TurnoDTO [aplicarToleranciaIntervalos=%s, codigo=%s, contabilizarHEFeriados=%s, descricao=%s, fimAdicionalNoturno=%s, fusoHorario=%s, horaExtraDSR=%s, horaExtraDSR150=%s, horaExtraNormal=%s, horaPadraoDescontoDSR=%s, id=%s, idTurno=%s, ignoraFeriados=%s, inicioAdicionalNoturno=%s, intervaloFlexivel=%s, intervaloPreAssinalado=%s, jornadaFeriado=%s, jornadaFlexivel=%s, qtdColunaPeriodo=%s, tipoTurno=%s, tipoTurnoFormatado=%s, tolerancia=%s, toleranciaPorBatida=%s, turnoCiclico=%s, turnoDias=%s]",
				aplicarToleranciaIntervalos, codigo, contabilizarHEFeriados, descricao, fimAdicionalNoturno,
				fusoHorario, horaExtraDSR, horaExtraDSR150, horaExtraNormal, horaPadraoDescontoDSR, id, idTurno,
				ignoraFeriados, inicioAdicionalNoturno, intervaloFlexivel, intervaloPreAssinalado, jornadaFeriado,
				jornadaFlexivel, qtdColunaPeriodo, tipoTurno, tipoTurnoFormatado, tolerancia, toleranciaPorBatida,
				turnoCiclico, turnoDias);
	}
	
}

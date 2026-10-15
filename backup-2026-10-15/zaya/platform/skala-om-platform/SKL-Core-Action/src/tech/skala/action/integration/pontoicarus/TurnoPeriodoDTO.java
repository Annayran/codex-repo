package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class TurnoPeriodoDTO implements DTO {
	private String								descricaoColuna;
	private String								hora;
	private BigDecimal							idTurnoPeriodo;
	private BigDecimal							ordenacao;
	private String								tipo;
	
	public String getDescricaoColuna() {
		return descricaoColuna;
	}
	public void setDescricaoColuna(String descricaoColuna) {
		this.descricaoColuna = descricaoColuna;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public BigDecimal getIdTurnoPeriodo() {
		return idTurnoPeriodo;
	}
	public void setIdTurnoPeriodo(BigDecimal idTurnoPeriodo) {
		this.idTurnoPeriodo = idTurnoPeriodo;
	}
	public BigDecimal getOrdenacao() {
		return ordenacao;
	}
	public void setOrdenacao(BigDecimal ordenacao) {
		this.ordenacao = ordenacao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return String.format("TurnoPeriodoDTO [descricaoColuna=%s, hora=%s, idTurnoPeriodo=%s, ordenacao=%s, tipo=%s]",
				descricaoColuna, hora, idTurnoPeriodo, ordenacao, tipo);
	}

}

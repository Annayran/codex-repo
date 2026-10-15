package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class PontoHorarioBatidaDTO implements DTO, Comparable<PontoHorarioBatidaDTO> {
	private BigDecimal							idPontoHorarioBatida;
	private String								horaRegistroFormatado;
	
	public BigDecimal getIdPontoHorarioBatida() {
		return idPontoHorarioBatida;
	}
	public void setIdPontoHorarioBatida(BigDecimal idPontoHorarioBatida) {
		this.idPontoHorarioBatida = idPontoHorarioBatida;
	}
	public String getHoraRegistroFormatado() {
		return horaRegistroFormatado;
	}
	public void setHoraRegistroFormatado(String horaRegistroFormatado) {
		this.horaRegistroFormatado = horaRegistroFormatado;
	}
	
	@Override
	public int compareTo(PontoHorarioBatidaDTO o) {
		return this.horaRegistroFormatado.compareTo(o.getHoraRegistroFormatado());
	}
	
	@Override
	public String toString() {
		return String.format("PontoHorarioBatidaDTO [idPontoHorarioBatida=%s, horaRegistroFormatado=%s]",
				idPontoHorarioBatida, horaRegistroFormatado);
	}

}

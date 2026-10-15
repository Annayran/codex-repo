package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class PerfilIdDTO implements DTO {
	private BigDecimal							idPerfil;

	public BigDecimal getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(BigDecimal idPerfil) {
		this.idPerfil = idPerfil;
	}

	@Override
	public String toString() {
		return String.format("PerfilIdDTO [idPerfil=%s]", idPerfil);
	}

}

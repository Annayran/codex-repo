package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class MutuarioIdDTO implements DTO {
	private BigDecimal							idMutuario;

	public BigDecimal getIdMutuario() {
		return idMutuario;
	}

	public void setIdMutuario(BigDecimal idMutuario) {
		this.idMutuario = idMutuario;
	}

	@Override
	public String toString() {
		return String.format("MutuarioIdDTO [idMutuario=%s]", idMutuario);
	}
	
}

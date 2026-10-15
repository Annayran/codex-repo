package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class MotivoAbonoPontoIdDTO implements DTO {
	private BigDecimal							idMotivoAbonoPonto;

	public BigDecimal getIdMotivoAbonoPonto() {
		return idMotivoAbonoPonto;
	}
	public void setIdMotivoAbonoPonto(BigDecimal idMotivoAbonoPonto) {
		this.idMotivoAbonoPonto = idMotivoAbonoPonto;
	}
	
	@Override
	public String toString() {
		return String.format("MotivoAbonoPontoIdDTO [idMotivoAbonoPonto=%s]", idMotivoAbonoPonto);
	}

}

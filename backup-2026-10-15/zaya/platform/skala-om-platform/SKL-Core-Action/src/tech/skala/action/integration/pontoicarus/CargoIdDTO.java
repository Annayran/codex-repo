package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class CargoIdDTO implements DTO {
	private BigDecimal							idCargo;

	public BigDecimal getIdCargo() {
		return idCargo;
	}
	public void setIdCargo(BigDecimal idCargo) {
		this.idCargo = idCargo;
	}

	@Override
	public String toString() {
		return String.format("CargoIdDTO [idCargo=%s]", idCargo);
	}

}

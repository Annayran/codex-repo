package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class CentroCustoIdDTO implements DTO {
	private BigDecimal							idCentroCusto;

	public BigDecimal getIdCentroCusto() {
		return idCentroCusto;
	}
	public void setIdCentroCusto(BigDecimal idCentroCusto) {
		this.idCentroCusto = idCentroCusto;
	}
	
	@Override
	public String toString() {
		return String.format("CentroCustoIdDTO [idCentroCusto=%s]", idCentroCusto);
	}
	
}

package tech.skala.modules.suprimentos.abastecimento;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class RequisicaoStatusDTO implements DTO{
	private BigDecimal							nuReq;
	private String 								status;
	
	public BigDecimal getNuReq() {
		return nuReq;
	}

	public void setNuReq(BigDecimal nuReq) {
		this.nuReq = nuReq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("RequisicaoStatusDTO [nuReq=%s, status=%s]", nuReq, status);
	}
}

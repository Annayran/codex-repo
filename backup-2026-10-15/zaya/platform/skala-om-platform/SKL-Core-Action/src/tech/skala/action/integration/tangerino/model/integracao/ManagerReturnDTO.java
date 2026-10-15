package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class ManagerReturnDTO {
	private BigDecimal 							id;

	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("jobRoleDTO [id=%s]", id);
	}
	
}

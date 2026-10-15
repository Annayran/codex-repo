package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class AdjustmentReasonDTO {
	private BigDecimal							id;
	private String								description;
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return String.format("adjustmentReasonDTO [id=%s, description=%s]", id, description);
	}
}

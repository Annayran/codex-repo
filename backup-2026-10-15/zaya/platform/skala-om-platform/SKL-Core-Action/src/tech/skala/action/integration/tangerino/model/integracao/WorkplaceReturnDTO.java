package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

public class WorkplaceReturnDTO {
	private boolean								active;
	private String								externalId;
	private BigDecimal							id;
	private String								name;
	private boolean								standard;
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isStandard() {
		return standard;
	}
	public void setStandard(boolean standard) {
		this.standard = standard;
	}
	@Override
	public String toString() {
		return String.format("WorkplaceReturnDTO [active=%s, externalId=%s, id=%s, name=%s, standard=%s]", active,
				externalId, id, name, standard);
	}
	
}

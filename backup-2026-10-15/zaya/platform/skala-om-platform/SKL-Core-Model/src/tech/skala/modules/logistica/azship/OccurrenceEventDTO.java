package tech.skala.modules.logistica.azship;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class OccurrenceEventDTO implements DTO {
	
	private BigDecimal    							id;
	private String    								createDate;
	private String    								agentUsername;
	private String    								agentType;
	private String    								observation;
	
	public BigDecimal getId() {
		return id;
	}
	
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getAgentUsername() {
		return agentUsername;
	}
	
	public void setAgentUsername(String agentUsername) {
		this.agentUsername = agentUsername;
	}
	
	public String getAgentType() {
		return agentType;
	}
	
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
	
	public String getObservation() {
		return observation;
	}
	
	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Override
	public String toString() {
		return String.format("OccurrenceEventDTO [id=%s, createDate=%s, agentUsername=%s, agentType=%s, observation=%s]",
				id, createDate, agentUsername, agentType, observation);
	}

}
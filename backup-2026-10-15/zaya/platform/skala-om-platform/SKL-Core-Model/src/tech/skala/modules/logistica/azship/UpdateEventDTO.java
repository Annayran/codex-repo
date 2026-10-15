package tech.skala.modules.logistica.azship;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import tech.skala.core.commons.dd.DTO;

public class UpdateEventDTO implements DTO {

	private String 								code;
	private List<BigDecimal>					orderIds;
	private Set<EventDTO>						events;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<BigDecimal> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<BigDecimal> orderIds) {
		this.orderIds = orderIds;
	}

	public Set<EventDTO> getEvents() {
		return events;
	}

	public void setEvents(Set<EventDTO> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return String.format("UpdateEventDTO [code=%s, orderIds=%s, events=%s]", code, orderIds, events);
	}

}

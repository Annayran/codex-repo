package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class OccurrenceDTO implements DTO {
	
	private BigDecimal   							id;
	private String   							orderCode;
	private String   							shippingCode;
	private BigDecimal   							occurrenceType;
	private String  								status;
	private Timestamp   							createDate;
	private Timestamp   							modifiedDate;
	private String   								destination;
	private List<OccurrenceEventDTO> 				event = new ArrayList<>();

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public BigDecimal getOccurrenceType() {
		return occurrenceType;
	}

	public void setOccurrenceType(BigDecimal occurrenceType) {
		this.occurrenceType = occurrenceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getModifiedDAte() {
		return modifiedDate;
	}

	public void setModifiedDAte(Timestamp modifiedDAte) {
		this.modifiedDate = modifiedDAte;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<OccurrenceEventDTO> getEvent() {
		return event;
	}

	public void setEvent(List<OccurrenceEventDTO> event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return String.format(
				"OccurrenceDTO [id=%s, orderCode=%s, shippingCode=%s, occurrenceType=%s, status=%s, createDate=%s, modifiedDate=%s, destination=%s, event=%s]",
				id, orderCode, shippingCode, occurrenceType, status, createDate, modifiedDate, destination, event);
	}
}
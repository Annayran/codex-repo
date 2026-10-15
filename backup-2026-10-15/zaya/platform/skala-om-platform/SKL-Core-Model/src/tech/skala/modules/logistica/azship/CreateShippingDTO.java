package tech.skala.modules.logistica.azship;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class CreateShippingDTO implements DTO {
	
	private String									code;
	private	String									transporterCnpj;
	private List<Timestamp>							collectDate = new ArrayList<>();
	private String 									observation;
	private List<BigDecimal>						orderIds = new ArrayList<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTransporterCnpj() {
		return transporterCnpj;
	}

	public void setTransporterCnpj(String transporterCnpj) {
		this.transporterCnpj = transporterCnpj;
	}

	public List<Timestamp> getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(List<Timestamp> collectDate) {
		this.collectDate = collectDate;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public List<BigDecimal> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<BigDecimal> orderIds) {
		this.orderIds = orderIds;
	}

	@Override
	public String toString() {
		return String.format(
				"CreateShippingDTO [code=%s, transporterCnpj=%s, collectDate=%s, observation=%s, orderIds=%s]", code,
				transporterCnpj, collectDate, observation, orderIds);
	}
	
}

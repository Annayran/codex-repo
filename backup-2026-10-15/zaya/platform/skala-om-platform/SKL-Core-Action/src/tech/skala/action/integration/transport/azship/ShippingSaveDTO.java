package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class ShippingSaveDTO implements DTO {
	
	private String   								code;
	private String[]   								targets;
	private BigDecimal   							freightPrice;
	private BigDecimal   							tollPrice;
	private BigDecimal   							feeDeliveryPrice;
	private String   								observation;
	private List<OrderDTO>   						orders = new ArrayList<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String[] getTargets() {
		return targets;
	}

	public void setTargets(String[] targets) {
		this.targets = targets;
	}

	public BigDecimal getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(BigDecimal freightPrice) {
		this.freightPrice = freightPrice;
	}

	public BigDecimal getTollPrice() {
		return tollPrice;
	}

	public void setTollPrice(BigDecimal tollPrice) {
		this.tollPrice = tollPrice;
	}

	public BigDecimal getFeeDeliveryPrice() {
		return feeDeliveryPrice;
	}

	public void setFeeDeliveryPrice(BigDecimal feeDeliveryPrice) {
		this.feeDeliveryPrice = feeDeliveryPrice;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return String.format(
				"ShippingSaveDTO [code=%s, targets=%s, freightPrice=%s, tollPrice=%s, feeDeliveryPrice=%s, observation=%s, orders=%s]",
				code, Arrays.toString(targets), freightPrice, tollPrice, feeDeliveryPrice, observation, orders);
	}
}
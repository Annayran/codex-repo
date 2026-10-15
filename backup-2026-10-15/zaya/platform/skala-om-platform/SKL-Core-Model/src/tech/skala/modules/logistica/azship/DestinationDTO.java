package tech.skala.modules.logistica.azship;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class DestinationDTO implements DTO {
	
	private String  								deliveryDate;
	private String  								productType;
	private String  								unitType;
	private BigDecimal  							weight;
	private BigDecimal  							cubage;
	private BigDecimal  							price;
	private PlaceDTO  								place;
	private String  								observation;
	private String  								orderCode;
	private String  								nfCode;
	private List<BigDecimal>						orderIds = new ArrayList<>();

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getCubage() {
		return cubage;
	}

	public void setCubage(BigDecimal cubage) {
		this.cubage = cubage;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public PlaceDTO getPlace() {
		return place;
	}

	public void setPlace(PlaceDTO placeDTO) {
		this.place = placeDTO;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getNfCode() {
		return nfCode;
	}

	public void setNfCode(String nfCode) {
		this.nfCode = nfCode;
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
				"DestinationDTO [deliveryDate=%s, productType=%s, unitType=%s, weight=%s, cubage=%s, price=%s, place=%s, observation=%s, orderCode=%s, nfCode=%s, orderIds=%s]",
				deliveryDate, productType, unitType, weight, cubage, price, place, observation, orderCode, nfCode,
				orderIds);
	}

}
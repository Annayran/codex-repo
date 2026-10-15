package tech.skala.modules.logistica.azship;

import java.math.BigDecimal;
import java.sql.Timestamp;

import tech.skala.core.commons.dd.DTO;

public class OriginDTO implements DTO {
	
	private Timestamp  								collectDate;
	private String  								productType;
	private String  								unitType;
	private BigDecimal  							weight;
	private BigDecimal  							cubage;
	private BigDecimal  							price;
	private PlaceDTO  								place;
	private String  								observation;

	public Timestamp getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Timestamp collectDate) {
		this.collectDate = collectDate;
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

	@Override
	public String toString() {
		return String.format(
				"OriginDTO [collectDate=%s, productType=%s, unitType=%s, weight=%s, cubage=%s, price=%s, place=%s, observation=%s]",
				collectDate, productType, unitType, weight, cubage, price, place, observation);
	}
}
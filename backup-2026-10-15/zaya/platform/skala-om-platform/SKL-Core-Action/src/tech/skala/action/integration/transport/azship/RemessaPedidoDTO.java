package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.util.Objects;

import tech.skala.core.commons.dd.DTO;

public class RemessaPedidoDTO implements DTO {
	
	private String								internalCode;
	private String								code;
	private String 								collectDocument;
	private BigDecimal 							collectAmount;
	private BigDecimal 							collectWeight;
	private String 								collectDate;
	private String 								deliveryDocument;
	private String 								deliveryName;
	private String 								deliveryStreet;
	private String 								deliveryCEP;
	private String 								deliveryDistrict;
	private String 								deliveryCity;
	private String 								deliveryUF;
	private BigDecimal 							deliveryLatitude;
	private BigDecimal 							deliveryLongitude;
	private String 								deliveryDate;
	private BigDecimal 							orderValue;
	private String								observation;
	private String 								treatment;
	
	public String getInternalCode() {
		return internalCode;
	}
	
	public void setInternalCode(String internalCode) {
		this.internalCode = internalCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCollectDocument() {
		return collectDocument;
	}

	public void setCollectDocument(String collectDocument) {
		this.collectDocument = collectDocument;
	}

	public BigDecimal getCollectAmount() {
		return collectAmount;
	}

	public void setCollectAmount(BigDecimal collectAmount) {
		this.collectAmount = collectAmount;
	}

	public BigDecimal getCollectWeight() {
		return collectWeight;
	}

	public void setCollectWeight(BigDecimal collectWeight) {
		this.collectWeight = collectWeight;
	}

	public String getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}

	public String getDeliveryDocument() {
		return deliveryDocument;
	}

	public void setDeliveryDocument(String deliveryDocument) {
		this.deliveryDocument = deliveryDocument;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryStreet() {
		return deliveryStreet;
	}

	public void setDeliveryStreet(String deliveryStreet) {
		this.deliveryStreet = deliveryStreet;
	}

	public String getDeliveryCEP() {
		return deliveryCEP;
	}

	public void setDeliveryCEP(String deliveryCEP) {
		this.deliveryCEP = deliveryCEP;
	}

	public String getDeliveryDistrict() {
		return deliveryDistrict;
	}

	public void setDeliveryDistrict(String deliveryDistrict) {
		this.deliveryDistrict = deliveryDistrict;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}

	public String getDeliveryUF() {
		return deliveryUF;
	}

	public void setDeliveryUF(String deliveryUF) {
		this.deliveryUF = deliveryUF;
	}

	public BigDecimal getDeliveryLatitude() {
		return deliveryLatitude;
	}

	public void setDeliveryLatitude(BigDecimal deliveryLatitude) {
		this.deliveryLatitude = deliveryLatitude;
	}

	public BigDecimal getDeliveryLongitude() {
		return deliveryLongitude;
	}

	public void setDeliveryLongitude(BigDecimal deliveryLongitude) {
		this.deliveryLongitude = deliveryLongitude;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public BigDecimal getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(BigDecimal orderValue) {
		this.orderValue = orderValue;
	}
	
	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, collectAmount, collectDate, collectDocument, collectWeight, deliveryCEP, deliveryCity,
				deliveryDate, deliveryDistrict, deliveryDocument, deliveryLatitude, deliveryLongitude, deliveryName,
				deliveryStreet, deliveryUF, internalCode, observation, orderValue, treatment);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RemessaPedidoDTO))
			return false;
		RemessaPedidoDTO other = (RemessaPedidoDTO) obj;
		return Objects.equals(code, other.code) && Objects.equals(collectAmount, other.collectAmount)
				&& Objects.equals(collectDate, other.collectDate)
				&& Objects.equals(collectDocument, other.collectDocument)
				&& Objects.equals(collectWeight, other.collectWeight) && Objects.equals(deliveryCEP, other.deliveryCEP)
				&& Objects.equals(deliveryCity, other.deliveryCity) && Objects.equals(deliveryDate, other.deliveryDate)
				&& Objects.equals(deliveryDistrict, other.deliveryDistrict)
				&& Objects.equals(deliveryDocument, other.deliveryDocument)
				&& Objects.equals(deliveryLatitude, other.deliveryLatitude)
				&& Objects.equals(deliveryLongitude, other.deliveryLongitude)
				&& Objects.equals(deliveryName, other.deliveryName)
				&& Objects.equals(deliveryStreet, other.deliveryStreet) && Objects.equals(deliveryUF, other.deliveryUF)
				&& Objects.equals(internalCode, other.internalCode) && Objects.equals(observation, other.observation)
				&& Objects.equals(orderValue, other.orderValue) && Objects.equals(treatment, other.treatment);
	}

	@Override
	public String toString() {
		return String.format(
				"RemessaPedidoDTO [internalCode=%s, code=%s, collectDocument=%s, collectAmount=%s, collectWeight=%s, collectDate=%s, deliveryDocument=%s, deliveryName=%s, deliveryStreet=%s, deliveryCEP=%s, deliveryDistrict=%s, deliveryCity=%s, deliveryUF=%s, deliveryLatitude=%s, deliveryLongitude=%s, deliveryDate=%s, orderValue=%s, observation=%s, treatment=%s]",
				internalCode, code, collectDocument, collectAmount, collectWeight, collectDate, deliveryDocument,
				deliveryName, deliveryStreet, deliveryCEP, deliveryDistrict, deliveryCity, deliveryUF, deliveryLatitude,
				deliveryLongitude, deliveryDate, orderValue, observation, treatment);
	}

}

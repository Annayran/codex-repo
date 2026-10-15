package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class ShippingDTO implements DTO {
	
	private String   								code;
	private BigDecimal   							weight;
	private BigDecimal   							cubage;
	private BigDecimal   							shippingPrice;
	private BigDecimal   							freightPrice;
	private BigDecimal   							tollPrice;
	private BigDecimal   							feeDeliveryPrice;
	private String   								status;
	private List<OriginDTO>   						origins = new ArrayList<>();
	private List<DestinationDTO>   					destinations = new ArrayList<>();
	private List<VehicleDTO> 						vehicles = new ArrayList<>();
	private DriverDTO   							driver;
	private TransporterDTO   						transporter;
	private List<EventDTO> 							events = new ArrayList<>();
	private String   								observation;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public BigDecimal getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(BigDecimal shippingPrice) {
		this.shippingPrice = shippingPrice;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OriginDTO> getOrigins() {
		return origins;
	}

	public void setOrigins(List<OriginDTO> origins) {
		this.origins = origins;
	}

	public List<DestinationDTO> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<DestinationDTO> destinations) {
		this.destinations = destinations;
	}

	public List<VehicleDTO> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<VehicleDTO> vehicles) {
		this.vehicles = vehicles;
	}

	public DriverDTO getDriver() {
		return driver;
	}

	public void setDriver(DriverDTO driver) {
		this.driver = driver;
	}

	public TransporterDTO getTransporter() {
		return transporter;
	}

	public void setTransporter(TransporterDTO transporter) {
		this.transporter = transporter;
	}

	public List<EventDTO> getEvents() {
		return events;
	}

	public void setEvents(List<EventDTO> events) {
		this.events = events;
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
				"ShippingDTO [code=%s, weight=%s, cubage=%s, shippingPrice=%s, freightPrice=%s, tollPrice=%s, feeDeliveryPrice=%s, status=%s, origins=%s, destinations=%s, vehicles=%s, driver=%s, transporter=%s, events=%s, observation=%s]",
				code, weight, cubage, shippingPrice, freightPrice, tollPrice, feeDeliveryPrice, status, origins,
				destinations, vehicles, driver, transporter, events, observation);
	}
}
package tech.skala.modules.logistica.azship;

import tech.skala.core.commons.dd.DTO;

public class VehicleDTO implements DTO {
	
	private String     								type;
	private String     								trailer;
	private String     								plate;
	private String     								renavan;
	private String     								uf;
	private String     								city;
	private String     								axlesAmount;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTrailer() {
		return trailer;
	}
	
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	
	public String getPlate() {
		return plate;
	}
	
	public void setPlate(String plate) {
		this.plate = plate;
	}
	
	public String getRenavan() {
		return renavan;
	}
	
	public void setRenavan(String renavan) {
		this.renavan = renavan;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getAxlesAmount() {
		return axlesAmount;
	}
	
	public void setAxlesAmount(String axlesAmount) {
		this.axlesAmount = axlesAmount;
	}
	
	@Override
	public String toString() {
		return String.format("VehicleDTO [type=%s, trailer=%s, plate=%s, renavan=%s, uf=%s, city=%s, axlesAmount=%s]",
				type, trailer, plate, renavan, uf, city, axlesAmount);
	}
}
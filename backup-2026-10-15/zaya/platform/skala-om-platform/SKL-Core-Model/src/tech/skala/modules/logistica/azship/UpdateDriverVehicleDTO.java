package tech.skala.modules.logistica.azship;

import java.math.BigDecimal;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class UpdateDriverVehicleDTO implements DTO {

	private String 								code;
	private List<BigDecimal>					orderIds;
	private String    							cpf;
	private String								plate;
	
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

	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	@Override
	public String toString() {
		return String.format("UpdateDriverVehicleDTO [code=%s, orderIds=%s, cpf=%s, plate=%s]", code, orderIds, cpf,
				plate);
	}
	
}

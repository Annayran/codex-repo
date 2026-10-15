package tech.skala.modules.logistica.azship;

import tech.skala.core.commons.dd.DTO;

public class DriverDTO implements DTO {
	
	private String    								name;
	private String    								phone;
	private String    								cpf;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return String.format("DriverDTO [name=%s, phone=%s, cpf=%s]", name, phone, cpf);
	}
}
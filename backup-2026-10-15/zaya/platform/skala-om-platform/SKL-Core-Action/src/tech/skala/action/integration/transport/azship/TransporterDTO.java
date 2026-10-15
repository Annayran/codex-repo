package tech.skala.action.integration.transport.azship;

import tech.skala.core.commons.dd.DTO;

public class TransporterDTO implements DTO {
	
	private String   								name;
	private String   								phone;
	private String   								cnpj;

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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return String.format("TransporterDTO [name=%s, phone=%s, cnpj=%s]", name, phone, cnpj);
	}
}
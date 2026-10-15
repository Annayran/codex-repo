package tech.skala.action.integration.transport.azship;

import tech.skala.core.commons.dd.DTO;

public class PlaceDTO implements DTO {
	
	private String   								name;
	private String   								document;
	private String   								address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return String.format("PlaceDTO [name=%s, document=%s, address=%s]", name, document, address);
	}
}
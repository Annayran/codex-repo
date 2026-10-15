package tech.skala.action.modules.centrais;

public enum StatusNotaEnum {

	ATENDIMENTO("A"),
	LIBERADA("L"),
	PENDENTE("P");
	
	private String value;
	
	StatusNotaEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

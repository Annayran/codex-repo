package tech.skala.action.integration.pontoicarus;

public enum SexoEnum {
	
	F("FEMININO"),
	M("MASCULINO");
	
	private String value;
	
	SexoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}

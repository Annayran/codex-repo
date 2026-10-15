package tech.skala.action.beans.demanda;

public enum DemandaTipoOrigemEnum {
	AGENDADOR("A"),
	MANUAL("M");
	
	private final String value;

	DemandaTipoOrigemEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}

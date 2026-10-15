package tech.skala.action.beans.meta;

public enum UsaReferenciaEnum {
	ATUAL("A"),
	PROXIMA("P");
	
	private final String value;

	UsaReferenciaEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

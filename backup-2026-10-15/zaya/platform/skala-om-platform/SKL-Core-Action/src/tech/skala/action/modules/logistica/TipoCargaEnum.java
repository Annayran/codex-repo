package tech.skala.action.modules.logistica;

public enum TipoCargaEnum {
	FRACIONADA("R"),
	FECHADA("F");

	private String value;

	private TipoCargaEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}

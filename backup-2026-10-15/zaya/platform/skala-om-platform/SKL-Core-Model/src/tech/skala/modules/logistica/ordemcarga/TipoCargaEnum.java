package tech.skala.modules.logistica.ordemcarga;

public enum TipoCargaEnum {
	FECHADA("F"),
	FRACIONADA("R");
	
	private String value;

	private TipoCargaEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}

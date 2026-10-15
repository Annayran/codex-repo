package tech.skala.modules.logistica.ordemcarga;

public enum SituacaoCargaEnum {

	ABERTO("A"),
	FECHADO("F");
	
	private String value;

	private SituacaoCargaEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}

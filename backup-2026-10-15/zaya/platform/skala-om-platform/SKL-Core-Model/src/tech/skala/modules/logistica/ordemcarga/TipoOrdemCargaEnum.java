package tech.skala.modules.logistica.ordemcarga;

public enum TipoOrdemCargaEnum {
	AMBOS("A"), 
	SAIDA("S"), 
	ENTRADA("E");

	private String value;

	private TipoOrdemCargaEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

package tech.skala.modules.logistica.ordemcarga;

public enum TipoEmbalagensOrdemCargaEnum {

	PALETIZADO("1"),
	PALETIZADO_SKU("2"),
	ESTIVADO("3"),
	PALETIZADO_ESTIVADO("4");
	
	private String value;

	private TipoEmbalagensOrdemCargaEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}

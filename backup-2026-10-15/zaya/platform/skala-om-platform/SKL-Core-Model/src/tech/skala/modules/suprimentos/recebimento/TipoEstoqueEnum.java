package tech.skala.modules.suprimentos.recebimento;

public enum TipoEstoqueEnum {
	PROPRIO("P"),
	TERCEIROS("T");
	
	private final String value;

	TipoEstoqueEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

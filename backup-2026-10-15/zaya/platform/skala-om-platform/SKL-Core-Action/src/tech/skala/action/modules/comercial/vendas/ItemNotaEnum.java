package tech.skala.action.modules.comercial.vendas;


public enum ItemNotaEnum {
	PENDENTE("S"),
	NAO_PENDENTE("N");
	
	private final String value;

	ItemNotaEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

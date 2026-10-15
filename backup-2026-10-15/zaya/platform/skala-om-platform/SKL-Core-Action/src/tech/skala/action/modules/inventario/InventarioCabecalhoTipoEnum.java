package tech.skala.action.modules.inventario;

public enum InventarioCabecalhoTipoEnum {
	GERAL("G"),
	ROTATIVO("R");
	
	private final String value;

	InventarioCabecalhoTipoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

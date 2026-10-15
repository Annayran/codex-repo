package tech.skala.action.modules.inventario;

public enum InventarioAjusteTipoAjusteEnum {
	ENTRADA("E"),
	SAIDA("S"),
	SEM_AJUSTE("N");
	
	private final String value;

	InventarioAjusteTipoAjusteEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}

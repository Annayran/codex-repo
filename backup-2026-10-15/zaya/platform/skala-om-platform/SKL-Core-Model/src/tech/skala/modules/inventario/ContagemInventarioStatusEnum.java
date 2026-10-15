package tech.skala.modules.inventario;

public enum ContagemInventarioStatusEnum {
	PENDENTE("P"),
	SINCRONIZADA("S"),
	FINALIZADA("F"),
	CONTAGEM_PROCESSADA("C");
	
	private final String value;

	ContagemInventarioStatusEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

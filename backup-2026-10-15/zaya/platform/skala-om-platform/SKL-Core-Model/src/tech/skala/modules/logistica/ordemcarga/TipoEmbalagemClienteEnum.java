package tech.skala.modules.logistica.ordemcarga;

public enum TipoEmbalagemClienteEnum {

	PALETIZADA("P"), 
	ESTIVADA("E"), 
	PALETIZADO_POR_ITEM("PI"), 
	INDIFERENTE("I");
	
	private String value;

	private TipoEmbalagemClienteEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}

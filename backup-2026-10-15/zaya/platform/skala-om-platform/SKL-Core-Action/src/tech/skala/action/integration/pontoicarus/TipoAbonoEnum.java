package tech.skala.action.integration.pontoicarus;

public enum TipoAbonoEnum {

	O("ABONO"),
	F("FALTA_DESCONTO"),
	S("SUSPENSAO");
	
	private String value;
	
	TipoAbonoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}

package tech.skala.modules.logistica.ordemcarga;

public enum StatusCargaEnum {
	
	EXPEDIDO("EX"),
	LIBERADO_PARA_EMBARQUE("AG"),
	LIBERADO_PARA_CONTRATACAO("FT"),
	PROGRAMADO_EXPEDICAO("CA"),
	MONTANDO_OC("SP"),
	SEPARANDO_CONFERINDO("CO");
	
	private String value;

	private StatusCargaEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

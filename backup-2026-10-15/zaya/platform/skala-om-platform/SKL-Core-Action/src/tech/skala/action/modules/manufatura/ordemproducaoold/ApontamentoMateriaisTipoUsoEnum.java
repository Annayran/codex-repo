package tech.skala.action.modules.manufatura.ordemproducaoold;

public enum ApontamentoMateriaisTipoUsoEnum {
	CONSUMIDO("C"),
	REAPROVEITADO("P");
	
	private final String value;

	ApontamentoMateriaisTipoUsoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

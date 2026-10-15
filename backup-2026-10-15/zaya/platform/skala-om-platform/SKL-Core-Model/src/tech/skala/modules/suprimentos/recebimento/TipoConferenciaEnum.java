package tech.skala.modules.suprimentos.recebimento;

public enum TipoConferenciaEnum {

	RECEBIMENTO("R");

	private String value;

	TipoConferenciaEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

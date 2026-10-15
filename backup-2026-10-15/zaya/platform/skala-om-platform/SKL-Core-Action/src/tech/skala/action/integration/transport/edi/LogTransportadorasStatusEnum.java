package tech.skala.action.integration.transport.edi;

public enum LogTransportadorasStatusEnum {

	PROCESSADO("P"),
	ERRO("E"),
	PROCESSADO_PARCIALMENTE("D"),
	PROCESSADO_ARQUIVO_NAO_MOVIDO("A");
	
	private String value;
	
	LogTransportadorasStatusEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

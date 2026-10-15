package tech.skala.action.integration.transport.ocorrencianaentrega;

public enum OcorrenciaOrigemEnum {

	MANUAL("M"),
	INTEGRACAO("I"),
	CALL_CENTER("C");
	
	private String value;
	
	private OcorrenciaOrigemEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}

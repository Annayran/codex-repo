package tech.skala.action.modules.abastecimento;

public enum StatusRequisicaoEnum {
	CRIADO("C"),
	LIBERADO("L"),
	EM_ATENDIMENTO("E"),
	ATENDIDO("A"),
	FINALIZADO("F");

	private String value;

	private StatusRequisicaoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}

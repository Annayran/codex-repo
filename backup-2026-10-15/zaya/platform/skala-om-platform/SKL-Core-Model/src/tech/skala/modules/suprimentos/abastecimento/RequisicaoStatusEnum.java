package tech.skala.modules.suprimentos.abastecimento;

public enum RequisicaoStatusEnum {

	CRIADO("C"),
	LIBERADO("L"),
	EM_ATENDIMENTO("E"),
	ATENDIDO("A"),
	FINALIZADO("F");

	private String value;

	RequisicaoStatusEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}

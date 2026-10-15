package tech.skala.modules.suprimentos.abastecimento;

public enum ProdutoRequisicaoStatusEnum {

	PENDENTE("P"),
	EM_ATENDIMENTO("E"),
	ATENDIDO("A"),
	CONFERIDO("C");
	
	private String value;

	ProdutoRequisicaoStatusEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

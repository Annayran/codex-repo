package tech.skala.modules.manufatura.apontamento;

public enum ApontamentoProducaoSituacaoEnum {
	PENDENTE("P"),
	CONFIRMADO("C");
	
	private final String value;

	ApontamentoProducaoSituacaoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
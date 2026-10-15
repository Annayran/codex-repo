package tech.skala.action.modules.abastecimento;

public enum TipoRequisicaoEnum {
	ALMOX_QUIMICO_PESAGEM("AQ"),
	ALMOX_ROTULOS_ROTULOS("AR"),
	ALMOX_EMBALAGEM_EMBALAGEM("AE");

	private String value;

	private TipoRequisicaoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

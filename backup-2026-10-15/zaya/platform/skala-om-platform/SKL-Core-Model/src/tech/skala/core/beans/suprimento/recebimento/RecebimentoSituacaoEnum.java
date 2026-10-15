package tech.skala.core.beans.suprimento.recebimento;

public enum RecebimentoSituacaoEnum {
	AGUARDANDO_CONFERENCIA("0"),
	EM_PROCESSO_CONFERENCIA("1"),
    CONFERENCIA_DIVERGENCIA("2"),
    CONCLUIDO("6");

	private final String value;

	private RecebimentoSituacaoEnum(String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}
}


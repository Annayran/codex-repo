package tech.skala.core.beans.suprimento.recebimento;

public enum ConferenciaSituacaoEnum {
	AGUARDANDO_CONFERENCIA("0"),	
	EM_ANDAMENTO("A"),
	FINALIZADA_DIVERGENTE("D"),
	FINALIZADA_OK("F"),
	AGUARDANDO_FINALIZACAO("Z");
	
	private final String value;

	ConferenciaSituacaoEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}


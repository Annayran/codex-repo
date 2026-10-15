package tech.skala.modules.manufatura.atividade;

public enum InstanciaAtividadeSituacaoEnum {
	AGUARDANDO_ACEITE("G"),
	ACEITA("A"),
	INICIADA("I"),
	FINALIZADA("F"),
	PARADA("P");
	
	private final String value;

	private InstanciaAtividadeSituacaoEnum(String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}

}

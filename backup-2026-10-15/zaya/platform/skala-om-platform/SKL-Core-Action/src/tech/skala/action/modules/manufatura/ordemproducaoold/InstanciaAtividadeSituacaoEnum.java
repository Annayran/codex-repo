package tech.skala.action.modules.manufatura.ordemproducaoold;

public enum InstanciaAtividadeSituacaoEnum {
	AGUARDANDO_ACEITE("G"),
	ACEITA("A"),
	INICIADA("I"),
	FINALIZADA("F");
	
	private final String value;

	private InstanciaAtividadeSituacaoEnum(String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}

}

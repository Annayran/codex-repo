package tech.skala.modules.manufatura.atividade;

public enum TipoParadaEnum {
	PARADA("P"),
	NORMAL("N");
	
	private final String value;

	private TipoParadaEnum(String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}

}

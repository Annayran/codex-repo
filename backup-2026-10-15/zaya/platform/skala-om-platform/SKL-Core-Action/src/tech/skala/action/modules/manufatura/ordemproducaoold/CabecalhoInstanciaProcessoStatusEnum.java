package tech.skala.action.modules.manufatura.ordemproducaoold;

public enum CabecalhoInstanciaProcessoStatusEnum {
	CRIADO("R"),
	EM_ANDAMENTO("A"),
	FINALIZADO("F"),
	CANCELADO("C");
	
	private final String value;

	private CabecalhoInstanciaProcessoStatusEnum(String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}

}

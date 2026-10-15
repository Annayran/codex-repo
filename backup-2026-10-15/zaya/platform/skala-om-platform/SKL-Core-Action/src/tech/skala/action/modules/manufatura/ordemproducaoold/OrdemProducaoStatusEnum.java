package tech.skala.action.modules.manufatura.ordemproducaoold;

/**
 * 
 * @author cassiomenezes
 * 
 */
public enum OrdemProducaoStatusEnum {
	CRIADO("R"),
	EM_ANDAMENTO("A"),
	FINALIZADO("F");
	
	private final String value;

	private OrdemProducaoStatusEnum(String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}

}

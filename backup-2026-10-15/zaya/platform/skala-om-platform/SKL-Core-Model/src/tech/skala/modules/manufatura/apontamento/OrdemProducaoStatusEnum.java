package tech.skala.modules.manufatura.apontamento;

/**
 * 
 * @author cassiomenezes
 * 
 */
public enum OrdemProducaoStatusEnum {
	CRIADO("R"),
	EM_ANDAMENTO("A"),
	FINALIZADO("F"),
	CANCELADO("C"),
	SUSPENSO("S");
	
	private final String value;

	private OrdemProducaoStatusEnum(String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}

}

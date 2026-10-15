package tech.skala.action.modules.manufatura.pcp.programacaosimplificada;

public enum ProgramacaoProducaoSimplificadaStatusEnum {
	EM_ELABORACAO("E"),
	PROGRAMADA("P"),
	INICIADA("I"),
	FINALIZADA("F"),
	CANCELADA("C");
	
	private final String value;

	private ProgramacaoProducaoSimplificadaStatusEnum(String value) {
		this.value = value;
	}

	public String getValue(){
		return value;
	}

}

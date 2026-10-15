package tech.skala.core.beans.produto;

public enum TipoControleEstoqueEnum {
	SERIE("E"),	
	GRADE("G"),	
	LIVRE("I"),	
	DATA_DA_VALIDADE("V"),	
	SEM_CONTROLE_ADICIONAL("N"),	
	PARCEIRO("P"),	
	LISTA("S"),	
	NUMERO_DO_LOTE("L");	
	
	private String value;
	
	
	public String getValue() {
		return value;
	}


	TipoControleEstoqueEnum(String value) {
		this.value = value;
	}
}

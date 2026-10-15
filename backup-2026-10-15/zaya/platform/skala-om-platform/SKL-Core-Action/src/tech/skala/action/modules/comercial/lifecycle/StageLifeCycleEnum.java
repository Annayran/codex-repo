package tech.skala.action.modules.comercial.lifecycle;

public enum StageLifeCycleEnum {

	PEDIDO_VENDAS("1"),
	ORDEM_EXPEDICAO_ORDEM_RESERVA_AGRUPA("2"),
	ORDEM_RESERVA("3"),
	NOTA_FISCAL("4"),
	ENTREGA_REALIZADA("5");
	
	private String value;
	
	private StageLifeCycleEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}

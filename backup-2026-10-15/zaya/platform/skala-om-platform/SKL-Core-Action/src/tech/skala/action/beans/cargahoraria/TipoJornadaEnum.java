package tech.skala.action.beans.cargahoraria;

import java.math.BigDecimal;

public enum TipoJornadaEnum {
	
	JORNADA_12_36(BigDecimal.valueOf(2)),
	DIA_FIXO_FOLGA_VARIAVEL(BigDecimal.valueOf(3)),
	DIA_FIXO_FOLGA_FIXA_DOM(BigDecimal.valueOf(4)),
	DIA_FIXO_FOLGA_FIXA_EXC_DOM(BigDecimal.valueOf(5)),
	DIA_FIXO_FOLGA_FIXA_OUTRO(BigDecimal.valueOf(6)),
	ININTERRUPTO_REVEZAMENTO(BigDecimal.valueOf(7)),
	DEMAIS_TIPOS(BigDecimal.valueOf(9));

	private final BigDecimal value;
	
	TipoJornadaEnum(BigDecimal bigDecimal) {
		this.value = bigDecimal;
	}

	public BigDecimal getValue() {
		return value;
	}

}

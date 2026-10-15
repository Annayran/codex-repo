package tech.skala.modules.suprimentos.abastecimento;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class RequisicaoSequenciaDTO implements DTO {
	private BigDecimal						nuReq;
	private BigDecimal						sequencia;
	
	public BigDecimal getNuReq() {
		return nuReq;
	}

	public void setNuReq(BigDecimal nuReq) {
		this.nuReq = nuReq;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	@Override
	public String toString() {
		return String.format("RequisicaoSequenciaDTO [nuReq=%s, sequencia=%s]", nuReq, sequencia);
	}
}
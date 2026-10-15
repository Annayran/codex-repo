package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class RegimeContratacaoIdDTO implements DTO {
	private BigDecimal							idRegimeContratacao;

	public BigDecimal getIdRegimeContratacao() {
		return idRegimeContratacao;
	}
	public void setIdRegimeContratacao(BigDecimal idRegimeContratacao) {
		this.idRegimeContratacao = idRegimeContratacao;
	}
	
	@Override
	public String toString() {
		return String.format("RegimeContratacaoIdDTO [idRegimeContratacao=%s]", idRegimeContratacao);
	}
	
}

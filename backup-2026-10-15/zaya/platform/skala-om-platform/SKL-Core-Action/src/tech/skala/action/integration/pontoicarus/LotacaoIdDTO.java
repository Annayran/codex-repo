package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class LotacaoIdDTO implements DTO {
	private BigDecimal							idLotacao;

	public BigDecimal getIdLotacao() {
		return idLotacao;
	}
	public void setIdLotacao(BigDecimal idLotacao) {
		this.idLotacao = idLotacao;
	}

	@Override
	public String toString() {
		return String.format("LotacaoIdDTO [idLotacao=%s]", idLotacao);
	}
	
}

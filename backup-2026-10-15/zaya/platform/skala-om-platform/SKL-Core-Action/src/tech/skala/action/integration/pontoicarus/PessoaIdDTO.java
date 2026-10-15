package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class PessoaIdDTO implements DTO {
	private BigDecimal							idPessoa;

	public BigDecimal getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(BigDecimal idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	@Override
	public String toString() {
		return String.format("PessoaIdDTO [idPessoa=%s]", idPessoa);
	}

}

package tech.skala.modules.suprimentos.abastecimento;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class ProdutoRequisicaoStatusDTO implements DTO{
	private BigDecimal							nuReq;
	private BigDecimal 							sequencia;
	private String 								status;
	private BigDecimal							codUsuAtd;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getCodUsuAtd() {
		return codUsuAtd;
	}

	public void setCodUsuAtd(BigDecimal codUsuAtd) {
		this.codUsuAtd = codUsuAtd;
	}

	@Override
	public String toString() {
		return String.format("ProdutoRequisicaoStatusDTO [nuReq=%s, sequencia=%s, status=%s, codUsuAtd=%s]", nuReq,
				sequencia, status, codUsuAtd);
	}
}

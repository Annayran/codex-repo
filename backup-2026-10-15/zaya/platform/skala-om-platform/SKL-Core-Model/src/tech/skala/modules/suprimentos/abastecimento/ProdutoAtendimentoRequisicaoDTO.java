package tech.skala.modules.suprimentos.abastecimento;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class ProdutoAtendimentoRequisicaoDTO implements DTO{
	private BigDecimal						nuReq;
	private BigDecimal						sequencia;
	private String							controle;
	private BigDecimal						qtdAtendida;
	private BigDecimal						qtdVol;

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

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public BigDecimal getQtdAtendida() {
		return qtdAtendida;
	}

	public void setQtdAtendida(BigDecimal qtdAtendida) {
		this.qtdAtendida = qtdAtendida;
	}

	public BigDecimal getQtdVol() {
		return qtdVol;
	}

	public void setQtdVol(BigDecimal qtdVol) {
		this.qtdVol = qtdVol;
	}

	@Override
	public String toString() {
		return String.format(
				"ProdutoAtendimentoRequisicaoDTO [nuReq=%s, sequencia=%s, controle=%s, qtdAtendida=%s, qtdVol=%s]",
				nuReq, sequencia, controle, qtdAtendida, qtdVol);
	}
}

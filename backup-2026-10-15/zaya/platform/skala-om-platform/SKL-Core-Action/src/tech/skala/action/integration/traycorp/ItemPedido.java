package tech.skala.action.integration.traycorp;

import java.math.BigDecimal;

public class ItemPedido {
	private BigDecimal			codProd;
	private String				referencia;
	private BigDecimal			qtdNeg;
	private String				obs;
	private String				instrucao;
	
	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public BigDecimal getQtdNeg() {
		return qtdNeg;
	}

	public void setQtdNeg(BigDecimal qtdNeg) {
		this.qtdNeg = qtdNeg;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getInstrucao() {
		return instrucao;
	}

	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
	}

	@Override
	public String toString() {
		return String.format("ItemPedido [codProd=%s, referencia=%s, qtdNeg=%s, obs=%s, instrucao=%s]", codProd,
				referencia, qtdNeg, obs, instrucao);
	}
}
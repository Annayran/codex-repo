package tech.skala.action.integration.traycorp;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayItemCentroDistribuicao implements EntityBean {
	private long							centroDistribuicaoId;
	private long							quantidade;
	private long							situacaoProdutoId;
	private double							valorFreteEmpresa;
	private double							valorFreteCliente;

	public long getCentroDistribuicaoId() {
		return centroDistribuicaoId;
	}

	public void setCentroDistribuicaoId(long centroDistribuicaoId) {
		this.centroDistribuicaoId = centroDistribuicaoId;
	}

	public long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}

	public long getSituacaoProdutoId() {
		return situacaoProdutoId;
	}

	public void setSituacaoProdutoId(long situacaoProdutoId) {
		this.situacaoProdutoId = situacaoProdutoId;
	}

	public double getValorFreteEmpresa() {
		return valorFreteEmpresa;
	}

	public void setValorFreteEmpresa(double valorFreteEmpresa) {
		this.valorFreteEmpresa = valorFreteEmpresa;
	}

	public double getValorFreteCliente() {
		return valorFreteCliente;
	}

	public void setValorFreteCliente(double valorFreteCliente) {
		this.valorFreteCliente = valorFreteCliente;
	}

	@Override
	public String toString() {
		return String.format(
				"TrayItemCentroDistribuicao [centroDistribuicaoId=%s, quantidade=%s, situacaoProdutoId=%s, valorFreteEmpresa=%s, valorFreteCliente=%s]",
				centroDistribuicaoId, quantidade, situacaoProdutoId, valorFreteEmpresa, valorFreteCliente);
	}

}

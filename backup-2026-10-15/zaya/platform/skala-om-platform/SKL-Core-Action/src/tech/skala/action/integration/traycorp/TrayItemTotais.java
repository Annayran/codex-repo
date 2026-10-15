package tech.skala.action.integration.traycorp;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayItemTotais implements EntityBean {
	private double							precoCusto;
	private double							precoVenda;
	private double							precoPor;
	private double							desconto;

	public double getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(double precoCusto) {
		this.precoCusto = precoCusto;
	}

	public double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public double getPrecoPor() {
		return precoPor;
	}

	public void setPrecoPor(double precoPor) {
		this.precoPor = precoPor;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	@Override
	public String toString() {
		return String.format("TrayItemTotais [precoCusto=%s, precoVenda=%s, precoPor=%s, desconto=%s]", precoCusto,
				precoVenda, precoPor, desconto);
	}

}

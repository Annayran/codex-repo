package tech.skala.action.integration.traycorp;

import java.util.ArrayList;
import java.util.Collection;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayPagamento implements EntityBean {
	private long							formaPagamentoId;
	private long							numeroParcelas;
	private double							valorParcela;
	private double							valorDesconto;
	private double							valorJuros;
	private double							valorTotal;
	private TrayPagamentoBoleto				boleto;
	private Collection<TrayPagamentoCartao>	cartaoCredito;
	private Collection<TrayPagamentoStatus>	pagamentoStatus = new ArrayList<>();
	private Collection<TrayCorpInformacoesAdicionais>	informacoesAdicionais = new ArrayList<>();

	public long getFormaPagamentoId() {
		return formaPagamentoId;
	}

	public void setFormaPagamentoId(long formaPagamentoId) {
		this.formaPagamentoId = formaPagamentoId;
	}

	public long getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(long numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(double valorParcela) {
		this.valorParcela = valorParcela;
	}

	public double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public double getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(double valorJuros) {
		this.valorJuros = valorJuros;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public TrayPagamentoBoleto getBoleto() {
		return boleto;
	}

	public void setBoleto(TrayPagamentoBoleto boleto) {
		this.boleto = boleto;
	}

	public Collection<TrayPagamentoCartao> getCartaoCredito() {
		return cartaoCredito;
	}

	public void setCartaoCredito(Collection<TrayPagamentoCartao> cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}

	public Collection<TrayPagamentoStatus> getPagamentoStatus() {
		return pagamentoStatus;
	}

	public void setPagamentoStatus(Collection<TrayPagamentoStatus> pagamentoStatus) {
		this.pagamentoStatus = pagamentoStatus;
	}

	public Collection<TrayCorpInformacoesAdicionais> getInformacoesAdicionais() {
		return informacoesAdicionais;
	}

	public void setInformacoesAdicionais(Collection<TrayCorpInformacoesAdicionais> informacoesAdicionais) {
		this.informacoesAdicionais = informacoesAdicionais;
	}

	@Override
	public String toString() {
		return String.format(
				"TrayPagamento [formaPagamentoId=%s, numeroParcelas=%s, valorParcela=%s, valorDesconto=%s, valorJuros=%s, valorTotal=%s, boleto=%s, cartaoCredito=%s, pagamentoStatus=%s, informacoesAdicionais=%s]",
				formaPagamentoId, numeroParcelas, valorParcela, valorDesconto, valorJuros, valorTotal, boleto,
				cartaoCredito, pagamentoStatus, informacoesAdicionais);
	}
}

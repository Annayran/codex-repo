package tech.skala.action.integration.traycorp;

import java.util.ArrayList;
import java.util.Collection;


import tech.skala.core.beans.interfaces.EntityBean;

public class TrayItem implements EntityBean {
	private long							produtoVarianteId;
	private String							sku;
	private String							nome;
	private long							quantidade;
	private double							precoCusto;
	private double							precoVenda;
	private boolean							isBrinde;
	private double							valorAliquota;
	private boolean							isMarketPlace;
	private double							precoPor;
	private double							desconto;
	private TrayItemTotais					totais;
	private Collection<TrayItemAjuste>		ajustes = new ArrayList<>();
	private Collection<TrayItemCentroDistribuicao>	centroDistribuicao = new ArrayList<>();
	private Collection<String>				valoresAdicionais = new ArrayList<>();
	private Collection<String>				atributos = new ArrayList<>();
	private Collection<String>				embalagens = new ArrayList<>();
	private Collection<String>				personalizacoes = new ArrayList<>();
	private Collection<String>				frete = new ArrayList<>();
	private String							dadosProdutoEvento;
	private Collection<String>				formulas = new ArrayList<>();
	private String							seller;
	public long getProdutoVarianteId() {
		return produtoVarianteId;
	}
	public void setProdutoVarianteId(long produtoVarianteId) {
		this.produtoVarianteId = produtoVarianteId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}
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
	public boolean isBrinde() {
		return isBrinde;
	}
	public void setBrinde(boolean isBrinde) {
		this.isBrinde = isBrinde;
	}
	public double getValorAliquota() {
		return valorAliquota;
	}
	public void setValorAliquota(double valorAliquota) {
		this.valorAliquota = valorAliquota;
	}
	public boolean isMarketPlace() {
		return isMarketPlace;
	}
	public void setMarketPlace(boolean isMarketPlace) {
		this.isMarketPlace = isMarketPlace;
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
	public TrayItemTotais getTotais() {
		return totais;
	}
	public void setTotais(TrayItemTotais totais) {
		this.totais = totais;
	}
	public Collection<TrayItemAjuste> getAjustes() {
		return ajustes;
	}
	public void setAjustes(Collection<TrayItemAjuste> ajustes) {
		this.ajustes = ajustes;
	}
	public Collection<TrayItemCentroDistribuicao> getCentroDistribuicao() {
		return centroDistribuicao;
	}
	public void setCentroDistribuicao(Collection<TrayItemCentroDistribuicao> centroDistribuicao) {
		this.centroDistribuicao = centroDistribuicao;
	}
	public Collection<String> getValoresAdicionais() {
		return valoresAdicionais;
	}
	public void setValoresAdicionais(Collection<String> valoresAdicionais) {
		this.valoresAdicionais = valoresAdicionais;
	}
	public Collection<String> getAtributos() {
		return atributos;
	}
	public void setAtributos(Collection<String> atributos) {
		this.atributos = atributos;
	}
	public Collection<String> getEmbalagens() {
		return embalagens;
	}
	public void setEmbalagens(Collection<String> embalagens) {
		this.embalagens = embalagens;
	}
	public Collection<String> getPersonalizacoes() {
		return personalizacoes;
	}
	public void setPersonalizacoes(Collection<String> personalizacoes) {
		this.personalizacoes = personalizacoes;
	}
	public Collection<String> getFrete() {
		return frete;
	}
	public void setFrete(Collection<String> frete) {
		this.frete = frete;
	}
	public String getDadosProdutoEvento() {
		return dadosProdutoEvento;
	}
	public void setDadosProdutoEvento(String dadosProdutoEvento) {
		this.dadosProdutoEvento = dadosProdutoEvento;
	}
	public Collection<String> getFormulas() {
		return formulas;
	}
	public void setFormulas(Collection<String> formulas) {
		this.formulas = formulas;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	@Override
	public String toString() {
		return String.format(
				"TrayItem [produtoVarianteId=%s, sku=%s, nome=%s, quantidade=%s, precoCusto=%s, precoVenda=%s, isBrinde=%s, valorAliquota=%s, isMarketPlace=%s, precoPor=%s, desconto=%s, totais=%s, ajustes=%s, centroDistribuicao=%s, valoresAdicionais=%s, atributos=%s, embalagens=%s, personalizacoes=%s, frete=%s, dadosProdutoEvento=%s, formulas=%s, seller=%s, getProdutoVarianteId()=%s, getSku()=%s, getNome()=%s, getQuantidade()=%s, getPrecoCusto()=%s, getPrecoVenda()=%s, isBrinde()=%s, getValorAliquota()=%s, isMarketPlace()=%s, getPrecoPor()=%s, getDesconto()=%s, getTotais()=%s, getAjustes()=%s, getCentroDistribuicao()=%s, getValoresAdicionais()=%s, getAtributos()=%s, getEmbalagens()=%s, getPersonalizacoes()=%s, getFrete()=%s, getDadosProdutoEvento()=%s, getFormulas()=%s, getSeller()=%s, getClass()=%s, hashCode()=%s, toString()=%s]",
				produtoVarianteId, sku, nome, quantidade, precoCusto, precoVenda, isBrinde, valorAliquota,
				isMarketPlace, precoPor, desconto, totais, ajustes, centroDistribuicao, valoresAdicionais, atributos,
				embalagens, personalizacoes, frete, dadosProdutoEvento, formulas, seller);
	}


}

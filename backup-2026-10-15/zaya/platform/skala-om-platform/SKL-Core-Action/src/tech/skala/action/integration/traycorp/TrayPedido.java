package tech.skala.action.integration.traycorp;

import java.util.ArrayList;
import java.util.Collection;

import tech.skala.core.beans.interfaces.EntityBean;



public class TrayPedido implements EntityBean {
	private long							pedidoId;
	private long							situacaoPedidoId;
	private String							tipoRastreamentoPedido;
	private long							transacaoId;
	private String							data;
	private String							dataPagamento;
	private String							dataUltimaAtualizacao;
	private double							valorFrete;
	private double							valorTotalPedido;
	private double							valorDesconto;
	private double							valorDebitoCC;
	private String							cupomDesconto;
	private String							marketPlacePedidoId;
	private String							marketPlacePedidoSiteId;
	private long							canalId;
	private String							canalNome;
	private String							canalOrigem;
	private long							retiradaLojaId;
	private boolean							isPedidoEvento;
	private TrayUsuario						usuario;
	private Collection<TrayEndereco>		pedidoEndereco = new ArrayList<>();
	private TrayFrete						frete;
	private Collection<TrayItem>			itens = new ArrayList<>();
	private Collection<String>				assinatura = new ArrayList<>();
	private Collection<TrayPagamento>		pagamento = new ArrayList<>();
	private Collection<TrayObservacao>		observacao = new ArrayList<>();
	private double							valorCreditoFidelidade;
	private boolean							valido;
	private double							valorSubTotalSemDescontos;

	public long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public long getSituacaoPedidoId() {
		return situacaoPedidoId;
	}

	public void setSituacaoPedidoId(long situacaoPedidoId) {
		this.situacaoPedidoId = situacaoPedidoId;
	}

	public String getTipoRastreamentoPedido() {
		return tipoRastreamentoPedido;
	}

	public void setTipoRastreamentoPedido(String tipoRastreamentoPedido) {
		this.tipoRastreamentoPedido = tipoRastreamentoPedido;
	}

	public long getTransacaoId() {
		return transacaoId;
	}

	public void setTransacaoId(long transacaoId) {
		this.transacaoId = transacaoId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(String dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public double getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(double valorFrete) {
		this.valorFrete = valorFrete;
	}

	public double getValorTotalPedido() {
		return valorTotalPedido;
	}

	public void setValorTotalPedido(double valorTotalPedido) {
		this.valorTotalPedido = valorTotalPedido;
	}

	public double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public double getValorDebitoCC() {
		return valorDebitoCC;
	}

	public void setValorDebitoCC(double valorDebitoCC) {
		this.valorDebitoCC = valorDebitoCC;
	}

	public String getCupomDesconto() {
		return cupomDesconto;
	}

	public void setCupomDesconto(String cupomDesconto) {
		this.cupomDesconto = cupomDesconto;
	}

	public String getMarketPlacePedidoId() {
		return marketPlacePedidoId;
	}

	public void setMarketPlacePedidoId(String marketPlacePedidoId) {
		this.marketPlacePedidoId = marketPlacePedidoId;
	}

	public String getMarketPlacePedidoSiteId() {
		return marketPlacePedidoSiteId;
	}

	public void setMarketPlacePedidoSiteId(String marketPlacePedidoSiteId) {
		this.marketPlacePedidoSiteId = marketPlacePedidoSiteId;
	}

	public long getCanalId() {
		return canalId;
	}

	public void setCanalId(long canalId) {
		this.canalId = canalId;
	}

	public String getCanalNome() {
		return canalNome;
	}

	public void setCanalNome(String canalNome) {
		this.canalNome = canalNome;
	}

	public String getCanalOrigem() {
		return canalOrigem;
	}

	public void setCanalOrigem(String canalOrigem) {
		this.canalOrigem = canalOrigem;
	}

	public long getRetiradaLojaId() {
		return retiradaLojaId;
	}

	public void setRetiradaLojaId(long retiradaLojaId) {
		this.retiradaLojaId = retiradaLojaId;
	}

	public boolean isPedidoEvento() {
		return isPedidoEvento;
	}

	public void setPedidoEvento(boolean isPedidoEvento) {
		this.isPedidoEvento = isPedidoEvento;
	}

	public TrayUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(TrayUsuario usuario) {
		this.usuario = usuario;
	}

	public Collection<TrayEndereco> getPedidoEndereco() {
		return pedidoEndereco;
	}

	public void setPedidoEndereco(Collection<TrayEndereco> pedidoEndereco) {
		this.pedidoEndereco = pedidoEndereco;
	}

	public TrayFrete getFrete() {
		return frete;
	}

	public void setFrete(TrayFrete frete) {
		this.frete = frete;
	}

	public Collection<TrayItem> getItens() {
		return itens;
	}

	public void setItens(Collection<TrayItem> itens) {
		this.itens = itens;
	}

	public Collection<String> getAssinatura() {
		return assinatura;
	}

	public void setAssinatura(Collection<String> assinatura) {
		this.assinatura = assinatura;
	}

	public Collection<TrayPagamento> getPagamento() {
		return pagamento;
	}

	public void setPagamento(Collection<TrayPagamento> pagamento) {
		this.pagamento = pagamento;
	}

	public Collection<TrayObservacao> getObservacao() {
		return observacao;
	}

	public void setObservacao(Collection<TrayObservacao> observacao) {
		this.observacao = observacao;
	}

	public double getValorCreditoFidelidade() {
		return valorCreditoFidelidade;
	}

	public void setValorCreditoFidelidade(double valorCreditoFidelidade) {
		this.valorCreditoFidelidade = valorCreditoFidelidade;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public double getValorSubTotalSemDescontos() {
		return valorSubTotalSemDescontos;
	}

	public void setValorSubTotalSemDescontos(double valorSubTotalSemDescontos) {
		this.valorSubTotalSemDescontos = valorSubTotalSemDescontos;
	}

	@Override
	public String toString() {
		return String.format(
				"TrayPedido [pedidoId=%s, situacaoPedidoId=%s, tipoRastreamentoPedido=%s, transacaoId=%s, data=%s, dataPagamento=%s, dataUltimaAtualizacao=%s, valorFrete=%s, valorTotalPedido=%s, valorDesconto=%s, valorDebitoCC=%s, cupomDesconto=%s, marketPlacePedidoId=%s, marketPlacePedidoSiteId=%s, canalId=%s, canalNome=%s, canalOrigem=%s, retiradaLojaId=%s, isPedidoEvento=%s, usuario=%s, pedidoEndereco=%s, frete=%s, itens=%s, assinatura=%s, pagamento=%s, observacao=%s, valorCreditoFidelidade=%s, valido=%s, valorSubTotalSemDescontos=%s]",
				pedidoId, situacaoPedidoId, tipoRastreamentoPedido, transacaoId, data, dataPagamento,
				dataUltimaAtualizacao, valorFrete, valorTotalPedido, valorDesconto, valorDebitoCC, cupomDesconto,
				marketPlacePedidoId, marketPlacePedidoSiteId, canalId, canalNome, canalOrigem, retiradaLojaId,
				isPedidoEvento, usuario, pedidoEndereco, frete, itens, assinatura, pagamento, observacao,
				valorCreditoFidelidade, valido, valorSubTotalSemDescontos);
	}

}

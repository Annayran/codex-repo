package tech.skala.action.integration.traycorp;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayPagamentoStatus implements EntityBean {
	private String							numeroAutorizacao;
	private String							numeroComprovanteVenda;
	private String							dataAtualizacao;
	private String							dataUltimoStatus;
	private String							adquirente;
	private String							tid;
	public String getNumeroAutorizacao() {
		return numeroAutorizacao;
	}
	public void setNumeroAutorizacao(String numeroAutorizacao) {
		this.numeroAutorizacao = numeroAutorizacao;
	}
	public String getNumeroComprovanteVenda() {
		return numeroComprovanteVenda;
	}
	public void setNumeroComprovanteVenda(String numeroComprovanteVenda) {
		this.numeroComprovanteVenda = numeroComprovanteVenda;
	}
	public String getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(String dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	public String getDataUltimoStatus() {
		return dataUltimoStatus;
	}
	public void setDataUltimoStatus(String dataUltimoStatus) {
		this.dataUltimoStatus = dataUltimoStatus;
	}
	public String getAdquirente() {
		return adquirente;
	}
	public void setAdquirente(String adquirente) {
		this.adquirente = adquirente;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	@Override
	public String toString() {
		return String.format(
				"TrayPagamentoStatus [numeroAutorizacao=%s, numeroComprovanteVenda=%s, dataAtualizacao=%s, dataUltimoStatus=%s, adquirente=%s, tid=%s]",
				numeroAutorizacao, numeroComprovanteVenda, dataAtualizacao, dataUltimoStatus, adquirente, tid);
	}

}

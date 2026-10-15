package tech.skala.action.integration.traycorp;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayPagamentoCartao implements EntityBean {
	private String							numeroCartao;
	private String							nomeTitular;
	private String							dataValidade;
	private String							codigoSeguranca;
	private String							documentoCartaoCredito;
	private String							token;
	private String							info;
	private String							bandeira;

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getNomeTitular() {
		return nomeTitular;
	}

	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}

	public String getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(String dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}

	public String getDocumentoCartaoCredito() {
		return documentoCartaoCredito;
	}

	public void setDocumentoCartaoCredito(String documentoCartaoCredito) {
		this.documentoCartaoCredito = documentoCartaoCredito;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	@Override
	public String toString() {
		return String.format(
				"TrayPagamentoCartao [numeroCartao=%s, nomeTitular=%s, dataValidade=%s, codigoSeguranca=%s, documentoCartaoCredito=%s, token=%s, info=%s, bandeira=%s]",
				numeroCartao, nomeTitular, dataValidade, codigoSeguranca, documentoCartaoCredito, token, info,
				bandeira);
	}

}

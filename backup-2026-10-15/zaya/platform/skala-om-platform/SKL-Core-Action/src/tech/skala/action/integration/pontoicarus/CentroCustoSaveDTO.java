package tech.skala.action.integration.pontoicarus;

import tech.skala.core.commons.dd.DTO;

public class CentroCustoSaveDTO implements DTO {
	private String								cei;
	private String								cnpj;
	private String								codigo;
	private String								descricao;
	private String								email;
	private String								endereco;
	private String								localizador;
	private String								numeroRep;
	private String								sigla;
	private String								telefone;
	
	public String getCei() {
		return cei;
	}
	public void setCei(String cei) {
		this.cei = cei;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getLocalizador() {
		return localizador;
	}
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}
	public String getNumeroRep() {
		return numeroRep;
	}
	public void setNumeroRep(String numeroRep) {
		this.numeroRep = numeroRep;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public String toString() {
		return String.format(
				"CentroCustoSaveDTO [cei=%s, cnpj=%s, codigo=%s, descricao=%s, email=%s, endereco=%s, localizador=%s, numeroRep=%s, sigla=%s, telefone=%s]",
				cei, cnpj, codigo, descricao, email, endereco, localizador, numeroRep, sigla, telefone);
	}
	
}

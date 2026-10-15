package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

import tech.skala.core.commons.dd.DTO;

public class PessoaDTO implements DTO {
	private String								cidadeNatal;
	private String								ufNatal;
	private BigDecimal							idPessoa;
	private String								documento;
	@SerializedName(value = "nome")
	private String								nomePessoa;
	private String								dataNascimento;
	private String								email;
	private String								telefone;
	private String								sexo;
	private String								tipoPessoa;
	private String								extrangeiro;
	private String								nomePai;
	private String								nomeMae;
	private String								nacionalidade;
	private String								raca;
	private String								estadoCivil;
	
	public String getCidadeNatal() {
		return cidadeNatal;
	}
	public void setCidadeNatal(String cidadeNatal) {
		this.cidadeNatal = cidadeNatal;
	}
	public String getUfNatal() {
		return ufNatal;
	}
	public void setUfNatal(String ufNatal) {
		this.ufNatal = ufNatal;
	}
	public BigDecimal getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(BigDecimal idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getNomePessoa() {
		return nomePessoa;
	}
	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	public String getExtrangeiro() {
		return extrangeiro;
	}
	public void setExtrangeiro(String extrangeiro) {
		this.extrangeiro = extrangeiro;
	}
	public String getNomePai() {
		return nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	public String getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	@Override
	public String toString() {
		return String.format(
				"PessoaDTO [cidadeNatal=%s, ufNatal=%s, idPessoa=%s, documento=%s, nomePessoa=%s, dataNascimento=%s, email=%s, telefone=%s, sexo=%s, tipoPessoa=%s, extrangeiro=%s, nomePai=%s, nomeMae=%s, nacionalidade=%s, raca=%s, estadoCivil=%s]",
				cidadeNatal, ufNatal, idPessoa, documento, nomePessoa, dataNascimento, email, telefone, sexo,
				tipoPessoa, extrangeiro, nomePai, nomeMae, nacionalidade, raca, estadoCivil);
	}
	
}

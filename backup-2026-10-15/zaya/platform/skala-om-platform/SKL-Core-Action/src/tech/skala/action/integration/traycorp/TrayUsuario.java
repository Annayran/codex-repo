package tech.skala.action.integration.traycorp;

import tech.skala.core.beans.interfaces.EntityBean;

public class TrayUsuario implements EntityBean {
	private long							usuarioId;
	private String							tipoPessoa;
	private String							origemContato;
	private String							tipoSexo;
	private String							nome;
	private String							cpf;
	private String							email;
	private String							rg;
	private String							telefoneResidencial;
	private String							telefoneCelular;
	private String							telefoneComercial;
	private String							dataNascimento;
	private String							razaoSocial;
	private String							cnpj;
	private String							inscricaoEstadual;
	private String							responsavel;
	private String							dataCriacao;
	private String							dataAtualizacao;
	private boolean							revendedor;
	private String							listaInformacaoCadastral;

	public long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getOrigemContato() {
		return origemContato;
	}

	public void setOrigemContato(String origemContato) {
		this.origemContato = origemContato;
	}

	public String getTipoSexo() {
		return tipoSexo;
	}

	public void setTipoSexo(String tipoSexo) {
		this.tipoSexo = tipoSexo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(String dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public boolean isRevendedor() {
		return revendedor;
	}

	public void setRevendedor(boolean revendedor) {
		this.revendedor = revendedor;
	}

	public String getListaInformacaoCadastral() {
		return listaInformacaoCadastral;
	}

	public void setListaInformacaoCadastral(String listaInformacaoCadastral) {
		this.listaInformacaoCadastral = listaInformacaoCadastral;
	}

	@Override
	public String toString() {
		return String.format(
				"TrayUsuario [usuarioId=%s, tipoPessoa=%s, origemContato=%s, tipoSexo=%s, nome=%s, cpf=%s, email=%s, rg=%s, telefoneResidencial=%s, telefoneCelular=%s, telefoneComercial=%s, dataNascimento=%s, razaoSocial=%s, cnpj=%s, inscricaoEstadual=%s, responsavel=%s, dataCriacao=%s, dataAtualizacao=%s, revendedor=%s, listaInformacaoCadastral=%s]",
				usuarioId, tipoPessoa, origemContato, tipoSexo, nome, cpf, email, rg, telefoneResidencial,
				telefoneCelular, telefoneComercial, dataNascimento, razaoSocial, cnpj, inscricaoEstadual, responsavel,
				dataCriacao, dataAtualizacao, revendedor, listaInformacaoCadastral);
	}

}

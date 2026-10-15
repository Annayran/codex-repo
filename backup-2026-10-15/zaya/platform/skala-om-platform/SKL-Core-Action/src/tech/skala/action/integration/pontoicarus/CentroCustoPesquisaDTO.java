package tech.skala.action.integration.pontoicarus;

import tech.skala.core.commons.dd.DTO;

public class CentroCustoPesquisaDTO implements DTO {
	private String								codigo;
	private String								descricao;
	private transient String					sigla;
	private boolean								somenteVinculadosUsuario;
	
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
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public boolean isSomenteVinculadosUsuario() {
		return somenteVinculadosUsuario;
	}
	public void setSomenteVinculadosUsuario(boolean somenteVinculadosUsuario) {
		this.somenteVinculadosUsuario = somenteVinculadosUsuario;
	}
	
	@Override
	public String toString() {
		return String.format("CentroCustoPesquisaDTO [codigo=%s, descricao=%s, sigla=%s, somenteVinculadosUsuario=%s]",
				codigo, descricao, sigla, somenteVinculadosUsuario);
	}
	
}

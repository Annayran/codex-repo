package tech.skala.action.integration.pontoicarus;

import tech.skala.core.commons.dd.DTO;

public class CargoSaveDTO implements DTO {
	private String								codigo;
	private String								descricao;
	private String								sigla;
	
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
	
	@Override
	public String toString() {
		return String.format("CargoSaveDTO [codigo=%s, descricao=%s, sigla=%s]", codigo, descricao, sigla);
	}
	
}

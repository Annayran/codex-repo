package tech.skala.action.integration.pontoicarus;

import tech.skala.core.commons.dd.DTO;

public class LotacaoSaveDTO implements DTO {
	private String								codigo;
	private String								descricao;
	
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
	
	@Override
	public String toString() {
		return String.format("LotacaoSaveDTO [codigo=%s, descricao=%s]", codigo, descricao);
	}
}

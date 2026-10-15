package tech.skala.action.integration.pontoicarus;

import tech.skala.core.commons.dd.DTO;

public class PerfilPesquisaDTO implements DTO {
	private String								papel;

	public PerfilPesquisaDTO() {
		super();
		this.papel = "COLABORADOR";
	}
	
	public String getPapel() {
		return papel;
	}
	public void setPapel(String papel) {
		this.papel = papel;
	}
	@Override
	public String toString() {
		return String.format("PerfilPesquisaDTO [papel=%s]", papel);
	}

}

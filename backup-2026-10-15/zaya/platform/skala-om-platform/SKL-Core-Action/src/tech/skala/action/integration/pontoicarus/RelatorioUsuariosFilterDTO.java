package tech.skala.action.integration.pontoicarus;

import tech.skala.core.commons.dd.DTO;

public class RelatorioUsuariosFilterDTO implements DTO {
	private String								papel;
	private String								status;
	private String								statusAcessoUsuario;
	
	public String getPapel() {
		return papel;
	}
	public void setPapel(String papel) {
		this.papel = papel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusAcessoUsuario() {
		return statusAcessoUsuario;
	}
	public void setStatusAcessoUsuario(String statusAcessoUsuario) {
		this.statusAcessoUsuario = statusAcessoUsuario;
	}
	
	@Override
	public String toString() {
		return String.format("RelatorioUsuariosFilterDTO [papel=%s, status=%s, statusAcessoUsuario=%s]", papel, status,
				statusAcessoUsuario);
	}

}

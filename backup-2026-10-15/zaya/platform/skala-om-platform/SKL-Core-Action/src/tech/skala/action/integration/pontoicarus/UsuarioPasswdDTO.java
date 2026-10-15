package tech.skala.action.integration.pontoicarus;

import tech.skala.core.commons.dd.DTO;

public class UsuarioPasswdDTO implements DTO {
	private PessoaIdDTO							pessoa;
	private PerfilIdDTO							perfil;
	private String								passwordUsuario;
	
	public PessoaIdDTO getPessoa() {
		return pessoa;
	}
	public void setPessoa(PessoaIdDTO pessoa) {
		this.pessoa = pessoa;
	}
	public PerfilIdDTO getPerfil() {
		return perfil;
	}
	public void setPerfil(PerfilIdDTO perfil) {
		this.perfil = perfil;
	}
	public String getPasswordUsuario() {
		return passwordUsuario;
	}
	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}
	
	@Override
	public String toString() {
		return String.format("UsuarioPasswdDTO [pessoa=%s, perfil=%s, passwordUsuario=%s]", pessoa, perfil,
				passwordUsuario);
	}

}

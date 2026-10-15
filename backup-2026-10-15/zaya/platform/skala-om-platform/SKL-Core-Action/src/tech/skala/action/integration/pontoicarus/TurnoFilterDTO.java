package tech.skala.action.integration.pontoicarus;

import tech.skala.core.commons.dd.DTO;

public class TurnoFilterDTO implements DTO {
	private String								descricao;
	private String								tipoTurno;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoTurno() {
		return tipoTurno;
	}
	public void setTipoTurno(String tipoTurno) {
		this.tipoTurno = tipoTurno;
	}
	
	@Override
	public String toString() {
		return String.format("TurnoFilterDTO [descricao=%s, tipoTurno=%s]", descricao, tipoTurno);
	}
	
}

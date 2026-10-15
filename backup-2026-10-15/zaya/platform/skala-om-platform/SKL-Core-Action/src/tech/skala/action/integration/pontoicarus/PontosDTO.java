package tech.skala.action.integration.pontoicarus;

import java.util.LinkedList;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class PontosDTO implements DTO {
	private List<PontoDTO>						pontos;
	
	public PontosDTO() {
		pontos = new LinkedList<>();
	}
	
	public List<PontoDTO> getPontos() {
		return pontos;
	}
	public void setPontos(List<PontoDTO> pontos) {
		this.pontos = pontos;
	}

	@Override
	public String toString() {
		return String.format("PontosDTO [pontos=%s]", pontos);
	}

}

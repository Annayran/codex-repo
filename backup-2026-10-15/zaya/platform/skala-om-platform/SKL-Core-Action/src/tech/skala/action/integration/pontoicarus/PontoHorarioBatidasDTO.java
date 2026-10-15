package tech.skala.action.integration.pontoicarus;

import java.util.LinkedList;
import java.util.List;

import tech.skala.core.commons.dd.DTO;

public class PontoHorarioBatidasDTO implements DTO {
	private List<PontoHorarioBatidaDTO>			batidas;

	public PontoHorarioBatidasDTO() {
		batidas = new LinkedList<>();
	}
	
	public List<PontoHorarioBatidaDTO> getBatidas() {
		return batidas;
	}
	public void setBatidas(List<PontoHorarioBatidaDTO> batidas) {
		this.batidas = batidas;
	}
	
	@Override
	public String toString() {
		return String.format("PontoHorarioBatidasDTO [batidas=%s]", batidas);
	}

}

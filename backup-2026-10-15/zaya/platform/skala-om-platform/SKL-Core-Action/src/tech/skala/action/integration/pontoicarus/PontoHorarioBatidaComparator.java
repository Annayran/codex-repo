package tech.skala.action.integration.pontoicarus;

import java.util.Comparator;

import com.sankhya.util.StringComparator;

public class PontoHorarioBatidaComparator implements Comparator<PontoHorarioBatidaDTO> {

	@Override
	public int compare(PontoHorarioBatidaDTO o1, PontoHorarioBatidaDTO o2) {
		return StringComparator.compare(o1.getHoraRegistroFormatado(), o2.getHoraRegistroFormatado());
	}

}

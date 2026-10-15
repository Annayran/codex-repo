package tech.skala.action.beans.departamento;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class DepartamentoFinder {

	private DepartamentoFinder() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Departamento findByPk(BigDecimal codDep) throws RecordNotFoundException {
		Departamento departamento = new Departamento();
		departamento.loadByPk(codDep);
		
		return departamento;
	}

}

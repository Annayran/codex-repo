package tech.skala.action.beans.cargo;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class CargoFinder {

	private CargoFinder() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Cargo findByPk(BigDecimal codCargo) throws RecordNotFoundException {
		Cargo cargo = new Cargo();
		cargo.loadByPk(codCargo);
		
		return cargo;
	}
}

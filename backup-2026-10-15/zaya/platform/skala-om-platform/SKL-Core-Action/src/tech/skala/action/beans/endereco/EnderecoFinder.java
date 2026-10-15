package tech.skala.action.beans.endereco;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class EnderecoFinder {
	private EnderecoFinder() {
		throw new IllegalStateException("Utility class");
	}
	
	public static UnidadeFederativa findUnidadeFederativaByPk(BigDecimal codUf) throws RecordNotFoundException {
		UnidadeFederativa uf = new UnidadeFederativa();
		uf.loadByPk(codUf);
		
		return uf;
	}
	
	public static Cidade findCidadeByPk(BigDecimal codCid) throws RecordNotFoundException {
		Cidade cidade = new Cidade();
		cidade.loadByPk(codCid);
		
		return cidade;
	}

}

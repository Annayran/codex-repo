package tech.skala.action.beans.funcionario;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class FuncionarioDAO {

	private FuncionarioDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static TipoRescisao findTipoRescisaoByPk(BigDecimal codTpr) throws RecordNotFoundException {
		TipoRescisao tipoRescisao = new TipoRescisao();
		tipoRescisao.loadByPk(codTpr);
		
		return tipoRescisao;
	}

}

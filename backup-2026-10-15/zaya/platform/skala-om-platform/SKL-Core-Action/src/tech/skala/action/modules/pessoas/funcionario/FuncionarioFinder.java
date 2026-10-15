package tech.skala.action.modules.pessoas.funcionario;

import java.math.BigDecimal;

import tech.skala.action.integration.tangerino.Company;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class FuncionarioFinder {

	private FuncionarioFinder() {
		throw new IllegalStateException("Utility class");
	}

	public static Funcao buscaFuncaoByPk(BigDecimal codFuncao) throws RecordNotFoundException {
		Funcao funcao = new Funcao();
		funcao.loadByPk(codFuncao);
		
		return funcao;
	}

	public static Company buscaEmpresaByPk(BigDecimal codEmp) throws RecordNotFoundException {
		Company company = new Company();
		company.loadByPk(codEmp);
		
		return company;
	}

}

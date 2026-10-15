package tech.skala.action.modules.empresas;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class EmpresaFinder {

	private EmpresaFinder() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Empresa findByPk(BigDecimal codEmp) throws RecordNotFoundException {
		Empresa empresa = new Empresa();
		empresa.loadByPk(codEmp);
		
		return empresa;
	}

	public static Collection<DynamicVO> findAllAsVO() {
		return null;
	}
}

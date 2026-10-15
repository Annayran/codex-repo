package tech.skala.action.integration.tangerino;

import java.util.logging.Logger;

import tech.skala.action.integration.tangerino.model.integracao.EmployeeFunctionSimpleDTO;
import tech.skala.action.modules.pessoas.funcionario.Funcao;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * 
 * @author cassiomenezes
 * @since 2023-01-23
 *
 */
public class TangerinoProcessor {
	private static final Logger LOGGER = Logger.getLogger(TangerinoProcessor.class.getCanonicalName());

	public void enviar() {
		
		enviaFuncao();
		
	}

	private void enviaFuncao() {
		try {
			EmployeeFunctionSimpleDTO dto = null;
			for (Funcao funcao : TangerinoFinder.carregaFuncoesEnviar()) {
				dto = (EmployeeFunctionSimpleDTO) TangerinoMapper.funcao2FunctionSimpleDtoMapper(funcao);
				
				
			}
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	
}

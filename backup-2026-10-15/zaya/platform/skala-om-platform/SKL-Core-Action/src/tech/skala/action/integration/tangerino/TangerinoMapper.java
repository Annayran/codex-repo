package tech.skala.action.integration.tangerino;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import tech.skala.action.integration.tangerino.model.integracao.EmployeeDTO;
import tech.skala.action.integration.tangerino.model.integracao.EmployeeFunctionSimpleDTO;
import tech.skala.action.modules.pessoas.funcionario.Funcao;
import tech.skala.core.commons.dd.DTO;

/**
 * 
 * @author cassiomenezes
 * @since 2023-01-23
 *
 */
public class TangerinoMapper {
	private static ModelMapper					modelMapper;

	private TangerinoMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static DTO funcao2FunctionSimpleDtoMapper(Funcao funcao) {
		
		modelMapper = new ModelMapper();
		modelMapper.typeMap(Funcao.class, EmployeeFunctionSimpleDTO.class).addMappings(mapper -> {
			mapper.map(Funcao::getDescrFuncao, EmployeeFunctionSimpleDTO::setDescription);
			mapper.map(Funcao::getCodFuncao, EmployeeFunctionSimpleDTO::setExternalId);
		});
		
		return modelMapper.map(funcao, EmployeeFunctionSimpleDTO.class);
	}

	public static DTO employee2EmployeeDtoMapper(Employee employee) {
		Condition<Long, Long> isNotNull = ctx -> ctx.getSource() != null;
		
		Converter<Timestamp, BigDecimal> timeStampToBigDecimal = ctx -> ctx.getSource() == null ? null : BigDecimal.valueOf(ctx.getSource().getTime());
		Converter<String, String> toUppercase = ctx -> ctx.getSource() == null ? null : ctx.getSource().toUpperCase();
			    
		modelMapper = new ModelMapper();
		modelMapper.typeMap(Employee.class, EmployeeDTO.class).addMappings(mapper -> {
			mapper.using(timeStampToBigDecimal).map(Employee::getDtAdm, EmployeeDTO::setAdmissionDate);
			mapper.using(timeStampToBigDecimal).map(Employee::getDtNasc, EmployeeDTO::setBirthDate);
			mapper.map(Employee::getIdentidade, EmployeeDTO::setCi);
			mapper.map(Employee::getCodigoTangerinoFilial, EmployeeDTO::setCompany);
			mapper.map(Employee::getCodigoExternoFilial, EmployeeDTO::setCompanyExternalId);
			mapper.using(timeStampToBigDecimal).map(Employee::getEffectiveDate, EmployeeDTO::setEffectiveDate);
			mapper.map(Employee::getCpf, EmployeeDTO::setExternalId);
			mapper.map(Employee::getCodigoExternoFuncao, EmployeeDTO::setFunctionExternalId);
			mapper.map(Employee::getCodigoTangerinoFuncao, EmployeeDTO::setFunctionId);
			mapper.map(Employee::getNumCps, EmployeeDTO::setCtps);
			mapper.map(Employee::getTangerinoId, EmployeeDTO::setId);
			mapper.map(Employee::getNomeFunc, EmployeeDTO::setName);
			mapper.map(Employee::getTangerinoId, EmployeeDTO::setTangerinoId);
			mapper.when(isNotNull).map(Employee::getCodigoTangerinoFuncao, EmployeeDTO::setFunctionId);
		});

		return modelMapper.map(employee, EmployeeDTO.class);
	}
	
}

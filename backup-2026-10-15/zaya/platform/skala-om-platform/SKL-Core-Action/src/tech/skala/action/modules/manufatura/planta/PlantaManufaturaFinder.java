package tech.skala.action.modules.manufatura.planta;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * A classe {@code PlantaManufaturaFinder} prove recursos para busca de informacoes relacionadas 
 * a uma {@link PlantaManufatura}.
 * 
 * @author hiagoalexandre
 * @since 2022-08-05
 *
 */
public class PlantaManufaturaFinder {

	/**
	 * Este metodo deve retornar o Identificador único de uma <b>Empresa</b>
	 * {@link TSIEMP}.
	 * 
	 * @return codEmp O código da Empresa cadastrado na <b>Planta de Manufatura</b>.
	 * @throws RecordNotFoundException Caso a Planta de Manufatura não seja encontrada.
	 * 
	 */
	public static BigDecimal findCodEmp(BigDecimal codPlp) throws RecordNotFoundException {
		BigDecimal codEmp = null;

		try {
			PlantaManufatura planta = new PlantaManufatura();
			planta.loadByPk(codPlp);

			codEmp = planta.getCodEmp();
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

		return codEmp;
	}
}

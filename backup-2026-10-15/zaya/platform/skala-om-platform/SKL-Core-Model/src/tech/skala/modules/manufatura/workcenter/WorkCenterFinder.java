package tech.skala.modules.manufatura.workcenter;

import java.math.BigDecimal;

import tech.skala.modules.manufatura.workcenter.WorkCenterExceptions.WorkCenterNotFoundException;

/**
 * A classe {@code WorkCenterFinder} prove recursos para busca de informacoes relacionadas 
 * a um {@link WorkCenter}.
 * 
 * @author hiagoalexandre
 * @since 2022-08-03
 *
 */
public class WorkCenterFinder {

	private WorkCenterFinder() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Este metodo deve retornar a quantidade padrão de um <b>Centro de trabalho</b> para 
	 * um {@link WorkCenter}.
	 * 
	 * @return qtdCapacidadePad A quantidade Padrão de um <b>Centro de Trabalho</b>.  
	 * @throws WorkCenterNotFoundException Caso o WorkCenter não seja encontrado.
	 * 
	 */
	public static BigDecimal findQtdCapacidadePadrao(BigDecimal codWcp) throws WorkCenterNotFoundException {
		BigDecimal qtdCapacidadePad = null;

		try {
			WorkCenter workCenter = new WorkCenter();
			workCenter.loadByPk(codWcp);

			qtdCapacidadePad = workCenter.getQtdCapacidadePad();
		} catch (Exception e) {
			throw new WorkCenterExceptions.WorkCenterNotFoundException(e, codWcp);
		}

		return qtdCapacidadePad;
	}
}

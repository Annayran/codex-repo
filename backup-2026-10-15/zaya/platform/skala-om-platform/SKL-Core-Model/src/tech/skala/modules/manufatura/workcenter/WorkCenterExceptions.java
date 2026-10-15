package tech.skala.modules.manufatura.workcenter;

import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.i18n.I18nMessageFactory;


/**
 * A classe {@code WorkCenterExceptions} contem as diversas excecoes usadas 
 * no pacote {@link tech.skala.modules.manufatura.workcenter}.
 * 
 * <p>
 * Todas as excecoes deverao ser metodos estaticos (<i>static</i>) e deverao extender a 
 * classe {@link SkalaModelException} utilizando as mensagens em um pacote de internacionalizacao.
 * </p>
 * 
 * @author hiagoalexandre
 * @since 2022-08-03
 *
 */
public class WorkCenterExceptions {
	public static final String				EXCEPTION_BASE_NAME = "tech.skala.action.modules.manufatura.i18n.ExceptionMessages";
	

	private WorkCenterExceptions() {
		throw new IllegalStateException("Utility class");
	}

	public static class WorkCenterNotFoundException extends SkalaModelException {

		private static final long serialVersionUID = 8647850584538335650L;

		public WorkCenterNotFoundException(Throwable error, Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("workcenter-not-found"), args), error);
		}
		
		public WorkCenterNotFoundException(Object... args) {
			super(String.format(I18nMessageFactory.getInstance(EXCEPTION_BASE_NAME).getMessage("workcenter-not-found"), args));
		}

	}
}

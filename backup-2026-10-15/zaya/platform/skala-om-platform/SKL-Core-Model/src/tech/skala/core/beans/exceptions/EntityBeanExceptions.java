package tech.skala.core.beans.exceptions;

import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByRecord;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.beans.interfaces.LoadableBySql;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * Disparada para indicar que uma classe que implementa metodos de alguma 
 * interface do pacote {@code tech.skala.core.beans.interfaces} que interagem 
 * com o banco de dados nao implementaram a classe {@link EntityBean}.
 * 
 * @author cassiomenezes
 * @since 2022-06-30
 * @see EntityBean
 * @see LoadableByPrimaryKey
 * @see LoadableByRecord
 * @see LoadableByResultSet
 * @see LoadableBySql
 * @see Persistable
 *
 */
public class EntityBeanExceptions {
	public static final String				EXCEPTION_BASE_NAME = "tech.skala.core.beans.exceptions.Messages";
	
	private static final String NAO_FOI_POSSIVEL_INICIALIZAR_OBJETO_DO_REGISTRO = "Não foi possível inicializar o objeto a partir do registro selecionado.";
	
	private EntityBeanExceptions() {
		throw new IllegalStateException("Utility class");
	}

	public static class RecordNotFoundException extends SkalaModelException {

		private static final long serialVersionUID = -6571362229002521054L;

		public RecordNotFoundException() {
			super(EXCEPTION_BASE_NAME, "record-not-found");
		}
	}
	
	public static class SetFromRecordNotPossibleException extends SkalaModelException {

		private static final long serialVersionUID = 7410666029756114994L;

		public SetFromRecordNotPossibleException(Throwable cause) {
			super(NAO_FOI_POSSIVEL_INICIALIZAR_OBJETO_DO_REGISTRO, cause);
		}

	}

	public static class PersonNotClientException extends SkalaModelException {

		private static final long serialVersionUID = -2120110194169479478L;
		
		public PersonNotClientException() {
			super(EXCEPTION_BASE_NAME, "person-not-client");
		}
		
		public PersonNotClientException(Throwable cause) {
			super(EXCEPTION_BASE_NAME, "person-not-client", cause);
		}
		
	}
}

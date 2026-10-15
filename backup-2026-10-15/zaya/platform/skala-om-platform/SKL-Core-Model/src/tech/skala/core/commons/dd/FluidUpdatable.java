package tech.skala.core.commons.dd;

import tech.skala.core.commons.dd.DataDictionaryExceptions.InvalidFieldException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.NullPrimaryKeyException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.NullValueObjectException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

/**
 * Facilitador para manutencao de banco de dados.
 * 
 * <p>
 * Esta interface exige a implementacao do metodo {@code fluidUpdate()} que devera 
 * persistir uma alteracao em um registro especifico.
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022/07/03
 * 
 * @see Entity
 *
 */
public interface FluidUpdatable {
	
	/**
	 * Efetua a atualizacao de um registro em uma entidade.
	 * <p>
	 * Eh necessario que exista uma {@code PK} ou um {@code DynamicVO} validos, alem de uma lista com um 
	 * ou mais campos para que seja possivel efetuar a atualizacao de um registro de banco de dados.
	 * </p>
	 * 
	 * @throws UpdateNotPossibleException quando nao for possivel persistir a alteracao desejada..
	 * @throws InvalidFieldException quando nao existir na entidade algum campo da lista de campos informada.
	 * @throws NullPrimaryKeyException quando a {@code PK} é nula e nao ha nenhum {@code DynamicVO} valido.
	 * @throws NullValueObjectException quando nao ha nenhum {@code DynamicVO} valido.
	 * @throws RecordNotFoundException quando a entidade nao possui um dos campos informados.
	 */
	void fluidUpdate() throws UpdateNotPossibleException, InvalidFieldException, NullPrimaryKeyException, NullValueObjectException, RecordNotFoundException;

}

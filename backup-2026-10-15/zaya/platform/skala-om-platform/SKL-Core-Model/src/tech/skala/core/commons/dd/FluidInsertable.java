package tech.skala.core.commons.dd;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InvalidFieldException;

/**
 * Facilitador para manutencao de banco de dados.
 * 
 * <p>
 * Esta interface exige a implementacao do metodo {@code fluidInsert()} que devera 
 * persistir uma insercao de um registro especifico.
 * </p>
 * 
 * @author cassiomenezes
 * @author marcosdamasceno
 * @author hiagoalexandre
 * @since 2022/07/03
 * 
 * @see Entity
 *
 */
public interface FluidInsertable {

	/**
	 * Efetua a insercao de um registro registro em uma entidade.
	 * <p>
	 * Eh necessario que exista uma lista com um ou mais campos para que seja possivel efetuar a insercao 
	 * de um registro de banco de dados.
	 * </p>
	 * 
	 * @return {@link DynamicVO} Retorna o ValueObject do objeto alterado.
	 * @throws InsertNotPossibleException quando nao for possivel persistir a alteracao desejada..
	 * @throws InvalidFieldException quando nao existir na entidade algum campo da lista de campos informada.
	 * 
	 */
	DynamicVO fluidInsert() throws InsertNotPossibleException, InvalidFieldException;
}

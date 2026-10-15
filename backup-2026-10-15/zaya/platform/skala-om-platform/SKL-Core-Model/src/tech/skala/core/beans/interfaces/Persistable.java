package tech.skala.core.beans.interfaces;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

/**
 * Persiste um registro no banco de dados.
 * 
 * <p>
 * Esta interface exige a implementacao do metodo {@code persists()} para facilitar uma 
 * forma de inserir e persistir um registro no banco de dados.
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-07-01
 * 
 */
public interface Persistable {
	
	/**
	 * Metodo responsavel pela insercao ou atualizacao de um registro no banco de dados.
	 * 
	 * @return DynamicVO representando o registro recem inserido.
	 * @throws InsertNotPossibleException quando nao for possivel efetivar a insercao.
	 * @throws UpdateNotPossibleException quando nao for possivel efetivar a atualizacao.
	 * 
	 */
	DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException;

}

package tech.skala.core.commons.dd;

import java.util.Set;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;

/**
 * A classe {@code EntityInsert} simplifica a tarefa de registros de entidades do sistema.
 * 
 * <p>
 * A classe {@code EntityInsert} extende a classe {@link Entity} e deve ser utilizada sempre 
 * que se efetua inclusao via {@code JdbcWrapper} de um registro bastando um 
 * <b>Nome da Entidade/Instância</b> válido e lista de campos.<br>
 * <br>
 * <b>Exemplo</b>:
 * </p>
 * <pre>
 * <code>
 * 	EntityInsert entityNew = new EntityInsert(DynamicEntityNames.PARCEIRO);
 * 	entityNew.setField(SklFieldName.NOMEPARC,"Nome do Parceiro");
 * 	entityNew.setField(SklFieldName.CGC_CPF,"11111111111");
 * 	entityNew.setField(SklFieldName.CLIENTE,"S");
 * 	entityNew.fluidInsert();
 * </code>
 * </pre>
 * 
 * <b>Importante</b>: Eh necessario informar no minimo os campos obrigatorios da entidade.
 * 
 * @author cassiomenezes
 * @author marcosdamasceno
 * @author hiagoalexandre
 * 
 * @see SklEntityName
 * @see SklFieldName
 * @see DynamicEntityNames
 * @see Entity
 * 
 * @since 2022-07-02
 * 
 */
public final class EntityInsert extends Entity implements FluidInsertable {

	public EntityInsert(String entityName) {
		super(entityName);
	}

	/**
	 * Efetua a insercao do registro solicitado utilizando um {@code FluidCreateVO}.
	 * <p>
	 * Eh necessario que exista uma {@code PK} ou um {@code DynamicVO} validos, alem de uma lista com um 
	 * ou mais campos para que seja possivel efetuar a atualizacao de um registro de banco de dados.
	 * </p>
	 * 
	 * @return {@link DynamicVO} Retorna o ValueObject do objeto alterado.
 	 * @throws InsertNotPossibleException quando nao for possivel persistir o registro.
	 */
	@Override
	public DynamicVO fluidInsert() throws InsertNotPossibleException {
		
		JapeWrapper dao = JapeFactory.dao(entityName);
		FluidCreateVO newVO = dao.create();
		
		Set<String> fieldNames = fields.keySet();
		for (String fieldName : fieldNames)
			newVO.set(fieldName, fields.get(fieldName));
		
		try {
			return newVO.save();
		} catch (Exception e) {
			throw new InsertNotPossibleException(e);
		}
	}
}

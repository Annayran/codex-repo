package tech.skala.core.commons.dd;

import java.util.Set;

import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InvalidFieldException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.NullPrimaryKeyException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.NullValueObjectException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

/**
 * A classe {@code EntityUpdate} simplifica a tarefa de atualizacao de campos de entidades do sistema.
 * 
 * <p>
 * A classe {@code EntityUpdate} extende a classe {@link Entity} e deve ser utilizada sempre que se 
 * efetua atualização em um registro especifico bastando o <b>Nome da Entidade/Instância</b> e 
 * a {@code Primary Key} do registro ou um {@code DynamicVO} válido.
 * </p>
 * <p>
 * Exemplo:<br>
 * </p>
 * <pre>
 * <code>
 * 	pk = new EntityPrimaryKey(new Object[] { seqOp, idiProc });
 * 	
 * 	EntityUpdate entityUpd = new EntityUpdate(SklEntityName.SEQUENCIAMENTO_OP_INSTANCIA, pk);
 * 	entityUpd.setField(SklFieldName.CONCLUIDO,"S");
 * 	entityUpd.fluidUpdate();
 * </code>
 * </pre>
 * <p>
 * <b>Importante</b>: A atualizacao eh feita somente em um <b>unico registro</b>
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-06-28
 * 
 * @see SklEntityName
 * @see SklFieldName
 * 
 */
public final class EntityUpdate extends Entity implements FluidUpdatable {

	/**
	 * Inicializa a classe {@code EntityUpdate} utilizando uma chave primaria valida de um registro.
	 * 
	 * @param entityName Nome da Entidade/Instancia
	 * @param pk Chave do registro do tipo {@code EntityPrimaryKey}
	 * 
	 * @see SklEntityName
	 */
	public EntityUpdate(String entityName, EntityPrimaryKey pk) {
		super(entityName, pk);
	}

	/**
	 * Inicializa a classe {@code EntityUpdate} utilizando um {@code DynamicVO} válido.
	 * 
	 * @param entityName Nome da Entidade/Instancia
	 * @param vo {@code DynamicVO} válido
	 * 
	 * @see SklEntityName
	 */
	public EntityUpdate(String entityName, DynamicVO vo) {
		super(entityName, vo);
	}

	@Override
	public void fluidUpdate() throws UpdateNotPossibleException {
		if (vo == null) {
			if (pk == null)
				throw new UpdateNotPossibleException(new NullPrimaryKeyException());

			try {
				vo = (DynamicVO) JapeFactory.getEntityFacade().findEntityByPrimaryKeyAsVO(entityName, pk);
			} catch (Exception e) {
				throw new UpdateNotPossibleException(new RecordNotFoundException(e));
			}
		}

		if (vo == null)
			throw new UpdateNotPossibleException(new NullValueObjectException());

		JapeWrapper dao = JapeFactory.dao(entityName);
		FluidUpdateVO updVO = dao.prepareToUpdate(vo);

		Set<String> fieldNames = fields.keySet();
		for (String fieldName : fieldNames) {
			if (vo.containsProperty(fieldName))
				updVO.set(fieldName, fields.get(fieldName));
			else
				throw new UpdateNotPossibleException(new InvalidFieldException());
		}

		try {
			updVO.update();
		} catch (Exception e) {
			throw new UpdateNotPossibleException(e);
		}
	}

}

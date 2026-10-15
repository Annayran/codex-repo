package tech.skala.core.commons.dd;

import java.util.LinkedHashMap;
import java.util.Map;

import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.DynamicVOPojo;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;

/**
 * A classe {@code Entity} deve ser extendida para operacoes com banco de dados.
 * 
 * <p>
 * A classe {@code Entity} deve ser extendida sempre que for necessario persistir, incluir ou 
 * atualizar um registro no banco de dados. 
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-06-28
 * 
 * @see SklEntityName
 * @see SklFieldName
 * @see EntityPrimaryKey
 * @see DynamicVOPojo
 * 
 */
public abstract class Entity {
	protected final String						entityName;
	protected Map<String, Object>				fields;
	protected EntityPrimaryKey					pk;
	protected DynamicVO							vo;

	/**
	 * Inicializa a classe {@code Entity} utilizando um nome de entidade.
	 * 
	 * @param entityName Nome da Entidade/Instancia
	 * 
	 * @see SklEntityName
	 */
	protected Entity(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * Inicializa a classe {@code Entity} utilizando uma chave primaria valida de um registro.
	 * 
	 * @param entityName Nome da Entidade/Instancia
	 * @param pk Chave do registro do tipo {@code EntityPrimaryKey}
	 * 
	 * @see SklEntityName
	 */
	protected Entity(String entityName, EntityPrimaryKey pk) {
		this.entityName = entityName;
		this.pk = pk;
	}

	/**
	 * Inicializa a classe {@code Entity} utilizando um {@code DynamicVO} válido.
	 * 
	 * @param entityName Nome da Entidade/Instancia
	 * @param vo {@code DynamicVO} válido
	 * 
	 * @see SklEntityName
	 */
	protected Entity(String entityName, DynamicVO vo) {
		this.entityName = entityName;
		this.vo = vo;
	}

	/**
	 * Atualiza uma colecao com o par {@code <Key, Value>} <b>Nome</b> e <b>Valor</b>.<br>
	 * Trata-se dos campos que serao persistidos com seus respectivos valores.
	 * 
	 * @param fieldName Nome de um campo da tabela
	 * @param fieldValue Valor a ser usado
	 * 
	 * @see SklFieldName
	 */
	public void setField(String fieldName, Object fieldValue) {
		if (fields == null)
			fields = new LinkedHashMap<>();
		
		this.fields.put(fieldName, fieldValue);
	}

}

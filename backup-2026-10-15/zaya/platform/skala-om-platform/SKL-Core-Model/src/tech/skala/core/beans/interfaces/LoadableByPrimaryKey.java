package tech.skala.core.beans.interfaces;

import br.com.sankhya.jape.vo.DynamicVOPojo;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * Inicializa o objeto a partir de um registro do banco de dados.
 * 
 * <p>
 * Esta interface exige a implementacao do metodo {@code loadByPk(Object... keyValue)} 
 * para facilitar a inicializacao de um objeto.<br>
 * Eh necessario carregar um {@code ValueObject}.
 * </p>
 * 
 * <pre>
 * <code>
 * 	public classe MinhaClasse implements EntityBean, LoadableByRecord {
 * 		private BigDecimal identificador;
 * 		private String descricao;
 * 
 * 		#@override
 * 		public void loadByPk(Object... keyValue) throws EntityBeanExceptions.RecordNotFoundException{
 * 			JapeWrapper dao = JapeFactory.dao("Instancia");
 * 			DynamicVO vo = dao.findByPK(Object... keyValue);
 * 
 * 			this.identificador = vo.asBigDecimal("IDENTIFICADOR");
 * 			this.descricao = vo.asString("DESCRICAO");
 * 		}
 * 	}
 * </code>
 * </pre>
 * 
 * @author cassiomenezes
 * @since 2022-06-29
 * 
 * @see EntityBean
 * @see JapeWrapper
 * @see JapeFactory
 * @see JapeFactory#dao(String)
 * @see DynamicVOPojo
 * 
 */
public interface LoadableByPrimaryKey {

	/**
	 * Inicializa o objeto a partir de um registro do banco de dados.
	 * 
	 * <p>
	 * A partir da chave primaria buscar o registro no banco de dados para
	 * inicializar os campos do objeto.
	 * </p>
	 * 
	 * @param keyValue Lista com valores de chave primaria.
	 * @throws RecordNotFoundException quando o registro nao for localizado.
	 * 
	 * @see DynamicVOPojo
	 * 
	 */
	void loadByPk(Object... keyValue) throws RecordNotFoundException;

}

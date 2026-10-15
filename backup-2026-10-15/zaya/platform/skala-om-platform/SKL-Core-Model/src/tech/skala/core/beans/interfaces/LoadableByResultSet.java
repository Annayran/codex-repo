package tech.skala.core.beans.interfaces;

import java.sql.ResultSet;

import br.com.sankhya.jape.sql.NativeSql;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * Inicializa o objeto a partir de um registro do banco de dados.
 * 
 * <p>
 * Esta interface exige a implementacao do metodo {@code loadByResultSet(ResultSet rs)} 
 * para facilitar a inicializacao de um objeto.<br>
 * Normalmente este metodo e executado dentro de um laco {@code while(rs.next())}.
 * </p>
 * 
 * <pre>
 * <code>
 * 	public classe MinhaClasse implements EntityBean, LoadableByRecord {
 * 		private BigDecimal identificador;
 * 		private String descricao;
 * 
 * 		#@override
 * 		public void loadByResultSet(ResultSet rs) throws EntityBeanExceptions.RecordNotFoundException{
 * 			this.identificador = rs.getBigDecimal("IDENTIFICADOR");
 * 			this.descricao = rs.getString("DESCRICAO");
 * 		}
 * 	}
 * </code>
 * </pre>
 * 
 * @author cassiomenezes
 * @since 2022-07-01
 * 
 */
public interface LoadableByResultSet {

	/**
	 * Inicializa o objeto a partir de um {@link ResultSet}.
	 * 
	 * <p>
	 * Inicializar os campos do objeto a partir de uma consulta de banco de dados 
	 * utilizando um {@link ResultSet} já definido.<br>
	 * 
	 * @param rs <b>ResultSet</b> definido externamente.
	 * @throws RecordNotFoundException quando o registro nao for localizado.
	 * 
	 * @see ResultSet
	 * @see NativeSql
	 * 
	 */
	void loadByResultSet(ResultSet rs) throws RecordNotFoundException;
	
}

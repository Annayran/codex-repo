package tech.skala.core.beans.interfaces;

import br.com.sankhya.jape.sql.NativeSql;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * Inicializa o objeto a partir de uma consulta de banco de dados.
 * 
 * <p>
 * Esta interface exige a implementacao do metodo {@code loadBySql(NativeSql sql)} 
 * para facilitar a inicializacao de um objeto.<br>
 * Recomendavel a utilizacao conjunta da interface {@link LoadableByResultSet} com 
 * implementacao do metodo {@link LoadableByResultSet#loadByResultSet(java.sql.ResultSet)}
 * </p>
 * 
 * <pre>
 * <code>
 * 	public classe MinhaClasse implements EntityBean, LoadableByRecord {
 * 		private BigDecimal identificador;
 * 		private String descricao;
 * 
 * 		#@override
 * 		public void loadBySql(NativeSql sql) throws EntityBeanExceptions.RecordNotFoundException{
 * 			ResultSet rs = sql.executeQuery();
 * 			while (rs.next()) {
 * 				loadByResultSet(rs);
 * 			}
 * 		}
 * 	}
 * </code>
 * </pre>
 * 
 * @author cassiomenezes
 * @since 2022-06-29
 * 
 */
public interface LoadableBySql {

	/**
	 * Inicializa o objeto a partir de uma consulta de banco de dados.
	 * 
	 * <p>
	 * Inicializar os campos do objeto a partir de uma consulta de banco de dados 
	 * utilizando as classes {@link NativeSql}.<br>
	 * 
	 * @param sql Recebe um {@link NativeSql} valido definido externamento.
	 * @throws RecordNotFoundException quando o registro nao for localizado.
	 * 
	 * @see NativeSql
	 * 
	 */
	void loadBySql(NativeSql sql) throws RecordNotFoundException;
}

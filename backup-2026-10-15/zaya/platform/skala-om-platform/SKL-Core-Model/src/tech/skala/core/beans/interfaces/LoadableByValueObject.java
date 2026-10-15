package tech.skala.core.beans.interfaces;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.DynamicVOPojo;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * Inicializa o objeto a partir de um registro do banco de dados.
 * 
 * <p>
 * Esta interface exige a implementacao do metodo {@code loadByVO(Dynamic vo)} 
 * para facilitar a inicializacao de um objeto utilizando um {@link DynamicVO}.
 * </p>
 * 
 * <pre>
 * <code>
 * 	public classe MinhaClasse implements EntityBean, LoadableByValueObject {
 * 		private BigDecimal identificador;
 * 		private String descricao;
 * 
 * 		#@override
 * 		public void loadByVO(DynamicVO vo) throws EntityBeanExceptions.RecordNotFoundException{
 * 			this.identificador = vo.asBigDecimal("IDENTIFICADOR");
 * 			this.descricao = vo.asString("DESCRICAO");
 * 		}
 * 	}
 * </code>
 * </pre>
 * 
 * @author marcosdamasceno
 * @since 2022-07-05
 * 
 * @see EntityBean
 * @see DynamicVOPojo
 * @see BigDecimalUtil
 * 
 */
public interface LoadableByValueObject {
	/**
	 * Inicializa o objeto a partir de um {@link DynamicVO}.
	 * 
	 * <p>
	 * Inicializa os campos do objeto a partir de um ValueObject válido.
	 * </p>
	 * 
	 * @param vo Representa um ValueObject. 
	 * @throws RecordNotFoundException quando o registro nao for localizado.
	 * 
	 */
	void loadByVO(DynamicVO vo) throws RecordNotFoundException;

}

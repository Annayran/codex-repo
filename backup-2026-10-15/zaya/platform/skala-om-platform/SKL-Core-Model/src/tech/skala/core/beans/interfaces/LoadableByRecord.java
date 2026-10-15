package tech.skala.core.beans.interfaces;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * Inicializa o objeto a partir de um registro do banco de dados.
 * 
 * <p>
 * Esta interface exige a implementacao do metodo {@code loadByRecord(Registro linha)} 
 * para facilitar a inicializacao de um objeto utilizando um {@link Registro}.
 * </p>
 * 
 * <pre>
 * <code>
 * 	public classe MinhaClasse implements EntityBean, LoadableByRecord {
 * 		private BigDecimal identificador;
 * 		private String descricao;
 * 
 * 		#@override
 * 		public void loadByRecord(Registro linha) throws EntityBeanExceptions.RecordNotFoundException{
 * 			this.identificador = BigDecimalUtil.getBigDecimal(linha.getCampo("IDENTIFICADOR"));
 * 			this.descricao = (String) linha.getCampo("DESCRICAO");
 * 		}
 * 	}
 * </code>
 * </pre>
 * 
 * @author cassiomenezes
 * @since 2022-07-01
 * 
 * @see EntityBean
 * @see Registro
 * @see BigDecimalUtil
 * 
 */
public interface LoadableByRecord {

	/**
	 * Inicializa o objeto a partir de um {@link Registro} de tela.
	 * 
	 * <p>
	 * Inicializa os campos do objeto a partir de uma linha de registro selecionada 
	 * pelo usuário.
	 * </p>
	 * 
	 * @param linha Representa um linha de grade selecionada em uma tela.
	 * @throws RecordNotFoundException quando o registro nao for localizado.
	 * 
	 */
	void loadByRecord(Registro linha) throws RecordNotFoundException;

}

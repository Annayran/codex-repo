package tech.skala.core.util.string;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

/**
 * Classe utilitaria com ferramentas para texto.
 * 
 * <p>
 * A classe {@code SkalaStringUtils} contem metodos utilitarios para manipulacao 
 * de textos.
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-06-09
 *
 */
public final class SkalaStringUtils {

	private SkalaStringUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
	 * Retorna caracter especial de quebra de linha conforme o sistema operacional.
	 * 
	 * <p>
	 * Utilizar este metodo para obter o caracter especial de quebra de linha conforme o 
	 * sistema operacional. ("\n" para Linux).<br>
	 * <pre>
	 * <code>
	 * 	...
	 * 	StringBuilder sbMessage = new StringBuilder();
	 * 	sbMessage.append(message);
	 * 	sbMessage.append(SkalaStringUtils.lineSeparator());
	 * 	...
	 * </pre>
	 * </code>
	 * 
	 * @return String com caracter especial de quebra de linha conforme o sistema operacional.
	 */
	public static final String lineSeparator() {
		return System.getProperty("line.separator");
	}
	
	public static final String appendWithSeparator(String thisMsg, String newMsg) {
		StringBuilder sb = new StringBuilder(thisMsg);
		sb.append(lineSeparator());
		sb.append(newMsg);
		
		return sb.toString();
	}
	
	public static final BigDecimal getNumeroNotaFromChaveNfe(String chaveNfe) {
		if (chaveNfe == null)
			return BigDecimal.ZERO;
		
		return BigDecimalUtil.getBigDecimal(chaveNfe.substring(25, 34));
	}
	
	public static final String getCnpjFromChaveNfe(String chaveNfe) {
		if (chaveNfe == null)
			return null;
		
		return chaveNfe.substring(6, 20);
	}

}

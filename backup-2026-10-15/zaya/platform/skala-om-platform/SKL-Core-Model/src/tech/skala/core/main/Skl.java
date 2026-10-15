package tech.skala.core.main;

/**
 * A classe {@code Skl} e a classe principal do modulo {@code Core}.
 * 
 * <p>
 * A classe {@code Skl} e a classe principal do modulo {@code Core} e 
 * contem metodos comuns ao ambiente.
 * </p>
 * <p>
 * Para o versionamento de modulos vamos trabalhar com o Versionamento Semantico 
 * mesclando com o Versionamento por Calendario.
 * </p>
 * <p>
 * Vamos utilizar {@code MAJOR.MINOR.PATCH} conforme Versionamento Semantico.<br>
 * <b>MAJOR</b>: Versao.<br>
 * <b>MINOR</b>: Release.<br>
 * <b>PATCH</b>: Numeracao sequencial para cada build.<br>
 * </p>
 * <p> 
 * Baseado na convencao utilizada por alguns dos sabores do Linux como 
 * referencia, adotamos a seguinte padronizacao seguindo o Versionamento por Calendario:<br>
 * <b>MAJOR</b>: Versao. <b>Ano com dois digitos e zeros aa esquerda</b><br>
 * <b>MINOR</b>: Release. <b>Mes com dois digitos e zeros aa esquerda</b><br>
 * <br>
 * 0Y - Zero-padded year - 06, 16, 106<br>
 * 0M - Zero-padded month - 01, 02... 11, 12<br>
 * </p>
 * <p>
 * O formato de apresentacao sera o seguinte: {@code 0Y.0Mb009}.
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-05-29
 * 
 * @see <i><a href="https://semver.org/spec/v2.0.0.html">Semantic Versioning 2.0.0</a></i>
 * @see <i><a href="https://calver.org/">Calendar Versioning</a></i>
 * @see tech.skala.core.main.VersionFactory
 * 
 */
public final class Skl {
	private static final int			MAJOR = 23;
	private static final int			MINOR = 1;
	private static final int			PATCH = 43;
	
	private Skl() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Retorna a identificacao de versao com o nome de classe padrao <b>SKL-Core-Model</b>.
	 * 
	 * <p>
	 * Exemplo:<br>
	 * Para versao do mes 07, Julho, do ano de 2022 teremos para um <i>build</i>:
	 * {@code "[SKL-Core-Model 22.07b003]"}.
	 * </p>
	 * 
	 * @return A identificacao de versao com o nome de classe padrao <b>SKL-Core-Model</b>.
	 * 
	 * @see tech.skala.core.main.VersionFactory#getVersion
	 */
	public static String getVersion() {
		return getVersion(null);
	}

	/**
	 * Retorna a identificacao de versao com o nome de classe informado no parametro.
	 * 
	 * <p>
	 * Exemplo:<br>
	 * Para versao do mes 07, Julho, do ano de 2022 teremos para um <i>build</i>:
	 * {@code "[className 22.07b003]"}.
	 * </p>
	 * 
	 * @param className Nome valido de uma classe
	 * @return A identificacao de versao com o nome de classe informado no parametro.
	 * 
	 * @see tech.skala.core.main.VersionFactory#getVersion
	 */
	public static String getVersion(String className) {
		
		return (className == null) ? VersionFactory.getVersion(MAJOR, MINOR, PATCH)
				: VersionFactory.getVersion(className, MAJOR, MINOR, PATCH);
		
	}

}

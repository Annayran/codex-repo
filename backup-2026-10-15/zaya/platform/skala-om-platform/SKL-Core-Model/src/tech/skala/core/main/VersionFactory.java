package tech.skala.core.main;

/**
 * A classe {@code VersionFactory} retorna a versao formatada no padrao 
 * MAJOR.MINOR.PATCH.
 * <p>
 * 
 * A classe {@code VersionFactory} retorna a versao formatada no padrao 
 * MAJOR.MINOR.PATCH conforme Versionamento Semantico:<br>
 * <b>MAJOR</b>: Versao.<br>
 * <b>MINOR</b>: Release.<br>
 * <b>PATCH</b>: Numeracao sequencial para cada build.<br>
 * <p>
 * 
 * O formato de apresentacao sera o seguinte: {@code 9.09b009}.
 * <p>
 * 
 * @author cassiomenezes
 * @since 2022-05-29
 * 
 * @see <i><a href="https://semver.org/spec/v2.0.0.html">Semantic Versioning 2.0.0</a></i>
 * @see <i><a href="https://calver.org/">Calendar Versioning</a></i>
 *
 */
public class VersionFactory {
	private static final String			CLASS_NAME = "SKL-Core-Model";
	private static final String 		MASK = "%1$d.%2$02db%3$03d";

	private VersionFactory() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Retorna a identificacao de versao com o nome de classe padrao <b>SKL-Core-Model</b>.
	 * <p>
	 * 
	 * Exemplo:
	 * <p>
	 * Para versao do mes 07, Julho, do ano de 2022 teremos para um <i>build</i>:
	 * {@code "[SKL-Core-Model 22.07b003]"}.
	 * 
	 * @param major Versao
	 * @param minor Release
	 * @param patch Build (compilacao)
	 * @return A identificacao de versao com o nome de classe padrao <b>SKL-Core-Model</b>.
	 */
	public static String getVersion(int major, int minor, int patch) {
		return getVersion(CLASS_NAME, major, minor, patch);
	}

	/**
	 * Retorna a identificacao de versao com o nome de classe informado no parametro.
	 * <p>
	 * 
	 * Exemplo:
	 * <p>
	 * Para versao do mes 07, Julho, do ano de 2022 teremos para um <i>build</i>:
	 * {@code "[className 22.07b003]"}.
	 * 
	 * @param className Nome valido de uma classe
	 * @param major Versao
	 * @param minor Release
	 * @param patch Build (compilacao)
	 * @return A identificacao de versao com o nome de classe informado no parametro.
	 */
	public static String getVersion(String className, int major, int minor, int patch) {
		return String.format("[%s %s]", className, String.format(MASK, major, minor, patch));
	}

}

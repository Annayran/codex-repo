package tech.skala.core.util.json;

import com.google.gson.JsonElement;

/**
 * Classe utilitaria com ferramentas para o formato json.
 * 
 * <p>
 * A classe {@code SkalaJsonUtils} contem metodos utilitarios para manipulacao
 * de elementos json.
 * </p>
 * 
 * @author hisgoalexandre
 * @since 2022-09-06
 *
 */
public class SkalaJsonUtils {

	private SkalaJsonUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean isJsonElementValid(JsonElement jsonElement) {
		return (jsonElement != null && jsonElement.isJsonPrimitive());
	}
}

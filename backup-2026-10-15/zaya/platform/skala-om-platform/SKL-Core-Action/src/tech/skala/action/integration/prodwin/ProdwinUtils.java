package tech.skala.action.integration.prodwin;

public class ProdwinUtils {

	private ProdwinUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static final String ENDPOINT = "http://prodwin.masterlinebrasil.com.br/";
	public static final String PRODUCTION_ORDER = "ws/Integracao.asmx/ImportarOrdemProducao";

	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String ACCEPT = "text/plain";
	
}

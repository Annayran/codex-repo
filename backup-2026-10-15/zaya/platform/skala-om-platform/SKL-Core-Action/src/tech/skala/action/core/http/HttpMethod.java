package tech.skala.action.core.http;

public enum HttpMethod {

	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE");

	private String value;
	
	private HttpMethod(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}

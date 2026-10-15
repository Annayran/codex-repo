package tech.skala.action.core.http;

import tech.skala.core.beans.interfaces.EntityBean;

/**
 * Classe que representa uma resposta de requisição HTTP.
 * 
 * Esta classe representa a resposta de uma requisição {@code HTTP} englobando o 
 * codigo da resposta e o payload de resposta.
 * 
 * @author hiagoalexandre
 * @author cassiomenezes
 * @since 2023-06-16
 */
public class ApiResponse implements EntityBean {
	private boolean								success;
	private String								response;
	private int									responseCode;
	
	public ApiResponse(boolean success, String response, int responseCode) {
		this.success = success;
		this.response = response;
		this.responseCode = responseCode;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		return String.format("ApiResponse [success=%s, response=%s, responseCode=%s]", success, response, responseCode);
	}
	
}

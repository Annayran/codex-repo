package tech.skala.action.integration.pontoicarus;

import tech.skala.core.beans.interfaces.EntityBean;

public class ApiResponseOld implements EntityBean {
	private boolean								sucess;
	private String								response;
	private int									responseCode;
	
	public ApiResponseOld(boolean sucess, String resonse, int responseCode) {
		this.sucess = sucess;
		this.response = resonse;
		this.responseCode = responseCode;
	}

	public boolean isSucess() {
		return sucess;
	}
	public void setSucess(boolean sucess) {
		this.sucess = sucess;
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
		return String.format("ApiResponseOld [sucess=%s, response=%s, responseCode=%s]", sucess, response, responseCode);
	}
	
}

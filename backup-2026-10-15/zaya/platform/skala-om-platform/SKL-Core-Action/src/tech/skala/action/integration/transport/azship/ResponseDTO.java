package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;

import tech.skala.core.commons.dd.DTO;

public class ResponseDTO implements DTO {
	
	private BigDecimal   							code;
	private String   								message;
	
	public BigDecimal getCode() {
		return code;
	}
	
	public void setCode(BigDecimal code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return String.format("ResponseDTO [code=%s, message=%s]", code, message);
	}
	
}
package tech.skala.action.modules.data.etl;

public class PipelineReturn {
	
	private String 						message;
	private boolean 					success;
	
	public PipelineReturn() {

	}
	
	public PipelineReturn(String message, boolean success) {
		this.message = message;
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return String.format("PipelineReturn [message=%s, success=%s]", message, success);
	}
	
	
}

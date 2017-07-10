package it.zano.shareride.rest.service.base.io;

public class BaseResponse {

	private String statusCode;
	private String statusMessage;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		return "BaseResponse [statusCode=" + statusCode + ", statusMessage=" + statusMessage + "]";
	}


}

package it.zano.shareride.rest.service.exception;

import it.zano.shareride.rest.service.base.io.BaseResponse;

/**
 * @author a.zanotti
 * This is the rest response in case of exceptions. It's the response both in case of:
 * - business exceptions, wrapped by ApplicationException class (not our fault)
 * - technical exceptions, that arrives directly as throwable (our fault)
 */
public class ErrorResponse extends BaseResponse {

	private int errorCode;
	private String developerMessage;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developMessage) {
		this.developerMessage = developMessage;
	}

	@Override
	public String toString() {
		return "ErrorResponse [errorCode=" + errorCode + ", developerMessage=" + developerMessage + ", statusCode=" + getStatusCode()
				+ ", statusMessage=" + getStatusMessage() + "]";
	}


}

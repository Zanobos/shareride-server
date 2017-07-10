package it.zano.shareride.rest.service.exception;

import com.graphhopper.directions.api.client.ApiException;

import it.zano.shareride.utils.Constants;

/**
 * @author a.zanotti
 * This class is thrown in case of unavoidable exceptions in the application
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	private String statusCode;
	private String statusMessage;
	private int errorCode;
	private String developerMessage;

	public ApplicationException(ApiException e, String developerMessage) {
		this.errorCode = e.getCode();
		this.statusMessage = e.getMessage();
		this.statusCode = Constants.StatusCodes.BUSINESS_ERROR;
		this.developerMessage = developerMessage;
	}

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

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

}

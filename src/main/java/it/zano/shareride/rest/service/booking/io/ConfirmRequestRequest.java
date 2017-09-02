package it.zano.shareride.rest.service.booking.io;

import it.zano.shareride.rest.service.base.io.BaseRequest;

public class ConfirmRequestRequest extends BaseRequest {

	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "ConfirmRequest [requestId=" + requestId + "]";
	}

}

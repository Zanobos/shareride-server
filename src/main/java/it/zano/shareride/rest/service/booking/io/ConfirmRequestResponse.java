package it.zano.shareride.rest.service.booking.io;

import it.zano.shareride.rest.service.base.io.BaseResponse;
import it.zano.shareride.utils.EnumStatus;

public class ConfirmRequestResponse extends BaseResponse {

	private String requestId;
	private EnumStatus status;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public EnumStatus getStatus() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ConfirmResponse [requestId=" + requestId + ", status=" + status + "]";
	}

}

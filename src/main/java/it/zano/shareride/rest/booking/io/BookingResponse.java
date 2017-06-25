package it.zano.shareride.rest.booking.io;

import it.zano.shareride.rest.base.io.BaseResponse;
import it.zano.shareride.utils.EnumStatus;

public class BookingResponse extends BaseResponse {

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

}

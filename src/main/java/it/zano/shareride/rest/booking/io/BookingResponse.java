package it.zano.shareride.rest.booking.io;

import it.zano.shareride.rest.base.io.BaseResponse;

public class BookingResponse extends BaseResponse {

	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}

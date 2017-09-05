package it.zano.shareride.rest.service.booking.io;

import it.zano.shareride.rest.service.base.io.BaseRequest;

public class ConfirmRequestRequest extends BaseRequest {

	private String requestId;
	private String routeId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	@Override
	public String toString() {
		return "ConfirmRequestRequest [requestId=" + requestId + ", routeId=" + routeId + "]";
	}

}

package it.zano.shareride.optimization.io;

import it.zano.shareride.utils.EnumStatus;

public class RouteDoabilityResponse {

	private EnumStatus status;
	private String requestId;

	public EnumStatus getStatus() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "RouteDoabilityResponse [status=" + status + ", requestId=" + requestId + "]";
	}
	
	

}

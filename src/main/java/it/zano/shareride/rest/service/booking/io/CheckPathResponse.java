package it.zano.shareride.rest.service.booking.io;

import it.zano.shareride.rest.service.base.io.BaseResponse;
import it.zano.shareride.utils.EnumStatus;

public class CheckPathResponse extends BaseResponse {

	private String requestId;
	private String routeId;
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
	
	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	@Override
	public String toString() {
		return "CheckPathResponse [requestId=" + requestId + ", routeId=" + routeId + ", status=" + status + "]";
	}
	
	

}

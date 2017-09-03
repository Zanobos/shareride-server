package it.zano.shareride.optimization.io;

import java.util.Arrays;
import java.util.List;

import it.zano.shareride.persistence.entities.RouteEntity;
import it.zano.shareride.utils.EnumStatus;

public class RouteDoabilityResponse {

	private EnumStatus status;
	private String requestId;
	
	private List<RouteEntity> routes;

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

	public List<RouteEntity> getRoutes() {
		return routes;
	}

	public void setRoutes(List<RouteEntity> routes) {
		this.routes = routes;
	}

	@Override
	public String toString() {
		return "RouteDoabilityResponse [status=" + status + ", requestId=" + requestId + ", routes=" + Arrays.toString(routes.toArray()) + "]";
	}
	
	

}

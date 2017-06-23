package it.zano.shareride.optimization.io;

import java.util.List;

import it.zano.shareride.persistence.entities.TransportEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;

public class RouteDoabilityRequest {

	private List<UserRequestEntity> requests;
	private List<TransportEntity> availableTransports;

	public List<UserRequestEntity> getRequests() {
		return requests;
	}

	public void setRequests(List<UserRequestEntity> requests) {
		this.requests = requests;
	}

	public List<TransportEntity> getAvailableTransports() {
		return availableTransports;
	}

	public void setAvailableTransports(List<TransportEntity> availableTransports) {
		this.availableTransports = availableTransports;
	}

}

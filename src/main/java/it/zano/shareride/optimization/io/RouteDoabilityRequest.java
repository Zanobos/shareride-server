package it.zano.shareride.optimization.io;

import java.util.Arrays;
import java.util.List;

import it.zano.shareride.persistence.entities.VehicleEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;

public class RouteDoabilityRequest {

	private List<UserRequestEntity> requests;
	private List<VehicleEntity> availableTransports;

	public List<UserRequestEntity> getRequests() {
		return requests;
	}

	public void setRequests(List<UserRequestEntity> requests) {
		this.requests = requests;
	}

	public List<VehicleEntity> getAvailableTransports() {
		return availableTransports;
	}

	public void setAvailableTransports(List<VehicleEntity> availableTransports) {
		this.availableTransports = availableTransports;
	}

	@Override
	public String toString() {
		return "RouteDoabilityRequest [requests=" + Arrays.toString(requests.toArray()) + ", availableTransports=" + Arrays.toString(availableTransports.toArray()) + "]";
	}
	
}

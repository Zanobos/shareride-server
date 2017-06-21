package it.zano.shareride.optimization;

import java.util.List;

import it.zano.shareride.base.model.Transport;
import it.zano.shareride.booking.entities.BookingRequest;

public class RouteDoabilityRequest {

	private List<BookingRequest> requests;
	private List<Transport> availableTransports;

	public List<BookingRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<BookingRequest> requests) {
		this.requests = requests;
	}

	public List<Transport> getAvailableTransports() {
		return availableTransports;
	}

	public void setAvailableTransports(List<Transport> availableTransports) {
		this.availableTransports = availableTransports;
	}

}

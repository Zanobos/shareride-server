package it.zano.shareride.persistence;

import java.util.List;

import it.zano.shareride.base.model.Transport;
import it.zano.shareride.booking.entities.BookingRequest;
import it.zano.shareride.optimization.RouteDoabilityResponse;

public class PersistenceController {

	/**
	 * I load from the db the previous requests
	 * @param input
	 * @return
	 */
	public List<BookingRequest> loadPreviousRequests(AreaTimeInput input) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Saving a new request
	 * @param newRequest
	 * @param doabilityResponse
	 */
	public void saveNewRequest(BookingRequest newRequest, RouteDoabilityResponse doabilityResponse) {
		// TODO Auto-generated method stub
		
	}

	public List<Transport> loadAvailableTransports(AreaTimeInput input) {
		// TODO Auto-generated method stub
		return null;
	}

}

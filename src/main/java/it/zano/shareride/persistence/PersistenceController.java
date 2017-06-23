package it.zano.shareride.persistence;

import java.util.List;

import it.zano.shareride.optimization.io.RouteDoabilityResponse;
import it.zano.shareride.persistence.entities.TransportEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.persistence.io.AreaTimeInput;

public class PersistenceController {

	/**
	 * I load from the db the previous requests
	 * @param input
	 * @return
	 */
	public List<UserRequestEntity> loadPreviousRequests(AreaTimeInput input) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Saving a new request
	 * @param newRequest
	 * @param doabilityResponse
	 */
	public void saveNewRequest(UserRequestEntity newRequest, RouteDoabilityResponse doabilityResponse) {
		// TODO Auto-generated method stub
		
	}

	public List<TransportEntity> loadAvailableTransports(AreaTimeInput input) {
		// TODO Auto-generated method stub
		return null;
	}

}

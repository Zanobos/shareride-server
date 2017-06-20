package it.zano.shareride.booking.service.utils;

import it.zano.shareride.booking.entities.BookingRequest;
import it.zano.shareride.booking.entities.BookingResponse;
import it.zano.shareride.optimization.RouteDoabilityResponse;
import it.zano.shareride.persistence.PersistenceInput;

public class BookingEntitiesController {

	private BookingRequest m_bookingRequest;
	
	/**
	 * @return the object with the day and the area of this request, in order to be able to look for the others request already inserted
	 */
	public PersistenceInput getPersistenceInput() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the request just inserted, enriching as needed (for example, maybe I have to geolocate lat and lon)
	 */
	public BookingRequest getNewRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	public BookingResponse createResponse(RouteDoabilityResponse doabilityResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setBookingRequest(BookingRequest bookingRequest) {
		// TODO Auto-generated method stub
		
	}

}

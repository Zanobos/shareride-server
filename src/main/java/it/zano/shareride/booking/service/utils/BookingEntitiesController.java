package it.zano.shareride.booking.service.utils;

import java.util.Date;

import org.joda.time.DateTime;

import it.zano.shareride.base.model.Location;
import it.zano.shareride.booking.entities.BookingRequest;
import it.zano.shareride.booking.entities.BookingResponse;
import it.zano.shareride.geocoding.ConvertAddressRequest;
import it.zano.shareride.geocoding.ConvertAddressResponse;
import it.zano.shareride.geocoding.GeocodingController;
import it.zano.shareride.optimization.RouteDoabilityResponse;
import it.zano.shareride.persistence.PreviousRequestInput;

public class BookingEntitiesController {

	private BookingRequest m_bookingRequest;
	
	/**
	 * @return the object with the day and the area of this request, in order to be able to look for the others request already inserted
	 */
	public PreviousRequestInput getPreviousRequestInput() {
		
		PreviousRequestInput previousRequestInput = new PreviousRequestInput();
		
		String areaId = m_bookingRequest.getAdditionalInfo().getAreaId();
		
		Date date = m_bookingRequest.getDelivery().getTime();
		if(date == null) {
			date = m_bookingRequest.getPickup().getTime();
		}
		
		DateTime dateTime = null;
		if(date != null) {
			dateTime = new DateTime(date.getTime());
		}
		previousRequestInput.setAreaId(areaId);
		previousRequestInput.setDateTime(dateTime);
		return previousRequestInput;
	}

	/**
	 * @return the request just inserted, enriching as needed (for example, maybe I have to geolocate lat and lon)
	 */
	public BookingRequest getNewRequest() {
		
		enrichLocation(m_bookingRequest.getDelivery());
		enrichLocation(m_bookingRequest.getPickup());
		
		return m_bookingRequest;
	}

	private void enrichLocation(Location location) {
		
		GeocodingController geocodingController = new GeocodingController();
		
		if(location.getLat() == null || location.getLon() == null) {
			
			ConvertAddressRequest convertAddressRequest = new ConvertAddressRequest();
			convertAddressRequest.setAddress(location.getAddress());
			
			ConvertAddressResponse convertAddressResponse = geocodingController.convertAddress(convertAddressRequest);
			
			location.setLat(convertAddressResponse.getLat());
			location.setLon(convertAddressResponse.getLon());
			
		}
		
	}

	public BookingResponse createResponse(RouteDoabilityResponse doabilityResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setBookingRequest(BookingRequest bookingRequest) {
		m_bookingRequest = bookingRequest;
	}

}

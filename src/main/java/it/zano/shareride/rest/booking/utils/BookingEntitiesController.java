package it.zano.shareride.rest.booking.utils;

import org.joda.time.DateTime;

import it.zano.shareride.geocoding.GeocodingController;
import it.zano.shareride.geocoding.io.ConvertAddressRequest;
import it.zano.shareride.geocoding.io.ConvertAddressResponse;
import it.zano.shareride.optimization.io.RouteDoabilityResponse;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.persistence.io.AreaTimeInput;
import it.zano.shareride.rest.booking.entities.Location;
import it.zano.shareride.rest.booking.io.BookingRequest;
import it.zano.shareride.rest.booking.io.BookingResponse;

public class BookingEntitiesController {

	private BookingRequest m_bookingRequest;
	
	/**
	 * @return the object with the day and the area of this request, in order to be able to look for the other requests already inserted
	 */
	public AreaTimeInput getAreaTimeInput() {
		
		AreaTimeInput previousRequestInput = new AreaTimeInput();
		
		String areaId = m_bookingRequest.getAdditionalInfo().getAreaId();
		
		Long date = m_bookingRequest.getDelivery().getTime();
		if(date == null) {
			date = m_bookingRequest.getPickup().getTime();
		}
		
		DateTime dateTime = null;
		if(date != null) {
			dateTime = new DateTime(date);
		}
		previousRequestInput.setAreaId(areaId);
		previousRequestInput.setDateTime(dateTime);
		return previousRequestInput;
	}

	/**
	 * @return the request just inserted, enriching as needed (for example, maybe I have to geolocate lat and lon)
	 */
	public UserRequestEntity getNewRequest() {
		
		enrichLocation(m_bookingRequest.getDelivery());
		enrichLocation(m_bookingRequest.getPickup());
		
		return null; //TODO
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

package it.zano.shareride.rest.booking.utils;

import org.joda.time.DateTime;

import it.zano.shareride.geocoding.GeocodingController;
import it.zano.shareride.geocoding.io.ConvertAddressRequest;
import it.zano.shareride.geocoding.io.ConvertAddressResponse;
import it.zano.shareride.optimization.io.RouteDoabilityResponse;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.rest.booking.entities.Location;
import it.zano.shareride.rest.booking.io.BookingRequest;
import it.zano.shareride.rest.booking.io.BookingResponse;

public class BookingServiceUtils {

	
	public static DateTime getDateTime(BookingRequest bookingRequest) {
		
		Long date = bookingRequest.getDelivery().getTime();
		if(date == null) {
			date = bookingRequest.getPickup().getTime();
		}
		
		DateTime dateTime = null;
		if(date != null) {
			dateTime = new DateTime(date);
		}
		return dateTime;
	}

	/**
	 * @return the request just inserted, enriching as needed (for example, maybe I have to geolocate lat and lon)
	 */
	public static UserRequestEntity convertRequest(BookingRequest bookingRequest) {
		
		enrichLocation(bookingRequest.getDelivery());
		enrichLocation(bookingRequest.getPickup());
		
		return null; //TODO
	}

	private static void enrichLocation(Location location) {
		
		GeocodingController geocodingController = new GeocodingController();
		
		if(location.getLat() == null || location.getLon() == null) {
			
			ConvertAddressRequest convertAddressRequest = new ConvertAddressRequest();
			convertAddressRequest.setAddress(location.getAddress());
			
			ConvertAddressResponse convertAddressResponse = geocodingController.convertAddress(convertAddressRequest);
			
			location.setLat(convertAddressResponse.getLat());
			location.setLon(convertAddressResponse.getLon());
			
		}
		
	}

	public static BookingResponse convertResponse(RouteDoabilityResponse doabilityResponse) {
		// TODO Auto-generated method stub
		return null;
	}

}

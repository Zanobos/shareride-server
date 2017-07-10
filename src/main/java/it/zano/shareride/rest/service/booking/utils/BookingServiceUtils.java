package it.zano.shareride.rest.service.booking.utils;

import org.joda.time.DateTime;

import it.zano.shareride.geocoding.GeocodingController;
import it.zano.shareride.geocoding.io.ConvertAddressRequest;
import it.zano.shareride.geocoding.io.ConvertAddressResponse;
import it.zano.shareride.optimization.io.RouteDoabilityResponse;
import it.zano.shareride.persistence.entities.LocationEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.rest.service.booking.entities.Location;
import it.zano.shareride.rest.service.booking.io.BookingRequest;
import it.zano.shareride.rest.service.booking.io.BookingResponse;
import it.zano.shareride.rest.service.exception.ApplicationException;
import it.zano.shareride.utils.EnumStatus;

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
	 * @throws ApplicationException 
	 */
	public static UserRequestEntity convertRequest(BookingRequest bookingRequest) throws ApplicationException {
		
		UserRequestEntity userRequest = new UserRequestEntity();
		
		enrichLocation(bookingRequest.getDelivery());
		enrichLocation(bookingRequest.getPickup());
		
		userRequest.setAreaId(bookingRequest.getAdditionalInfo().getAreaId());
		userRequest.setDateTime(getDateTime(bookingRequest));
		userRequest.setNeedAssistance(bookingRequest.getAdditionalInfo().getNeedAssistance());
		userRequest.setNumberOfSeats(bookingRequest.getAdditionalInfo().getNumberOfSeats());
		userRequest.setUserName(bookingRequest.getUserInfo().getName());
		userRequest.setDelivery(convertLocation(bookingRequest.getDelivery()));
		userRequest.setPickup(convertLocation(bookingRequest.getPickup()));
		userRequest.setStatus(EnumStatus.TOBEDONE);
		
		return userRequest;
	}

	private static LocationEntity convertLocation(Location location) {
		LocationEntity locationEntity = new LocationEntity();
		
		locationEntity.setAddress(location.getAddress());
		locationEntity.setDateTime(location.getTime() != null ? new DateTime(location.getTime()) : null);
		locationEntity.setLat(location.getLat());
		locationEntity.setLocationName(location.getLocationName());
		locationEntity.setLon(location.getLon());
		
		return locationEntity;
	}

	private static void enrichLocation(Location location) throws ApplicationException {
		
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
		
		BookingResponse response = new BookingResponse();
		
		response.setStatus(doabilityResponse.getStatus());
		response.setRequestId(doabilityResponse.getRequestId());
		
		return response;
	}

}
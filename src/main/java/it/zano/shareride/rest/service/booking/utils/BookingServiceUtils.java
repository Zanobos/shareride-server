package it.zano.shareride.rest.service.booking.utils;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import it.zano.shareride.geocoding.GeocodingController;
import it.zano.shareride.geocoding.io.ConvertAddressRequest;
import it.zano.shareride.geocoding.io.ConvertAddressResponse;
import it.zano.shareride.geocoding.io.ConvertLatLonRequest;
import it.zano.shareride.geocoding.io.ConvertLatLonResponse;
import it.zano.shareride.persistence.entities.LocationEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.rest.service.booking.entities.Location;
import it.zano.shareride.rest.service.booking.io.CheckPathRequest;
import it.zano.shareride.rest.service.exception.ApplicationException;
import it.zano.shareride.utils.EnumStatus;

public class BookingServiceUtils {

	public static LocalDate getDate(CheckPathRequest request) {
		
		LocalDate result = request.getDelivery().getDate();
		if(result == null) {
			result = request.getPickup().getDate();
		}
		
		return result;
	}
	
	public static LocalTime getTime(CheckPathRequest request) {
		
		LocalTime result = request.getDelivery().getTime();
		if(result == null) {
			result = request.getPickup().getTime();
		}
		
		return result;
	}

	/**
	 * @return the request just inserted, enriching as needed (for example, maybe I have to geolocate lat and lon)
	 * @throws ApplicationException 
	 */
	public static UserRequestEntity convertRequest(CheckPathRequest request) throws ApplicationException {
		
		UserRequestEntity userRequest = new UserRequestEntity();
		
		enrichLocation(request.getDelivery());
		enrichLocation(request.getPickup());
		
		userRequest.setAreaId(request.getAdditionalInfo().getAreaId());
		userRequest.setLocalDate(getDate(request));
		userRequest.setLocalTime(getTime(request));
		userRequest.setNeedAssistance(request.getAdditionalInfo().getNeedAssistance());
		userRequest.setNumberOfSeats(request.getAdditionalInfo().getNumberOfSeats());
		userRequest.setUserName(request.getUserInfo().getName());
		userRequest.setDelivery(convertLocation(request.getDelivery()));
		userRequest.setPickup(convertLocation(request.getPickup()));
		userRequest.setStatus(EnumStatus.TOBEDONE);
		
		return userRequest;
	}

	private static LocationEntity convertLocation(Location location) {
		LocationEntity locationEntity = new LocationEntity();
		
		locationEntity.setAddress(location.getAddress());
		locationEntity.setDate(location.getDate());
		locationEntity.setTime(location.getTime());
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
		
		if(location.getLocationName() == null || location.getAddress() == null) {
			ConvertLatLonRequest convertLatLonRequest = new ConvertLatLonRequest();
			convertLatLonRequest.setLat(location.getLat());
			convertLatLonRequest.setLon(location.getLon());
			
			ConvertLatLonResponse convertLatLngResponse = geocodingController.convertLatLng(convertLatLonRequest);
			//TODO use lat and lon of new point, and also send them back because we have found a better position
			location.setAddress(convertLatLngResponse.getAddress());
			location.setLocationName(convertLatLngResponse.getName());
		}
		
	}

}

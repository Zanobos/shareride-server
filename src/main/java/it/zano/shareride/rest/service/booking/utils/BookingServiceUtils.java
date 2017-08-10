package it.zano.shareride.rest.service.booking.utils;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import it.zano.shareride.geocoding.GeocodingController;
import it.zano.shareride.geocoding.io.ConvertAddressRequest;
import it.zano.shareride.geocoding.io.ConvertAddressResponse;
import it.zano.shareride.geocoding.io.ConvertLatLonRequest;
import it.zano.shareride.geocoding.io.ConvertLatLonResponse;
import it.zano.shareride.optimization.io.RouteDoabilityResponse;
import it.zano.shareride.persistence.entities.LocationEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.rest.service.booking.entities.Location;
import it.zano.shareride.rest.service.booking.io.BookingRequest;
import it.zano.shareride.rest.service.booking.io.BookingResponse;
import it.zano.shareride.rest.service.booking.io.CheckPathRequest;
import it.zano.shareride.rest.service.booking.io.CheckPathResponse;
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

	public static LocalDate getDate(BookingRequest request) {
		
		LocalDate result = request.getDelivery().getDate();
		if(result == null) {
			result = request.getPickup().getDate();
		}
		
		return result;
	}
	
	public static LocalTime getTime(BookingRequest request) {
		
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
	public static UserRequestEntity convertRequest(BookingRequest bookingRequest) throws ApplicationException {
		
		UserRequestEntity userRequest = new UserRequestEntity();
		
		enrichLocation(bookingRequest.getDelivery());
		enrichLocation(bookingRequest.getPickup());
		
		userRequest.setAreaId(bookingRequest.getAdditionalInfo().getAreaId());
		userRequest.setLocalDate(getDate(bookingRequest));
		userRequest.setLocalTime(getTime(bookingRequest));
		userRequest.setNeedAssistance(bookingRequest.getAdditionalInfo().getNeedAssistance());
		userRequest.setNumberOfSeats(bookingRequest.getAdditionalInfo().getNumberOfSeats());
		userRequest.setUserName(bookingRequest.getUserInfo().getName());
		userRequest.setDelivery(convertLocation(bookingRequest.getDelivery()));
		userRequest.setPickup(convertLocation(bookingRequest.getPickup()));
		userRequest.setStatus(EnumStatus.TOBEDONE);
		
		return userRequest;
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

	public static BookingResponse convertResponse(RouteDoabilityResponse doabilityResponse) {
		
		BookingResponse response = new BookingResponse();
		
		response.setStatus(doabilityResponse.getStatus());
		response.setRequestId(doabilityResponse.getRequestId());
		
		return response;
	}
	
	public static CheckPathResponse convertCheckPathResponse(RouteDoabilityResponse doabilityResponse) {
		
		CheckPathResponse response = new CheckPathResponse();
		
		response.setStatus(doabilityResponse.getStatus());
		response.setRequestId(doabilityResponse.getRequestId());
		
		return response;
	}

}

package it.zano.shareride.rest.service.booking.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import it.zano.shareride.geocoding.GeocodingController;
import it.zano.shareride.geocoding.io.ConvertAddressRequest;
import it.zano.shareride.geocoding.io.ConvertAddressResponse;
import it.zano.shareride.geocoding.io.ConvertLatLonRequest;
import it.zano.shareride.geocoding.io.ConvertLatLonResponse;
import it.zano.shareride.persistence.entities.BoundingBoxEntity;
import it.zano.shareride.persistence.entities.GeoPointEntity;
import it.zano.shareride.persistence.entities.LocationEntity;
import it.zano.shareride.persistence.entities.RouteEntity;
import it.zano.shareride.persistence.entities.RouteLocationEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.rest.service.booking.entities.BoundingBox;
import it.zano.shareride.rest.service.booking.entities.GeoPoint;
import it.zano.shareride.rest.service.booking.entities.Location;
import it.zano.shareride.rest.service.booking.entities.UserRequest;
import it.zano.shareride.rest.service.booking.io.CheckPathRequest;
import it.zano.shareride.rest.service.exception.ApplicationException;
import it.zano.shareride.utils.EnumRouteStatus;
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
		userRequest.setUserId(request.getUserInfo().getUserId());
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

	private static Location convertLocationEntity(LocationEntity locationEntity) {
		Location location = new Location();
		
		location.setAddress(locationEntity.getAddress());
		location.setDate(locationEntity.getDate());
		location.setLat(locationEntity.getLat());
		location.setLocationName(locationEntity.getLocationName());
		location.setLon(locationEntity.getLon());
		location.setTime(locationEntity.getTime());
		
		return location;
		
	}
	
	private static void enrichLocation(Location location) throws ApplicationException {
		
		GeocodingController geocodingController = new GeocodingController();
		
		if(location.getLat() == null || location.getLon() == null) {
			
			ConvertAddressRequest convertAddressRequest = new ConvertAddressRequest();
			convertAddressRequest.setAddress(location.getAddress());
			
			ConvertAddressResponse convertAddressResponse = geocodingController.convertAddress(convertAddressRequest);
			
			location.setLat(convertAddressResponse.getPoint().getLatitude());
			location.setLon(convertAddressResponse.getPoint().getLongitude());
			
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

	public static Map<String, UserRequest> convertRequestList(List<UserRequestEntity> previousRequests) {
		
		Map<String,UserRequest> requestMap = new HashMap<String,UserRequest>();
		
		for(UserRequestEntity requestEntity : previousRequests){
			UserRequest userRequest = new UserRequest();
			
			userRequest.setRequestId(requestEntity.getId());
			
			LocationEntity delivery = requestEntity.getDelivery();
			Location askedDevilery = convertLocationEntity(delivery);
			userRequest.setAskedDevilery(askedDevilery);
			
			LocationEntity pickup = requestEntity.getPickup();
			Location askedPickup =  convertLocationEntity(pickup);
			userRequest.setAskedPickup(askedPickup);
			
			RouteLocationEntity proposedDevileryEntity = null;
			RouteLocationEntity proposedPickupEntity = null;
			for(RouteEntity route : requestEntity.getRoutes()){
				if(route.getRouteStatus().equals(EnumRouteStatus.PLANNED)) {
					//Getting the right locations
					for(RouteLocationEntity routeLocation : route.getRouteLocations()){
						if(routeLocation.getLocationEntity().getId().equals(delivery.getId())){
							proposedDevileryEntity = routeLocation;
						} else if(routeLocation.getLocationEntity().getId().equals(pickup.getId())){
							proposedPickupEntity = routeLocation;
						}
					}
					userRequest.setPath(new ArrayList<GeoPoint>());
					//Getting the path
					for(GeoPointEntity geoPointEntity : route.getPath()){
						GeoPoint point = convertGeoPointEntity(geoPointEntity);
						userRequest.getPath().add(point);
					}
					// putting in the right position the points
					Collections.sort(userRequest.getPath(), new Comparator<GeoPoint>() {
						@Override
						public int compare(GeoPoint point1, GeoPoint point2) {
							return point1.getPosition().compareTo(point2.getPosition());
						}
					});
					//Getting the bounding box
					BoundingBoxEntity boundingBoxEntity = route.getBoundingBox();
					BoundingBox boundingBox = new BoundingBox();
					boundingBox.setMinimum(convertGeoPointEntity(boundingBoxEntity.getMinimum()));
					boundingBox.setMaximum(convertGeoPointEntity(boundingBoxEntity.getMaximum()));
					userRequest.setBoundingBox(boundingBox);
					
				}
			}
			
			Location proposedDevilery = convertRouteLocationEntity(proposedDevileryEntity);
			Location proposedPickup = convertRouteLocationEntity(proposedPickupEntity);
			userRequest.setProposedDevilery(proposedDevilery);
			userRequest.setProposedPickup(proposedPickup);
			
			requestMap.put(userRequest.getRequestId(), userRequest);
		}
		
		return requestMap;
	}

	private static GeoPoint convertGeoPointEntity(GeoPointEntity geoPointEntity) {
		GeoPoint geoPoint = new GeoPoint();
		
		geoPoint.setPosition(geoPointEntity.getPosition());
		geoPoint.setLatitude(geoPointEntity.getLatitude());
		geoPoint.setLongitude(geoPointEntity.getLongitude());
		
		return geoPoint;
	}
	
	private static Location convertRouteLocationEntity(RouteLocationEntity routeLocationEntity) {
		
		Location location = new Location();
		
		LocationEntity locationEntity = routeLocationEntity.getLocationEntity();
		
		location.setAddress(locationEntity.getAddress());
		location.setDate(locationEntity.getDate());
		location.setLat(locationEntity.getLat());
		location.setLocationName(locationEntity.getLocationName());
		location.setLon(locationEntity.getLon());
		location.setTime(routeLocationEntity.getArrivalTime());
		
		return location;
	}

}

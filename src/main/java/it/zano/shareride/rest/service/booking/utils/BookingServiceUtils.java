package it.zano.shareride.rest.service.booking.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import it.zano.shareride.persistence.entities.WayPointEntity;
import it.zano.shareride.rest.service.booking.entities.BoundingBox;
import it.zano.shareride.rest.service.booking.entities.GeoPoint;
import it.zano.shareride.rest.service.booking.entities.Location;
import it.zano.shareride.rest.service.booking.entities.UserRequest;
import it.zano.shareride.rest.service.booking.io.CheckPathRequest;
import it.zano.shareride.rest.service.exception.ApplicationException;
import it.zano.shareride.utils.EnumRouteLocationType;
import it.zano.shareride.utils.EnumRouteStatus;
import it.zano.shareride.utils.EnumStatus;

public class BookingServiceUtils {
	
	private static final int MAX_ROUTE_DURATION_MINUTES = 30;

	private BookingServiceUtils() {
		//Impossible to instantiate
	}
	
	/**
	 * @return the request just inserted, enriching as needed (for example, maybe I have to geolocate lat and lon)
	 * @throws ApplicationException 
	 */
	public static UserRequestEntity convertRequest(CheckPathRequest request) throws ApplicationException {
		
		UserRequestEntity userRequest = new UserRequestEntity();
		
		//TODO the FE should pass also the time not selected?
		setMissingTime(request);
		
		enrichLocation(request.getDelivery());
		enrichLocation(request.getPickup());
		
		userRequest.setAreaId(request.getAdditionalInfo().getAreaId());
		userRequest.setLocalDate(LocalDate.now());
		userRequest.setLocalTime(LocalTime.now());
		userRequest.setNeedAssistance(request.getAdditionalInfo().getNeedAssistance());
		userRequest.setNumberOfSeats(request.getAdditionalInfo().getNumberOfSeats());
		userRequest.setUserName(request.getUserInfo().getName());
		userRequest.setUserId(request.getUserInfo().getUserId());
		userRequest.setDelivery(convertLocation(request.getDelivery()));
		userRequest.setPickup(convertLocation(request.getPickup()));
		userRequest.setStatus(EnumStatus.TOBEDONE);
		
		return userRequest;
	}

	private static void setMissingTime(CheckPathRequest request) {
		
		LocalTime deliveryTime = request.getDelivery().getTime();
		LocalTime pickupTime = request.getPickup().getTime();
		
		if(pickupTime == null && deliveryTime != null) {
			pickupTime = deliveryTime.minusMinutes(MAX_ROUTE_DURATION_MINUTES);
			request.getPickup().setTime(pickupTime);
		}
		
		if(pickupTime != null && deliveryTime == null) {
			deliveryTime = pickupTime.plusMinutes(MAX_ROUTE_DURATION_MINUTES);
			request.getDelivery().setTime(deliveryTime);
		}
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
		
		Map<String,UserRequest> requestMap = new HashMap<>();
		
		for(UserRequestEntity requestEntity : previousRequests){
			UserRequest userRequest = new UserRequest();
			
			String requestId = requestEntity.getId();
			userRequest.setRequestId(requestId);
			
			LocationEntity delivery = requestEntity.getDelivery();
			Location askedDevilery = convertLocationEntity(delivery);
			userRequest.setAskedDelivery(askedDevilery);
			
			LocationEntity pickup = requestEntity.getPickup();
			Location askedPickup =  convertLocationEntity(pickup);
			userRequest.setAskedPickup(askedPickup);
			
			RouteLocationEntity proposedDevileryEntity = null;
			RouteLocationEntity proposedPickupEntity = null;
			for(RouteEntity route : requestEntity.getRoutes()){
				if(route.getRouteStatus().equals(EnumRouteStatus.PLANNED)) {
					
					//Getting the waypoints for further use
					Set<WayPointEntity> waypoints = route.getWayPoints();
					
					//Getting the right locations
					for(RouteLocationEntity routeLocation : route.getRouteLocations()){
						if(routeLocation.getLocationEntity().getId().equals(delivery.getId())){
							proposedDevileryEntity = routeLocation;
						} else if(routeLocation.getLocationEntity().getId().equals(pickup.getId())){
							proposedPickupEntity = routeLocation;
						}
					}
					
					//Getting the path
					//I assume the interested path is continue between pickup and delivery
					Integer positionPickup = null;
					Integer positionDelivery = null;
					userRequest.setPath(new ArrayList<GeoPoint>());
					for(GeoPointEntity geoPointEntity : route.getPath()){
						GeoPoint point = convertGeoPointEntity(geoPointEntity);
						//A loop over the waypoints in order to add infos to the points
						for(WayPointEntity waypoint : waypoints) {
							if(waypoint.getGeoPoint().equals(geoPointEntity)){
								if(point.getType() == null){
									point.setType(waypoint.getLocationType());
								}
								//If the request id in the waypoint is the same of this request, it's a point of interest
								if(waypoint.getRequestId().equals(requestId) && waypoint.getLocationType().equals(EnumRouteLocationType.PICKUPSHIPMENT)){
									positionPickup = point.getPosition();
								}
								else if(waypoint.getRequestId().equals(requestId) && waypoint.getLocationType().equals(EnumRouteLocationType.DELIVERSHIPMENT)){
									positionDelivery = point.getPosition();
								}
							}
						}
						userRequest.getPath().add(point);
					}
					
					// putting in the right position the points
					Collections.sort(userRequest.getPath(), new Comparator<GeoPoint>() {
						@Override
						public int compare(GeoPoint point1, GeoPoint point2) {
							return point1.getPosition().compareTo(point2.getPosition());
						}
					});
					
					//I mark the part of the path I will be on the bus
					if(positionPickup != null && positionDelivery != null) {
						for(GeoPoint geoPoint : userRequest.getPath()){
							if(geoPoint.getPosition() >= positionPickup && geoPoint.getPosition() <= positionDelivery) {
								geoPoint.setUserPath(true);
							}
						}
					}
					
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
			userRequest.setProposedDelivery(proposedDevilery);
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
		location.setTime(routeLocationEntity.getEndTime());
		
		return location;
	}

}

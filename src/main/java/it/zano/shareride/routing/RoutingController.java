package it.zano.shareride.routing;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.RoutingApi;
import com.graphhopper.directions.api.client.model.ResponseCoordinatesArray;
import com.graphhopper.directions.api.client.model.RouteResponse;
import com.graphhopper.directions.api.client.model.RouteResponsePath;

import it.zano.shareride.persistence.entities.BoundingBoxEntity;
import it.zano.shareride.persistence.entities.GeoPointEntity;
import it.zano.shareride.persistence.entities.RouteLocationEntity;
import it.zano.shareride.persistence.entities.WayPointEntity;
import it.zano.shareride.rest.service.exception.ApplicationException;
import it.zano.shareride.routing.io.RoutingRequest;
import it.zano.shareride.routing.io.RoutingResponse;
import it.zano.shareride.utils.PropertiesLoader;

public class RoutingController {
	
	private static final Logger log = Logger.getLogger(RoutingController.class.getName());
	
	private static final String SEPARATOR = ",";
	
	public RoutingResponse calculateRoutePath(RoutingRequest routingRequest) throws ApplicationException {
		
		log.log(Level.INFO, "INPUT:<<" + routingRequest.toString() + ">>");
		
		RoutingResponse routingResponse = null;
		
		RoutingApi routingApi = new RoutingApi();
		
		List<RouteLocationEntity> routeLocations = routingRequest.getRouteLocations();
		
		List<String> points = convertPoints(routeLocations);
		Boolean pointsEncoded = PropertiesLoader.getPropertyBoolean("routing.pointsEncoded"); // Boolean | IMPORTANT- currently you have to pass false for the swagger client - Have not found a way to force add a parameter. If `false` the coordinates in `point` and `snapped_waypoints` are returned as array using the order [lon,lat,elevation] for every point. If `true` the coordinates will be encoded as string leading to less bandwith usage. You'll need a special handling for the decoding of this string on the client-side. We provide open source code in [Java](https://github.com/graphhopper/graphhopper/blob/d70b63660ac5200b03c38ba3406b8f93976628a6/web/src/main/java/com/graphhopper/http/WebHelper.java#L43) and [JavaScript](https://github.com/graphhopper/graphhopper/blob/d70b63660ac5200b03c38ba3406b8f93976628a6/web/src/main/webapp/js/ghrequest.js#L139). It is especially important to use no 3rd party client if you set `elevation=true`!
		String key = PropertiesLoader.getProperty("graphopper.key");
		String locale = PropertiesLoader.getProperty("routing.locale");
		Boolean instructions = PropertiesLoader.getPropertyBoolean("routing.instruction");
		Boolean elevation = PropertiesLoader.getPropertyBoolean("routing.elevation");
		Boolean calcPoints = PropertiesLoader.getPropertyBoolean("routing.calcPoints");
		
		String vehicle = null; // String | The vehicle for which the route should be calculated. Other vehicles are foot, small_truck, ...
		List<String> pointHint = null; // List<String> | Optional parameter. Specifies a hint for each `point` parameter to prefer a certain street for the closest location lookup. E.g. if there is an address or house with two or more neighboring streets you can control for which street the closest location is looked up.
		Boolean chDisable = null; // Boolean | Use this parameter in combination with one or more parameters of this table
		String weighting = null; // String | Which kind of 'best' route calculation you need. Other option is `shortest` (e.g. for `vehicle=foot` or `bike`), `short_fastest` if time and distance is expensive e.g. for `vehicle=truck`
		Boolean edgeTraversal = null; // Boolean | Use `true` if you want to consider turn restrictions for bike and motor vehicles. Keep in mind that the response time is roughly 2 times slower.
		String algorithm = null; // String | The algorithm to calculate the route. Other options are `dijkstra`, `astar`, `astarbi`, `alternative_route` and `round_trip`
		Integer heading = null; // Integer | Favour a heading direction for a certain point. Specify either one heading for the start point or as many as there are points. In this case headings are associated by their order to the specific points. Headings are given as north based clockwise angle between 0 and 360 degree. This parameter also influences the tour generated with `algorithm=round_trip` and force the initial direction.
		Integer headingPenalty = null; // Integer | Penalty for omitting a specified heading. The penalty corresponds to the accepted time delay in seconds in comparison to the route without a heading.
		Boolean passThrough = null; // Boolean | If `true` u-turns are avoided at via-points with regard to the `heading_penalty`.
		Integer roundTripDistance = null; // Integer | If `algorithm=round_trip` this parameter configures approximative length of the resulting round trip
		Long roundTripSeed = null; // Long | If `algorithm=round_trip` this parameter introduces randomness if e.g. the first try wasn't good.
		Integer alternativeRouteMaxPaths = null; // Integer | If `algorithm=alternative_route` this parameter sets the number of maximum paths which should be calculated. Increasing can lead to worse alternatives.
		Integer alternativeRouteMaxWeightFactor = null; // Integer | If `algorithm=alternative_route` this parameter sets the factor by which the alternatives routes can be longer than the optimal route. Increasing can lead to worse alternatives.
		Integer alternativeRouteMaxShareFactor = null; // Integer | If `algorithm=alternative_route` this parameter specifies how much alternatives routes can have maximum in common with the optimal route. Increasing can lead to worse alternatives.
		
		try {
			log.log(Level.FINE, "OUTBOUND:<<" + points + ">>");
		    RouteResponse routeResponse = routingApi.routeGet(points, pointsEncoded, key, locale, instructions, vehicle, elevation, calcPoints, pointHint, chDisable, weighting, edgeTraversal, algorithm, heading, headingPenalty, passThrough, roundTripDistance, roundTripSeed, alternativeRouteMaxPaths, alternativeRouteMaxWeightFactor, alternativeRouteMaxShareFactor);
		    log.log(Level.FINE, "INBOUND:<<" + routeResponse.toString() + ">>");
		    
		    routingResponse = new RoutingResponse();
		    
		    //I get the first path
		    RouteResponsePath routeResponsePath = routeResponse.getPaths().get(0);
		    
		    //Path
		    routingResponse.setPoints(new LinkedHashSet<GeoPointEntity>());
		    ResponseCoordinatesArray coordinates = routeResponsePath.getPoints().getCoordinates();
		    
		    int index = 1;
		    for(List<?> coordinate : coordinates){
		    	
		    	GeoPointEntity point = convertGeoPoint(coordinate,index++);
		    	
		    	routingResponse.getPoints().add(point);
		    }
		    
		    //Waypoints
		    routingResponse.setWaypoints(new LinkedHashSet<WayPointEntity>());
		    ResponseCoordinatesArray snappedWaypoints = routeResponsePath.getSnappedWaypoints().getCoordinates();
		    
		    index = 0;
		    for(List<?> coordinate : snappedWaypoints){
		    	
		    	//I assume snappedWaypoints and routeLocations are still in the same order
		    	RouteLocationEntity routeLocationEntity = routeLocations.get(index);
		    	
		    	GeoPointEntity geoPoint = convertGeoPoint(coordinate,++index);
		    	
		    	WayPointEntity waypoint = new WayPointEntity();
		    	waypoint.setGeoPoint(geoPoint);
		    	waypoint.setRequestId(routeLocationEntity.getLocationEntity().getRequestId());
		    	waypoint.setLocationType(routeLocationEntity.getRouteLocationType());
		    	
		    	routingResponse.getWaypoints().add(waypoint);
		    }
		    
		    //Bounding box
		    List<Double> bbox = routeResponsePath.getBbox();
		    BoundingBoxEntity boundingBox = new BoundingBoxEntity();
		    
		    GeoPointEntity minimum = new GeoPointEntity();
		    minimum.setLongitude(bbox.get(0));
		    minimum.setLatitude(bbox.get(1));
		    boundingBox.setMinimum(minimum);
		    
		    GeoPointEntity maximum = new GeoPointEntity();
		    maximum.setLongitude(bbox.get(2));
		    maximum.setLatitude(bbox.get(3));
		    boundingBox.setMaximum(maximum);
		    
		    routingResponse.setBoundingBox(boundingBox);
		    
		} catch (ApiException e) {
			log.log(Level.SEVERE, "Routing failed: " + e.getMessage() + e.getResponseBody() != null ? e.getResponseBody() : "", e);
			throw new ApplicationException(e, "Error during routing: " + points);
		}
		
		
		log.log(Level.INFO, "OUTPUT:<<" + routingResponse.toString() + ">>");
		
		return routingResponse;
	}

	private List<String> convertPoints(List<RouteLocationEntity> routeLocations) {
		List<String> points = new ArrayList<>();
		
		for(RouteLocationEntity routeLocationEntity : routeLocations){
			Double lat = routeLocationEntity.getLocationEntity().getLat();
			Double lon = routeLocationEntity.getLocationEntity().getLon();
			
			String point = "" + lat + SEPARATOR + lon;
			
			points.add(point);
		}
		
		return points;
	}
	
	private GeoPointEntity convertGeoPoint(List<?> coordinate, int position) {
		GeoPointEntity point = new GeoPointEntity();
		// From docs, they are inverted...
		Double lon = (Double) coordinate.get(0);
		Double lat = (Double) coordinate.get(1);

		point.setPosition(position);
		point.setLatitude(lat);
		point.setLongitude(lon);

		return point;
	}

}

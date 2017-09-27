package it.zano.shareride.routing.io;

import java.util.Arrays;
import java.util.List;

import it.zano.shareride.persistence.entities.RouteLocationEntity;

public class RoutingRequest {

	private List<RouteLocationEntity> routeLocations;

	public List<RouteLocationEntity> getRouteLocations() {
		return routeLocations;
	}

	public void setRouteLocations(List<RouteLocationEntity> routeLocations) {
		this.routeLocations = routeLocations;
	}

	@Override
	public String toString() {
		return "RoutingRequest [routeLocations=" + Arrays.toString(routeLocations.toArray()) + "]";
	}

}

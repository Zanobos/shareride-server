package it.zano.shareride.routing.io;

import java.util.Arrays;
import java.util.Set;

import it.zano.shareride.persistence.entities.GeoPointEntity;

public class RoutingResponse {

	private Set<GeoPointEntity> points;

	public Set<GeoPointEntity> getPoints() {
		return points;
	}

	public void setPoints(Set<GeoPointEntity> points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "RoutingResponse [points=" + Arrays.toString(points.toArray()) + "]";
	}

}

package it.zano.shareride.routing.io;

import java.util.Arrays;
import java.util.Set;

import it.zano.shareride.persistence.entities.BoundingBoxEntity;
import it.zano.shareride.persistence.entities.GeoPointEntity;

public class RoutingResponse {

	private Set<GeoPointEntity> points;
	private Set<GeoPointEntity> waypoints;
	private BoundingBoxEntity boundingBox;

	public Set<GeoPointEntity> getPoints() {
		return points;
	}

	public void setPoints(Set<GeoPointEntity> points) {
		this.points = points;
	}

	public BoundingBoxEntity getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBoxEntity boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public Set<GeoPointEntity> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(Set<GeoPointEntity> waypoints) {
		this.waypoints = waypoints;
	}

	@Override
	public String toString() {
		return "RoutingResponse [points=" + Arrays.toString(points.toArray()) + ", waypoints=" + Arrays.toString(waypoints.toArray()) + ", boundingBox=" + boundingBox + "]";
	}

}

package it.zano.shareride.routing.io;

import java.util.Arrays;
import java.util.Set;

import it.zano.shareride.persistence.entities.BoundingBoxEntity;
import it.zano.shareride.persistence.entities.GeoPointEntity;
import it.zano.shareride.persistence.entities.WayPointEntity;

public class RoutingResponse {

	private Set<GeoPointEntity> points;
	private Set<WayPointEntity> waypoints;
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
	
	public Set<WayPointEntity> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(Set<WayPointEntity> waypoints) {
		this.waypoints = waypoints;
	}

	@Override
	public String toString() {
		return "RoutingResponse [points=" + Arrays.toString(points.toArray()) + ", waypoints=" + Arrays.toString(waypoints.toArray()) + ", boundingBox=" + boundingBox + "]";
	}

}

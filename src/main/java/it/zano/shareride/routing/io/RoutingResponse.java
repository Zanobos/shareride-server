package it.zano.shareride.routing.io;

import java.util.Arrays;
import java.util.Set;

import it.zano.shareride.persistence.entities.BoundingBoxEntity;
import it.zano.shareride.persistence.entities.GeoPointEntity;

public class RoutingResponse {

	private Set<GeoPointEntity> points;
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

	@Override
	public String toString() {
		return "RoutingResponse [points=" + Arrays.toString(points.toArray()) + ", boundingBox=" + boundingBox + "]";
	}

}

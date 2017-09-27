package it.zano.shareride.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.zano.shareride.persistence.PersistenceController;

@Entity
@Table(name = PersistenceController.GEO_POINTS)
public class GeoPointEntity extends BaseEntity {

	private Double latitude;
	private Double longitude;
	@ManyToOne
	private RouteEntity route;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public RouteEntity getRoute() {
		return route;
	}

	public void setRoute(RouteEntity route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "GeoPointEntity [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}

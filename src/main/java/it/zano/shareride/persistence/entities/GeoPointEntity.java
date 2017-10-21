package it.zano.shareride.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.zano.shareride.persistence.PersistenceController;

@Entity
@Table(name = PersistenceController.GEO_POINTS)
public class GeoPointEntity extends BaseEntity {

	private Integer position;
	private Double latitude;
	private Double longitude;
	@ManyToOne
	private RouteEntity route;

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

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
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if (getClass() != obj.getClass())
			return false;
		GeoPointEntity other = (GeoPointEntity) obj;
		return this.latitude.equals(other.latitude) && this.longitude.equals(other.longitude);
	}

	@Override
	public String toString() {
		return "GeoPointEntity [position=" + position + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		return result;
	}

}

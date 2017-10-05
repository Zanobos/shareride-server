package it.zano.shareride.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.zano.shareride.persistence.PersistenceController;
import it.zano.shareride.utils.EnumRouteLocationType;

/**
 * @author Zano This class does not have information about timing and other
 *         business data (such as RouteLocationEntity) but only keeps geographic
 *         information
 */
@Entity
@Table(name = PersistenceController.WAY_POINTS)
public class WayPointEntity extends BaseEntity {

	// Composition instead of inheritance
	@OneToOne(cascade = CascadeType.ALL)
	private GeoPointEntity geoPoint;
	@Enumerated(EnumType.STRING)
	private EnumRouteLocationType locationType;
	private String requestId;
	@ManyToOne
	private RouteEntity route;
	
	public GeoPointEntity getGeoPoint() {
		return geoPoint;
	}

	public void setGeoPoint(GeoPointEntity geoPoint) {
		this.geoPoint = geoPoint;
	}

	public EnumRouteLocationType getLocationType() {
		return locationType;
	}

	public void setLocationType(EnumRouteLocationType locationType) {
		this.locationType = locationType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public RouteEntity getRoute() {
		return route;
	}

	public void setRoute(RouteEntity route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "WayPointEntity [geoPoint=" + geoPoint + ", locationType=" + locationType + ", requestId=" + requestId
				+ "]";
	}

}

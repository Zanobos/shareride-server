package it.zano.shareride.persistence.entities;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.zano.shareride.persistence.PersistenceController;

@Entity
@Table(name = PersistenceController.ROUTES)
public class RouteEntity extends BaseEntity {

	@OneToOne(cascade = CascadeType.ALL)
	private List<RouteLocationEntity> routeLocations;
	@ManyToOne(cascade = CascadeType.ALL)
	private VehicleEntity vehicle;
	private String vehicleId; // because in the output of the route optimization, we only have the id
	private Long distance;
	private Long completionTime;

	public List<RouteLocationEntity> getRouteLocations() {
		return routeLocations;
	}

	public void setRouteLocations(List<RouteLocationEntity> routeLocations) {
		this.routeLocations = routeLocations;
	}

	public VehicleEntity getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleEntity vehicle) {
		this.vehicle = vehicle;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Long getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(Long completionTime) {
		this.completionTime = completionTime;
	}

	@Override
	public String toString() {
		return "RouteEntity [locations=" + Arrays.toString(routeLocations.toArray()) + ", vehicle=" + vehicle + ", vehicleId=" + vehicleId
				+ ", distance=" + distance + ", completionTime=" + completionTime + "]";
	}

	
}

package it.zano.shareride.persistence.entities;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.zano.shareride.persistence.PersistenceController;
import it.zano.shareride.utils.EnumRouteStatus;

@Entity
@Table(name = PersistenceController.ROUTES)
public class RouteEntity extends BaseEntity {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "route", orphanRemoval = true,fetch = FetchType.EAGER)
	private Set<WayPointEntity> wayPoints = new LinkedHashSet<>();
	@OneToOne(cascade = CascadeType.ALL)
	private BoundingBoxEntity boundingBox;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "route", orphanRemoval = true,fetch = FetchType.EAGER)
	private Set<GeoPointEntity> path = new LinkedHashSet<>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "route", orphanRemoval = true,fetch = FetchType.EAGER)
	private List<RouteLocationEntity> routeLocations;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "routes",fetch = FetchType.EAGER)
	private Set<UserRequestEntity> userRequests = new LinkedHashSet<>();
	@ManyToOne
	private VehicleEntity vehicle;
	@Transient
	private String vehicleId; // because in the output of the route optimization, we only have the id
	private Long distance;
	private Long completionTime;
	@Enumerated(EnumType.STRING)
	private EnumRouteStatus routeStatus;

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

	public EnumRouteStatus getRouteStatus() {
		return routeStatus;
	}

	public void setRouteStatus(EnumRouteStatus routeStatus) {
		this.routeStatus = routeStatus;
	}
	
	public Set<UserRequestEntity> getUserRequests() {
		return userRequests;
	}
	
	public void addUserRequest(UserRequestEntity userRequest) {
		getUserRequests().add(userRequest);
		userRequest.getRoutes().add(this);
	}

	public void setUserRequests(Set<UserRequestEntity> userRequests) {
		this.userRequests = userRequests;
	}
	
	public Set<GeoPointEntity> getPath() {
		return path;
	}

	public void setPath(Set<GeoPointEntity> path) {
		for(GeoPointEntity point : path) {
			point.setRoute(this);
		}
		this.path = path;
	}
	
	public BoundingBoxEntity getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBoxEntity boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public Set<WayPointEntity> getWayPoints() {
		return wayPoints;
	}

	public void setWayPoints(Set<WayPointEntity> wayPoints) {
		for(WayPointEntity waypoint : wayPoints) {
			waypoint.setRoute(this);
		}
		this.wayPoints = wayPoints;
	}

	@Override
	public String toString() {
		return "RouteEntity [routeLocations=" + Arrays.toString(routeLocations.toArray()) + ", vehicle=" + vehicle + ", vehicleId=" + vehicleId
				+ ", distance=" + distance + ", completionTime=" + completionTime + ", routeStatus=" + routeStatus
				+ "]";
	}

	
}

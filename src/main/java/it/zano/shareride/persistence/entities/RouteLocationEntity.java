package it.zano.shareride.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import it.zano.shareride.persistence.PersistenceController;
import it.zano.shareride.utils.EnumRouteLocationType;

@Entity
@Table(name = PersistenceController.ROUTE_LOCATIONS)
public class RouteLocationEntity extends BaseEntity {

	@OneToOne
	private LocationEntity locationEntity;
	@Transient
	private String locationEntityId; // because in the output of the route optimization, we only have the id
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
	private LocalTime arrivalTime;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
	private LocalTime endTime;
	private Integer loadBefore;
	private Integer loadAfter;
	@Enumerated(EnumType.STRING)
	private EnumRouteLocationType routeLocationType;
	@ManyToOne
	private RouteEntity route;
	
	public LocationEntity getLocationEntity() {
		return locationEntity;
	}

	public void setLocationEntity(LocationEntity locationEntity) {
		this.locationEntity = locationEntity;
	}

	public String getLocationEntityId() {
		return locationEntityId;
	}

	public void setLocationEntityId(String locationEntityId) {
		this.locationEntityId = locationEntityId;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Integer getLoadBefore() {
		return loadBefore;
	}

	public void setLoadBefore(Integer loadBefore) {
		this.loadBefore = loadBefore;
	}

	public Integer getLoadAfter() {
		return loadAfter;
	}

	public void setLoadAfter(Integer loadAfter) {
		this.loadAfter = loadAfter;
	}

	public EnumRouteLocationType getRouteLocationType() {
		return routeLocationType;
	}

	public void setRouteLocationType(EnumRouteLocationType routeLocationType) {
		this.routeLocationType = routeLocationType;
	}

	public RouteEntity getRoute() {
		return route;
	}

	public void setRoute(RouteEntity route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "RouteLocationEntity [locationEntity=" + locationEntity + ", locationEntityId=" + locationEntityId
				+ ", arrivalTime=" + arrivalTime + ", endTime=" + endTime + ", loadBefore=" + loadBefore
				+ ", loadAfter=" + loadAfter + ", routeLocationType=" + routeLocationType + ", route=" + route.getId() + "]";
	}

}

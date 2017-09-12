package it.zano.shareride.persistence.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import it.zano.shareride.persistence.PersistenceController;
import it.zano.shareride.utils.EnumStatus;

@Entity
@Table(name = PersistenceController.USER_REQUESTS)
public class UserRequestEntity extends BaseEntity {

	private String userId;
	private String userName;
	@OneToOne(cascade = CascadeType.ALL)
	private LocationEntity pickup;
	@OneToOne(cascade = CascadeType.ALL)
	private LocationEntity delivery;
	private Integer numberOfSeats;
	private Boolean needAssistance;
	private String areaId;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate localDate;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
	private LocalTime localTime;
	@Enumerated(EnumType.STRING)
	private EnumStatus status;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
	@JoinTable(name = PersistenceController.USER_REQUESTS_ROUTES,
	    joinColumns = {
	        @JoinColumn(
	            name = "user_request_id"
	        )
	    },
	    inverseJoinColumns = {
	        @JoinColumn(
	            name = "route_id"
	        )
	    }
	)
	private Set<RouteEntity> routes = new HashSet<>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocationEntity getPickup() {
		return pickup;
	}

	public void setPickup(LocationEntity pickup) {
		this.pickup = pickup;
	}

	public LocationEntity getDelivery() {
		return delivery;
	}

	public void setDelivery(LocationEntity delivery) {
		this.delivery = delivery;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Boolean getNeedAssistance() {
		return needAssistance;
	}

	public void setNeedAssistance(Boolean needAssistance) {
		this.needAssistance = needAssistance;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public LocalTime getLocalTime() {
		return localTime;
	}

	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}

	public EnumStatus getStatus() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<RouteEntity> getRoutes() {
		return routes;
	}

	public void setRoutes(Set<RouteEntity> routes) {
		this.routes = routes;
	}

	@Override
	public String toString() {
		return "UserRequestEntity [userId=" + userId + ", userName=" + userName + ", pickup=" + pickup + ", delivery="
				+ delivery + ", numberOfSeats=" + numberOfSeats + ", needAssistance=" + needAssistance + ", areaId="
				+ areaId + ", localDate=" + localDate + ", localTime=" + localTime + ", status=" + status + "]";
	}
	
}

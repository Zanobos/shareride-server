package it.zano.shareride.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	@Override
	public String toString() {
		return "UserRequestEntity [userName=" + userName + ", pickup=" + pickup + ", delivery=" + delivery
				+ ", numberOfSeats=" + numberOfSeats + ", needAssistance=" + needAssistance + ", areaId=" + areaId
				+ ", localDate=" + localDate + ", localTime=" + localTime + ", status=" + status + "]";
	}
	
	

}

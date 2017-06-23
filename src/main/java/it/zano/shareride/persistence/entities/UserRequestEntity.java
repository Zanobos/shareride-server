package it.zano.shareride.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "USER_REQUESTS")
public class UserRequestEntity extends BaseEntity {

	private String userName;
	private LocationEntity pickup;
	private LocationEntity delivery;
	private Integer numberOfSeats;
	private Boolean needAssistance;
	private String areaId;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private DateTime dateTime;

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

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

}

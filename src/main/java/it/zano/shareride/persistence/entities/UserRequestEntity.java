package it.zano.shareride.persistence.entities;

public class UserRequestEntity {

	private String id;
	private String userName;
	private LocationEntity pickup;
	private LocationEntity delivery;
	private Integer numberOfSeats;
	private Boolean needAssistance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

}

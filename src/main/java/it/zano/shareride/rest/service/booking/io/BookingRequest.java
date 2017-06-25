package it.zano.shareride.rest.service.booking.io;

import it.zano.shareride.rest.service.base.io.BaseRequest;
import it.zano.shareride.rest.service.booking.entities.AdditionalInfo;
import it.zano.shareride.rest.service.booking.entities.Location;
import it.zano.shareride.rest.service.booking.entities.UserInfo;

public class BookingRequest extends BaseRequest {

	private UserInfo userInfo;
	private Location pickup;
	private Location delivery;
	private AdditionalInfo additionalInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Location getPickup() {
		return pickup;
	}

	public void setPickup(Location pickup) {
		this.pickup = pickup;
	}

	public Location getDelivery() {
		return delivery;
	}

	public void setDelivery(Location delivery) {
		this.delivery = delivery;
	}

	public AdditionalInfo getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(AdditionalInfo additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

}

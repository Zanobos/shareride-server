package it.zano.shareride.rest.service.booking.entities;

public class UserInfo {

	private String name;
	private String userId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserInfo [name=" + name + ", userId=" + userId + "]";
	}
	

}

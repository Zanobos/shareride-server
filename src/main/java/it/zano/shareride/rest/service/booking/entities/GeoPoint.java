package it.zano.shareride.rest.service.booking.entities;

public class GeoPoint {
	
	private Integer position;
	private Double latitude;
	private Double longitude;

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "GeoPoint [position=" + position + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}

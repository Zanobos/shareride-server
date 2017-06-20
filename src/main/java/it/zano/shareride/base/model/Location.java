package it.zano.shareride.base.model;

import java.util.Date;

public class Location {

	private String locationId;
	private String address;
	private Double lat;
	private Double lon;
	private Date time;

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}

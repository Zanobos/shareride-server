package it.zano.shareride.geocoding.io;

public class ConvertLatLonResponse {

	private String address;
	private String name;
	private double lat;
	private double lon;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "ConvertLatLonResponse [address=" + address + ", name=" + name + ", lat=" + lat + ", lon=" + lon + "]";
	}

}

package it.zano.shareride.rest.service.booking.entities;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.joda.deser.LocalTimeDeserializer;

public class Location {

	private String locationName;
	private String address;
	private Double lat;
	private Double lon;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date;
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	private LocalTime time;

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Location [locationName=" + locationName + ", address=" + address + ", lat=" + lat + ", lon=" + lon
				+ ", date=" + date + ", time=" + time + "]";
	}
	

}

package it.zano.shareride.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import it.zano.shareride.persistence.PersistenceController;

@Entity
@Table(name = PersistenceController.LOCATIONS)
public class LocationEntity extends BaseEntity {

	private String locationName;
	private String address;
	private Double lat;
	private Double lon;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate date;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
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
		return "LocationEntity [locationName=" + locationName + ", address=" + address + ", lat=" + lat + ", lon=" + lon
				+ ", date=" + date + ", time=" + time + "]";
	}

}

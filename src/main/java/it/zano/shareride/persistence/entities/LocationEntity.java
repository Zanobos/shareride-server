package it.zano.shareride.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import it.zano.shareride.persistence.PersistenceController;

@Entity
@Table( name = PersistenceController.LOCATIONS )
public class LocationEntity extends BaseEntity{

	private String locationId;
	private String address;
	private Double lat;
	private Double lon;
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private DateTime dateTime;

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

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

}

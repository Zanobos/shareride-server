package it.zano.shareride.persistence.io;

import org.joda.time.DateTime;


/**
 * @author Zano
 */
public class AreaTimeInput {

	private String areaId;
	private DateTime dateTime;

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

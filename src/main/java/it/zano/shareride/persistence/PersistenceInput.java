package it.zano.shareride.persistence;

import org.joda.time.DateTime;


/**
 * @author Zano
 * TODO
 */
public class PersistenceInput {

	private String areaId;
	private DateTime date;

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

}

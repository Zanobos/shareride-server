package it.zano.shareride.rest.service.booking.entities;

import it.zano.shareride.utils.EnumPeriodicity;

public class AdditionalInfo {

	private Integer numberOfSeats;
	private Boolean needAssistance;
	private String areaId;
	private EnumPeriodicity periodicity;

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Boolean getNeedAssistance() {
		return needAssistance;
	}

	public void setNeedAssistance(Boolean needAssistance) {
		this.needAssistance = needAssistance;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public EnumPeriodicity getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(EnumPeriodicity periodicity) {
		this.periodicity = periodicity;
	}

	@Override
	public String toString() {
		return "AdditionalInfo [numberOfSeats=" + numberOfSeats + ", needAssistance=" + needAssistance + ", areaId="
				+ areaId + ", periodicity=" + periodicity + "]";
	}
	
	

}

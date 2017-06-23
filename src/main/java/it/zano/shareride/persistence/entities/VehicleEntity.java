package it.zano.shareride.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "VEHICLES" )
public class VehicleEntity extends BaseEntity{

	private String type;
	private int size;
	private LocationEntity startAddress;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public LocationEntity getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(LocationEntity startAddress) {
		this.startAddress = startAddress;
	}

}

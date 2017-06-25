package it.zano.shareride.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VEHICLES")
public class VehicleEntity extends BaseEntity {

	private String vehicleId;
	@ManyToOne(cascade = CascadeType.ALL)
	private LocationEntity startAddress;
	@ManyToOne(cascade = CascadeType.ALL)
	private VehicleTypeEntity type;

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public LocationEntity getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(LocationEntity startAddress) {
		this.startAddress = startAddress;
	}

	public VehicleTypeEntity getType() {
		return type;
	}

	public void setType(VehicleTypeEntity type) {
		this.type = type;
	}

}

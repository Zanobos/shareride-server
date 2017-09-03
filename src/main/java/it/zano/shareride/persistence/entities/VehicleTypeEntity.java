package it.zano.shareride.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import it.zano.shareride.persistence.PersistenceController;

@Entity
@Table(name = PersistenceController.VEHICLE_TYPES)
public class VehicleTypeEntity extends BaseEntity {

	private String typeId;
	private int capacity;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}

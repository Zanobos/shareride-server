package it.zano.shareride.persistence.entities;

public class TransportEntity {

	private String id;
	private String type;
	private int size;
	private LocationEntity startAddress;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

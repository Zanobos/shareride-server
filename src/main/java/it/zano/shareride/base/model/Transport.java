package it.zano.shareride.base.model;

public class Transport {

	private String id;
	private String type;
	private int size;
	private Location startAddress;

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

	public Location getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(Location startAddress) {
		this.startAddress = startAddress;
	}

}

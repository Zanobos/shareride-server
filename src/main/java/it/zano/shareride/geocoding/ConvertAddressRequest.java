package it.zano.shareride.geocoding;

public class ConvertAddressRequest {

	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ConvertAddressRequest [address=" + address + "]";
	}

}

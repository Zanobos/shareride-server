package it.zano.shareride.rest.service.booking.entities;

public class BoundingBox {

	private GeoPoint minimum;
	private GeoPoint maximum;

	public GeoPoint getMinimum() {
		return minimum;
	}

	public void setMinimum(GeoPoint minimum) {
		this.minimum = minimum;
	}

	public GeoPoint getMaximum() {
		return maximum;
	}

	public void setMaximum(GeoPoint maximum) {
		this.maximum = maximum;
	}

	@Override
	public String toString() {
		return "BoundingBox [minimum=" + minimum + ", maximum=" + maximum + "]";
	}

}

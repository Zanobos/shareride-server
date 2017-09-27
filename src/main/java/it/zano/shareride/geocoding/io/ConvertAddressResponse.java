package it.zano.shareride.geocoding.io;

import it.zano.shareride.persistence.entities.GeoPointEntity;

public class ConvertAddressResponse {

	private GeoPointEntity point;

	public GeoPointEntity getPoint() {
		return point;
	}

	public void setPoint(GeoPointEntity point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "ConvertAddressResponse [point=" + point + "]";
	}

}

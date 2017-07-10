package it.zano.shareride.geocoding;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.GeocodingApi;
import com.graphhopper.directions.api.client.model.GeocodingLocation;
import com.graphhopper.directions.api.client.model.GeocodingPoint;
import com.graphhopper.directions.api.client.model.GeocodingResponse;

import it.zano.shareride.geocoding.io.ConvertAddressRequest;
import it.zano.shareride.geocoding.io.ConvertAddressResponse;
import it.zano.shareride.rest.service.exception.ApplicationException;
import it.zano.shareride.utils.PropertiesLoader;

public class GeocodingController {

	private static final String LOCALE = PropertiesLoader.getProperty("geocoding.locale");
	private static final int RETURNED_RESULT = PropertiesLoader.getPropertyInt("geocoding.returnedResults");
	private static final String PROVIDER = PropertiesLoader.getProperty("geocoding.provider");
	
	private static final Logger log = Logger.getLogger(GeocodingController.class.getName());

	public ConvertAddressResponse convertAddress(ConvertAddressRequest convertAddressRequest) throws ApplicationException {

		log.log(Level.INFO, "In method: convertAddress, " + convertAddressRequest.toString());
		
		GeocodingApi geocoding = new GeocodingApi();
		String key = PropertiesLoader.getProperty("graphopper.key");
		boolean reverse = false;
		String address = convertAddressRequest.getAddress();

		ConvertAddressResponse convertAddressResponse = new ConvertAddressResponse();
		try {
			GeocodingResponse geocodingResponse = geocoding.geocodeGet(key, address, LOCALE, RETURNED_RESULT, reverse,"", PROVIDER);
			GeocodingLocation location = geocodingResponse.getHits().get(0);
			GeocodingPoint geocodingPoint = location.getPoint();
			convertAddressResponse.setLat(geocodingPoint.getLat());
			convertAddressResponse.setLon(geocodingPoint.getLng());
		} catch (ApiException e) {
			log.log(Level.SEVERE, "Geocoding failed: " + e.getMessage(), e);
			throw new ApplicationException(e,"Error during geocoding: " + convertAddressRequest.getAddress());
		}
		return convertAddressResponse;

	}

}

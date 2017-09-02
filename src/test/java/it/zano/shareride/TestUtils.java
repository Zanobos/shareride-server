package it.zano.shareride;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.zano.shareride.persistence.entities.VehicleEntity;
import it.zano.shareride.rest.service.booking.io.ConfirmRequestRequest;
import it.zano.shareride.utils.Constants;

public class TestUtils {

	private static final String suffix = ".json";
	private static final String baseFileRequest = "Request_";
	private static final String baseFileVehicle = "Vehicle_";
	
	public static ConfirmRequestRequest createRequest(String number) {

		ConfirmRequestRequest request = null;
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(Constants.FileSystem.DIR_BOOKING_REQUESTS + Constants.FileSystem.SEPARATOR + baseFileRequest + number + suffix);
		Reader reader = new InputStreamReader(inputStream);
		request = gson.fromJson(reader, ConfirmRequestRequest.class);

		return request;
	}
	
	public static VehicleEntity createVehicle(String number) {
		
		VehicleEntity vehicle = null;
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(Constants.FileSystem.DIR_VEHICLES + Constants.FileSystem.SEPARATOR + baseFileVehicle + number + suffix);
		Reader reader = new InputStreamReader(inputStream);
		vehicle = gson.fromJson(reader, VehicleEntity.class);

		return vehicle;
	}

}

package it.zano.shareride;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.zano.shareride.rest.service.booking.io.BookingRequest;
import it.zano.shareride.utils.Constants;

public class TestUtils {

	private static final String suffix = ".json";
	private static final String baseFile = "Request_";
	
	public static BookingRequest createRequest(String number) {

		BookingRequest request = null;
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(Constants.FileSystem.DIR_BOOKING_REQUESTS + Constants.FileSystem.SEPARATOR + baseFile + number + suffix);
		Reader reader = new InputStreamReader(inputStream);
		request = gson.fromJson(reader, BookingRequest.class);

		return request;
	}

}

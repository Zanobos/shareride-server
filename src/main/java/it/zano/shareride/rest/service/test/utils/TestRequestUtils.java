package it.zano.shareride.rest.service.test.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.graphhopper.directions.api.client.model.Request;

import it.zano.shareride.utils.Constants;

public class TestRequestUtils {

	private static final String suffix = ".json";
	private static final String baseFile = "Request_";
	
	public static Request createRequest(String number) {

		Request request = null;
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(Constants.FileSystem.DIR_ROUTE_REQUESTS + Constants.FileSystem.SEPARATOR + baseFile + number + suffix);
		Reader reader = new InputStreamReader(inputStream);
		request = gson.fromJson(reader, Request.class);

		return request;
	}

}

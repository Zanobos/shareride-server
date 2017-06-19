package it.zano.shareride.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.graphhopper.directions.api.client.model.Address;
import com.graphhopper.directions.api.client.model.Algorithm;
import com.graphhopper.directions.api.client.model.Request;
import com.graphhopper.directions.api.client.model.Service;
import com.graphhopper.directions.api.client.model.Vehicle;

public class TestRequestUtils {

	private static final String requestDir = "request/";
	private static final String suffix = ".json";
	private static final String baseFile = "Request_";
	
	public static Request createRequest(String number) {

		Request request = null;
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(requestDir + baseFile + number + suffix);
		Reader reader = new InputStreamReader(inputStream);
		request = gson.fromJson(reader, Request.class);

		return request;
	}

	public static Request createRequest() {
		Request request = new Request();
		request.setAlgorithm(new Algorithm());

		/*
		 * specify vehicles
		 */
		List<Vehicle> vehicles = new ArrayList<Vehicle>();

		Vehicle v = new Vehicle();
		v.setVehicleId("v1");
		v.setStartAddress(createAddress("berlin", 52.537, 13.406));
		vehicles.add(v);
		request.setVehicles(vehicles);

		/*
		 * specify your services
		 */
		List<Service> services = new ArrayList<Service>();

		services.add(createService("hamburg", 53.552, 9.999));
		services.add(createService("munich", 48.145, 11.570));
		services.add(createService("cologne", 50.936, 6.957));
		services.add(createService("frankfurt", 50.109, 8.670));

		request.setServices(services);

		return request;
	}

	public static Address createAddress(String locationId, double lat, double lon) {
		Address a = new Address();
		a.setLat(lat);
		a.setLon(lon);
		a.setLocationId(locationId);
		return a;
	}

	public static Service createService(String id, double lat, double lon) {
		Service service = new Service();
		service.setId(id);
		service.setType(Service.TypeEnum.SERVICE);
		service.setAddress(createAddress(id, lat, lon));
		return service;
	}
}

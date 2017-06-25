package it.zano.shareride.optimization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;

import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.SolutionApi;
import com.graphhopper.directions.api.client.api.VrpApi;
import com.graphhopper.directions.api.client.model.Address;
import com.graphhopper.directions.api.client.model.Algorithm;
import com.graphhopper.directions.api.client.model.Algorithm.ObjectiveEnum;
import com.graphhopper.directions.api.client.model.Algorithm.ProblemTypeEnum;
import com.graphhopper.directions.api.client.model.JobId;
import com.graphhopper.directions.api.client.model.Request;
import com.graphhopper.directions.api.client.model.Response;
import com.graphhopper.directions.api.client.model.Shipment;
import com.graphhopper.directions.api.client.model.SolutionUnassigned;
import com.graphhopper.directions.api.client.model.Stop;
import com.graphhopper.directions.api.client.model.TimeWindow;
import com.graphhopper.directions.api.client.model.Vehicle;
import com.graphhopper.directions.api.client.model.VehicleType;
import com.graphhopper.directions.api.client.model.VehicleType.ProfileEnum;

import it.zano.shareride.optimization.io.RouteDoabilityRequest;
import it.zano.shareride.optimization.io.RouteDoabilityResponse;
import it.zano.shareride.persistence.entities.LocationEntity;
import it.zano.shareride.persistence.entities.VehicleEntity;
import it.zano.shareride.persistence.entities.VehicleTypeEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.utils.EnumStatus;
import it.zano.shareride.utils.PropertiesLoader;

public class RouteOptimizationController {

	private static final Logger log = Logger.getLogger(RouteOptimizationController.class.getName());
	
	public RouteDoabilityResponse assessDoability(RouteDoabilityRequest request) {

		log.log(Level.INFO, "In method: assessDoability, " + request.toString());
		
		String key = PropertiesLoader.getProperty("graphopper.key");
		int waitTime = PropertiesLoader.getPropertyInt("routeoptimization.waitTime");

		VrpApi vrpApi = new VrpApi();

		Request body = convertRequest(request);
		JobId jobId;
		RouteDoabilityResponse response = null;
		try {
			jobId = vrpApi.postVrp(key, body);

			SolutionApi solApi = new SolutionApi();
			Response rsp;

			while (true) {
				rsp = solApi.getSolution(key, jobId.getJobId());
				if (rsp.getStatus().equals(Response.StatusEnum.FINISHED)) {
					break;
				}
				Thread.sleep(waitTime);
			}
			
			response = convertResponse(rsp);
		} catch (ApiException e) {
			log.log(Level.SEVERE, "Route optimization failed: " + e.getMessage(), e);
			response = new RouteDoabilityResponse();
			response.setStatus(EnumStatus.REJECTED);
			//TODO gestione errori PER BENE
		} catch (InterruptedException e) {
			log.log(Level.SEVERE, "Route optimization failed: " + e.getMessage(), e);
		}

		return response;

	}

	private RouteDoabilityResponse convertResponse(Response response) {
		RouteDoabilityResponse doabilityResponse = new RouteDoabilityResponse();
		
		EnumStatus status = EnumStatus.REJECTED;
		SolutionUnassigned unassigned = response.getSolution().getUnassigned();
		if(unassigned.getShipments().isEmpty()){
			status = EnumStatus.ACCEPTED;
		}
		
		//TODO the exact time and location of pickups
		doabilityResponse.setStatus(status);
		return doabilityResponse;
	}

	private Request convertRequest(RouteDoabilityRequest routeDoabilityRequest) {
		
		Request request = new Request();
		
		Algorithm algorithm = createAlgorithm();
		request.setAlgorithm(algorithm);

		List<VehicleType> vehicleTypes = convertVehiclesType(routeDoabilityRequest.getAvailableTransports());
		request.setVehicleTypes(vehicleTypes);
		
		List<Vehicle> vehicles = convertVehicles(routeDoabilityRequest.getAvailableTransports());
		request.setVehicles(vehicles);

		List<Shipment> shipments = convertShipments(routeDoabilityRequest.getRequests());
		request.setShipments(shipments);

		return request;
	}

	private List<VehicleType> convertVehiclesType(List<VehicleEntity> availableTransports) {
		
		Map<String,VehicleType> typeMap = new LinkedHashMap<>();
		for(VehicleEntity transport : availableTransports) {
			VehicleType type = convertVehicleType(transport.getType());
			typeMap.put(type.getTypeId(), type);
		}
		return new ArrayList<>(typeMap.values());
	}

	private VehicleType convertVehicleType(VehicleTypeEntity type) {
		VehicleType vehicleType = new VehicleType();
		vehicleType.setCapacity(Arrays.asList(type.getCapacity()));
		vehicleType.setProfile(ProfileEnum.SMALL_TRUCK); //TODO
		vehicleType.setTypeId(type.getTypeId());
		return vehicleType;
	}

	private List<Shipment> convertShipments(List<UserRequestEntity> requests) {

		List<Shipment> shipments = new ArrayList<>();
		for(UserRequestEntity request : requests) {
			Shipment shipment = convertShipment(request);
			shipments.add(shipment);
		}
		return shipments;
	}

	private Shipment convertShipment(UserRequestEntity userRequest) {
		
		Shipment shipment = new Shipment();
		shipment.setId(userRequest.getId());
		shipment.setSize(Arrays.asList(userRequest.getNumberOfSeats()));
		shipment.setPickup(convertStop(userRequest.getPickup(), userRequest.getNumberOfSeats(), true));
		shipment.setDelivery(convertStop(userRequest.getDelivery(), userRequest.getNumberOfSeats(), false));
		return shipment;
	}

	private Stop convertStop(LocationEntity location, Integer numberOfSeats, boolean pickup) {
		Stop stop = new Stop();
		
		stop.setAddress(convertAddress(location));
		
		Integer stopTimePerPerson = PropertiesLoader.getPropertyInt("routeoptimization.stopTimePerPerson");
		Long duration = (long) (stopTimePerPerson * numberOfSeats);
		stop.setDuration(duration);
		
		stop.setTimeWindows(Arrays.asList(convertTimeWindow(location.getDateTime(),pickup)));
		return stop;
	}

	private TimeWindow convertTimeWindow(DateTime dateTime, boolean pickup) {
		//TODO
		if(dateTime == null){
			return null;
		}
		
		TimeWindow timeWindow = new TimeWindow();
		Long time = dateTime.getMillis();
		if(pickup) {
			timeWindow.setEarliest(time);
			timeWindow.setLatest(time + 60 * 15);
		} else {
			timeWindow.setEarliest(0l); //TODO
			timeWindow.setLatest(time);
		}
		return timeWindow;
	}

	private List<Vehicle> convertVehicles(List<VehicleEntity> availableTransports) {
		
		List<Vehicle> vehicles = new ArrayList<>();
		for(VehicleEntity transport : availableTransports) {
			Vehicle vehicle = convertVehicle(transport);
			vehicles.add(vehicle);
		}
		return vehicles;
	}

	private Vehicle convertVehicle(VehicleEntity transport) {
		
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleId(transport.getId());
		vehicle.setTypeId(transport.getType().getTypeId());
		vehicle.setStartAddress(convertAddress(transport.getStartAddress()));
		
		return vehicle;
	}

	private Algorithm createAlgorithm() {
		Algorithm algorithm = new Algorithm();
		
		String objective = PropertiesLoader.getProperty("routeoptimization.planning.algorithm.objective");
		ObjectiveEnum objectiveEnum = ObjectiveEnum.valueOf(objective);
		algorithm.setObjective(objectiveEnum);
		
		String problemType = PropertiesLoader.getProperty("routeoptimization.planning.algorithm.problemType");
		ProblemTypeEnum problemTypeEnum = ProblemTypeEnum.valueOf(problemType);
		algorithm.setProblemType(problemTypeEnum);
		
		return algorithm;
	}
	
	private Address convertAddress(LocationEntity location) {
		Address address = new Address();
		address.setLat(location.getLat());
		address.setLon(location.getLon());
		address.setLocationId(location.getLocationName());
		address.setName(location.getAddress());
		return address;
	}

}

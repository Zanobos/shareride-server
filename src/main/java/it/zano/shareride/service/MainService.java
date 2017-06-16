package it.zano.shareride.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.graphhopper.directions.api.client.api.SolutionApi;
import com.graphhopper.directions.api.client.api.VrpApi;
import com.graphhopper.directions.api.client.model.Address;
import com.graphhopper.directions.api.client.model.Algorithm;
import com.graphhopper.directions.api.client.model.JobId;
import com.graphhopper.directions.api.client.model.Request;
import com.graphhopper.directions.api.client.model.Response;
import com.graphhopper.directions.api.client.model.Service;
import com.graphhopper.directions.api.client.model.Vehicle;


@Path("/mainService")
public class MainService {

	private static final Logger log = Logger.getLogger(MainService.class.getName());

	private static final String key = "e43da19e-9203-4721-a651-254d12693021";
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/doService")
	public String doService(String request) throws Exception {

		log.log(Level.INFO, "doService");

		VrpApi vrpApi = new VrpApi();
		
		Request body = createRequest();
		JobId jobId = vrpApi.postVrp(key, body);
		
		log.log(Level.INFO, body.toString());
		SolutionApi solApi = new SolutionApi();
        Response rsp;

        while (true) {
            rsp = solApi.getSolution(key, jobId.getJobId());
            if (rsp.getStatus().equals(Response.StatusEnum.FINISHED)) {
                break;
            }
            Thread.sleep(200);
        }
		
        log.log(Level.INFO, rsp.toString());
        
		return request;
	}
	
	private Request createRequest() {
        Request request = new Request();
        request.setAlgorithm(new Algorithm());

        /*
        specify vehicles
         */
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        Vehicle v = new Vehicle();
        v.setVehicleId("v1");
        v.setStartAddress(createAddress("berlin", 52.537, 13.406));
        vehicles.add(v);
        request.setVehicles(vehicles);

        /*
        specify your services
         */
        List<Service> services = new ArrayList<Service>();

        services.add(createService("hamburg", 53.552, 9.999));
        services.add(createService("munich", 48.145, 11.570));
        services.add(createService("cologne", 50.936, 6.957));
        services.add(createService("frankfurt", 50.109, 8.670));

        request.setServices(services);

        return request;
    }

    public Address createAddress(String locationId, double lat, double lon) {
        Address a = new Address();
        a.setLat(lat);
        a.setLon(lon);
        a.setLocationId(locationId);
        return a;
    }

    public Service createService(String id, double lat, double lon) {
        Service service = new Service();
        service.setId(id);
        service.setType(Service.TypeEnum.SERVICE);
        service.setAddress(createAddress(id, lat, lon));
        return service;
    }

}

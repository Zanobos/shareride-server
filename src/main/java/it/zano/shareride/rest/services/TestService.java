package it.zano.shareride.rest.services;

import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.graphhopper.directions.api.client.api.SolutionApi;
import com.graphhopper.directions.api.client.api.VrpApi;
import com.graphhopper.directions.api.client.model.JobId;
import com.graphhopper.directions.api.client.model.Request;
import com.graphhopper.directions.api.client.model.Response;

import it.zano.shareride.geocoding.GeocodingController;
import it.zano.shareride.geocoding.io.ConvertAddressRequest;
import it.zano.shareride.geocoding.io.ConvertAddressResponse;
import it.zano.shareride.rest.base.services.BaseService;
import it.zano.shareride.utils.PropertiesLoader;
import it.zano.shareride.utils.TestRequestUtils;

@Path("/test")
public class TestService extends BaseService{
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/routeoptimization")
	public Response test(
			@QueryParam("requestNumber") @DefaultValue("01")String requestNumber) throws Exception {

		log.log(Level.INFO, "testService");

		String key = PropertiesLoader.getProperty("graphopper.key");
		
		VrpApi vrpApi = new VrpApi();

		Request body = TestRequestUtils.createRequest(requestNumber);
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

		return rsp;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/geocode")
	public ConvertAddressResponse geocode(ConvertAddressRequest request) throws Exception {

		log.log(Level.INFO, "geocode");

		GeocodingController controller = new GeocodingController();
		ConvertAddressResponse convertAddressResponse = controller.convertAddress(request);

		return convertAddressResponse;
	}

}

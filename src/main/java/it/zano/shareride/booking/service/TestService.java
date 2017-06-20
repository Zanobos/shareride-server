package it.zano.shareride.booking.service;

import java.util.logging.Level;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.graphhopper.directions.api.client.api.SolutionApi;
import com.graphhopper.directions.api.client.api.VrpApi;
import com.graphhopper.directions.api.client.model.JobId;
import com.graphhopper.directions.api.client.model.Request;
import com.graphhopper.directions.api.client.model.Response;

import it.zano.shareride.utils.PropertiesLoader;
import it.zano.shareride.utils.TestRequestUtils;

@Path("/testService")
public class TestService extends BaseService{
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
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

}

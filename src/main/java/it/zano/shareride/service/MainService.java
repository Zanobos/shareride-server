package it.zano.shareride.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.graphhopper.directions.api.client.api.SolutionApi;
import com.graphhopper.directions.api.client.api.VrpApi;
import com.graphhopper.directions.api.client.model.JobId;
import com.graphhopper.directions.api.client.model.Request;
import com.graphhopper.directions.api.client.model.Response;

import it.zano.shareride.utils.TestRequestUtils;

@Path("/mainService")
public class MainService {

	private static final Logger log = Logger.getLogger(MainService.class.getName());

	private static final String key = "e43da19e-9203-4721-a651-254d12693021";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/testService")
	public Response testService() throws Exception {

		log.log(Level.INFO, "doService");

		VrpApi vrpApi = new VrpApi();

		Request body = TestRequestUtils.createRequest();
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

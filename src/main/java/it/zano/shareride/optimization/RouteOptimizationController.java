package it.zano.shareride.optimization;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.graphhopper.directions.api.client.api.SolutionApi;
import com.graphhopper.directions.api.client.api.VrpApi;
import com.graphhopper.directions.api.client.model.JobId;
import com.graphhopper.directions.api.client.model.Request;
import com.graphhopper.directions.api.client.model.Response;

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
		} catch (Exception e) {
			log.log(Level.SEVERE, "Route optimization failed: " + e.getMessage(), e);
		}

		return response;

	}

	private RouteDoabilityResponse convertResponse(Response response) {
		// TODO Auto-generated method stub
		return null;
	}

	private Request convertRequest(RouteDoabilityRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}

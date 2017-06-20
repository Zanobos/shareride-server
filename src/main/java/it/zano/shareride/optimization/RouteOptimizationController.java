package it.zano.shareride.optimization;

import java.util.List;

import com.graphhopper.directions.api.client.api.SolutionApi;
import com.graphhopper.directions.api.client.api.VrpApi;
import com.graphhopper.directions.api.client.model.JobId;
import com.graphhopper.directions.api.client.model.Request;
import com.graphhopper.directions.api.client.model.Response;

import it.zano.shareride.booking.entities.BookingRequest;
import it.zano.shareride.utils.PropertiesLoader;

public class RouteOptimizationController {

	public RouteDoabilityResponse assessDoability(RouteDoabilityRequest request) {

		String key = PropertiesLoader.getProperty("graphopper.key");

		VrpApi vrpApi = new VrpApi();

		Request body = convertRequests(request.getRequests());
		JobId jobId;
		try {
			jobId = vrpApi.postVrp(key, body);

			SolutionApi solApi = new SolutionApi();
			Response rsp;

			while (true) {
				rsp = solApi.getSolution(key, jobId.getJobId());
				if (rsp.getStatus().equals(Response.StatusEnum.FINISHED)) {
					break;
				}
				Thread.sleep(200);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

		// TODO use the response
		return new RouteDoabilityResponse();

	}

	private Request convertRequests(List<BookingRequest> requests) {
		// TODO Auto-generated method stub
		return null;
	}

}

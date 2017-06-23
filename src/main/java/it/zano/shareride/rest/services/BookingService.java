package it.zano.shareride.rest.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;

import it.zano.shareride.optimization.RouteOptimizationController;
import it.zano.shareride.optimization.io.RouteDoabilityRequest;
import it.zano.shareride.optimization.io.RouteDoabilityResponse;
import it.zano.shareride.persistence.PersistenceController;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.persistence.entities.VehicleEntity;
import it.zano.shareride.rest.base.services.BaseService;
import it.zano.shareride.rest.booking.io.BookingRequest;
import it.zano.shareride.rest.booking.io.BookingResponse;
import it.zano.shareride.rest.booking.utils.BookingServiceUtils;

@Path("/bookingService")
public class BookingService extends BaseService {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/uploadRequest")
	public BookingResponse uploadRequest(BookingRequest bookingRequest){
		
		//Preparing the controllers
		PersistenceController persistenceController = PersistenceController.getInstance();
		RouteOptimizationController routeOptimizationController = new RouteOptimizationController();
		
		//Load from the db the previous request
		DateTime dateTime = BookingServiceUtils.getDateTime(bookingRequest);
		String areaId = bookingRequest.getAdditionalInfo().getAreaId();
		List<UserRequestEntity> previousRequests = persistenceController.loadPreviousRequests(dateTime,areaId);
		
		//Load from the db the vehicles
		List<VehicleEntity> availableTransports = persistenceController.loadAvailableTransports(dateTime,areaId);
		
		//Get the new request (calculating lat and lon if we don't know them)
		UserRequestEntity newRequest = BookingServiceUtils.convertRequest(bookingRequest);
		previousRequests.add(newRequest);
		
		//Asking graphhopper if the new route is viable
		RouteDoabilityRequest doabilityRequest = new RouteDoabilityRequest();
		doabilityRequest.setRequests(previousRequests);
		doabilityRequest.setAvailableTransports(availableTransports);
		RouteDoabilityResponse doabilityResponse = routeOptimizationController.assessDoability(doabilityRequest);
		
		//Saving in persistence the response
		persistenceController.saveNewRequest(newRequest,doabilityResponse);
		
		//Preparing the response
		BookingResponse bookingResponse = BookingServiceUtils.convertResponse(doabilityResponse);
		
		return bookingResponse;
	}



}

package it.zano.shareride.booking.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.zano.shareride.booking.entities.BookingRequest;
import it.zano.shareride.booking.entities.BookingResponse;
import it.zano.shareride.booking.service.utils.BookingEntitiesController;
import it.zano.shareride.optimization.RouteOptimizationController;
import it.zano.shareride.optimization.RouteDoabilityRequest;
import it.zano.shareride.optimization.RouteDoabilityResponse;
import it.zano.shareride.persistence.PersistenceInput;
import it.zano.shareride.persistence.PersistenceController;

@Path("/bookingService")
public class BookingService extends BaseService {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/uploadRequest")
	public BookingResponse uploadRequest(BookingRequest bookingRequest){
		
		//Preparing the controllers
		BookingEntitiesController bookingEntitiesController = new BookingEntitiesController();
		PersistenceController persistenceController = new PersistenceController();
		RouteOptimizationController routeOptimizationController = new RouteOptimizationController();
		
		//Load from the db the previous request
		bookingEntitiesController.setBookingRequest(bookingRequest);
		PersistenceInput input = bookingEntitiesController.getPersistenceInput();
		List<BookingRequest> previousRequests = persistenceController.getPreviousRequests(input);
		
		//Get the new request (calculating lat and lon if we don't know them)
		BookingRequest newRequest = bookingEntitiesController.getNewRequest();
		previousRequests.add(newRequest);
		
		//Asking graphhopper if the new route is viable
		RouteDoabilityRequest doabilityRequest = new RouteDoabilityRequest();
		doabilityRequest.setRequests(previousRequests);
		RouteDoabilityResponse doabilityResponse = routeOptimizationController.assessDoability(doabilityRequest);
		
		//Saving in persistence the response
		persistenceController.saveNewRequest(newRequest,doabilityResponse);
		
		//Preparing the response
		BookingResponse bookingResponse = bookingEntitiesController.createResponse(doabilityResponse);
		
		return bookingResponse;
	}



}

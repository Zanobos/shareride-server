package it.zano.shareride.rest.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.LocalDate;

import it.zano.shareride.optimization.RouteOptimizationController;
import it.zano.shareride.optimization.io.RouteDoabilityRequest;
import it.zano.shareride.optimization.io.RouteDoabilityResponse;
import it.zano.shareride.persistence.PersistenceController;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.persistence.entities.VehicleEntity;
import it.zano.shareride.rest.service.base.BaseService;
import it.zano.shareride.rest.service.booking.io.CheckPathRequest;
import it.zano.shareride.rest.service.booking.io.CheckPathResponse;
import it.zano.shareride.rest.service.booking.io.ConfirmRequestRequest;
import it.zano.shareride.rest.service.booking.io.ConfirmRequestResponse;
import it.zano.shareride.rest.service.booking.utils.BookingServiceUtils;
import it.zano.shareride.rest.service.exception.ApplicationException;
import it.zano.shareride.utils.EnumStatus;

@Path("/bookingService")
public class BookingService extends BaseService {
	
	private static final Logger log = Logger.getLogger(BookingService.class.getName());
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/checkPath")
	public CheckPathResponse checkPath(CheckPathRequest checkPathRequest) throws ApplicationException, InterruptedException{
		
		log.log(Level.INFO, "REQUEST:<<" + checkPathRequest.toString() + ">>");
		
		//Preparing the controllers
		PersistenceController persistenceController = PersistenceController.getInstance();
		RouteOptimizationController routeOptimizationController = new RouteOptimizationController();
		
		//Load from the db the previous requests of the same day
		LocalDate date = BookingServiceUtils.getDate(checkPathRequest);
		List<UserRequestEntity> previousRequests = persistenceController.loadPreviousRequests(date);
		
		//Load from the db the vehicles
		List<VehicleEntity> availableTransports = persistenceController.loadAvailableVehicles(date,null);
		
		//Get the new request (calculating lat and lon if we don't know them)
		UserRequestEntity newRequest = BookingServiceUtils.convertRequest(checkPathRequest);
		previousRequests.add(newRequest);
		
		//Asking graphhopper if the new route is viable
		RouteDoabilityRequest doabilityRequest = new RouteDoabilityRequest();
		doabilityRequest.setRequests(previousRequests);
		doabilityRequest.setAvailableTransports(availableTransports);
		
		//Saving the request in persistence (in order to generate an id to all the entities)
		persistenceController.saveRequest(newRequest);
		
		//Assigning the response to the request
		RouteDoabilityResponse doabilityResponse = routeOptimizationController.assessDoability(doabilityRequest);
		doabilityResponse.setRequestId(newRequest.getId());
		
		//Updating the status
		newRequest.setStatus(doabilityResponse.getStatus());
		persistenceController.updateRequest(newRequest);
		
		//Preparing the response
		CheckPathResponse checkPathResponse = BookingServiceUtils.convertCheckPathResponse(doabilityResponse);
		
		log.log(Level.INFO, "RESPONSE:<<" + checkPathResponse.toString() + ">>");
		
		return checkPathResponse;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/confirmRequest")
	public ConfirmRequestResponse confirmRequest(ConfirmRequestRequest confirmRequest) throws ApplicationException, InterruptedException{
		
		log.log(Level.INFO, "REQUEST:<<" + confirmRequest.toString() + ">>");
		
		//Preparing the controllers
		PersistenceController persistenceController = PersistenceController.getInstance();
		
		//Load from the DB the previous request
		UserRequestEntity request = persistenceController.loadRequest(confirmRequest.getRequestId());
		request.setStatus(EnumStatus.CONFIRMED);
		persistenceController.updateRequest(request);
		
		ConfirmRequestResponse confirmResponse = new ConfirmRequestResponse();
		confirmResponse.setRequestId(request.getId());
		confirmResponse.setStatus(request.getStatus());
		
		log.log(Level.INFO, "RESPONSE:<<" + confirmResponse.toString() + ">>");
		
		return confirmResponse;
	}



}

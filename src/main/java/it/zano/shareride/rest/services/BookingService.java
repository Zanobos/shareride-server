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
import it.zano.shareride.rest.service.booking.io.BookingRequest;
import it.zano.shareride.rest.service.booking.io.BookingResponse;
import it.zano.shareride.rest.service.booking.io.CheckPathRequest;
import it.zano.shareride.rest.service.booking.io.CheckPathResponse;
import it.zano.shareride.rest.service.booking.utils.BookingServiceUtils;
import it.zano.shareride.rest.service.exception.ApplicationException;

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
		List<VehicleEntity> availableTransports = persistenceController.loadAvailableTransports(date,null);
		
		//Get the new request (calculating lat and lon if we don't know them)
		UserRequestEntity newRequest = BookingServiceUtils.convertRequest(checkPathRequest);
		previousRequests.add(newRequest);
		
		//Asking graphhopper if the new route is viable
		RouteDoabilityRequest doabilityRequest = new RouteDoabilityRequest();
		doabilityRequest.setRequests(previousRequests);
		doabilityRequest.setAvailableTransports(availableTransports);
		RouteDoabilityResponse doabilityResponse = routeOptimizationController.assessDoability(doabilityRequest);
		newRequest.setStatus(doabilityResponse.getStatus());
		
		doabilityResponse.setRequestId(newRequest.getId());
		
		//Preparing the response
		CheckPathResponse checkPathResponse = BookingServiceUtils.convertCheckPathResponse(doabilityResponse);
		
		log.log(Level.INFO, "RESPONSE:<<" + checkPathResponse.toString() + ">>");
		
		return checkPathResponse;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/uploadRequest")
	public BookingResponse uploadRequest(BookingRequest bookingRequest) throws ApplicationException, InterruptedException{
		
		//Preparing the controllers
		PersistenceController persistenceController = PersistenceController.getInstance();
		RouteOptimizationController routeOptimizationController = new RouteOptimizationController();
		
		//Load from the db the previous request
		LocalDate date = BookingServiceUtils.getDate(bookingRequest);
		String areaId = bookingRequest.getAdditionalInfo().getAreaId();
		List<UserRequestEntity> previousRequests = persistenceController.loadPreviousRequests(date,areaId);
		
		//Load from the db the vehicles
		List<VehicleEntity> availableTransports = persistenceController.loadAvailableTransports(date,areaId);
		
		//Get the new request (calculating lat and lon if we don't know them)
		UserRequestEntity newRequest = BookingServiceUtils.convertRequest(bookingRequest);
		previousRequests.add(newRequest);
		//Saving in persistence the response
		persistenceController.saveNewRequest(newRequest);
		
		//Asking graphhopper if the new route is viable
		RouteDoabilityRequest doabilityRequest = new RouteDoabilityRequest();
		doabilityRequest.setRequests(previousRequests);
		doabilityRequest.setAvailableTransports(availableTransports);
		RouteDoabilityResponse doabilityResponse = routeOptimizationController.assessDoability(doabilityRequest);
		newRequest.setStatus(doabilityResponse.getStatus());
		
		doabilityResponse.setRequestId(newRequest.getId());
		
		//Preparing the response
		BookingResponse bookingResponse = BookingServiceUtils.convertResponse(doabilityResponse);
		
		return bookingResponse;
	}



}

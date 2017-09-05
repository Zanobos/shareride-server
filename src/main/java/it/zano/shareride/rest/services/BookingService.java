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
import it.zano.shareride.persistence.entities.RouteEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.persistence.entities.VehicleEntity;
import it.zano.shareride.rest.service.base.BaseService;
import it.zano.shareride.rest.service.booking.io.CheckPathRequest;
import it.zano.shareride.rest.service.booking.io.CheckPathResponse;
import it.zano.shareride.rest.service.booking.io.ConfirmRequestRequest;
import it.zano.shareride.rest.service.booking.io.ConfirmRequestResponse;
import it.zano.shareride.rest.service.booking.utils.BookingServiceUtils;
import it.zano.shareride.rest.service.exception.ApplicationException;
import it.zano.shareride.utils.EnumRouteStatus;
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
		String requestId = newRequest.getId();
		
		//Assigning the response to the request
		RouteDoabilityResponse doabilityResponse = routeOptimizationController.assessDoability(doabilityRequest);
		EnumStatus status = doabilityResponse.getStatus();
		
		//Updating the status
		newRequest.setStatus(status);
		persistenceController.updateRequest(newRequest);
		
		//Recupero le rotte create dal servizio
		List<RouteEntity> routes = doabilityResponse.getRoutes();
		RouteEntity routeEntity = routes.isEmpty() ? null : routes.get(0);
		
		//Salvo la rotta
		persistenceController.saveRoute(routeEntity); //salvo la rotta anche se incomplete - taggata UNFEASIBLE
		String routeId = routeEntity.getId(); //It can be null
		
		//Preparing the response
		CheckPathResponse checkPathResponse = new CheckPathResponse();
		checkPathResponse.setStatus(status);
		checkPathResponse.setRequestId(requestId);
		checkPathResponse.setRouteId(routeId);
		
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
		
		//Load from the DB the route
		RouteEntity route = persistenceController.loadRoute(confirmRequest.getRouteId());
		route.setRouteStatus(EnumRouteStatus.PLANNED);
		persistenceController.updateRoute(route);
		
		ConfirmRequestResponse confirmResponse = new ConfirmRequestResponse();
		confirmResponse.setRequestId(request.getId());
		confirmResponse.setStatus(request.getStatus());
		
		log.log(Level.INFO, "RESPONSE:<<" + confirmResponse.toString() + ">>");
		
		return confirmResponse;
	}



}

package it.zano.shareride.booking.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.zano.shareride.booking.entities.BookingRequest;
import it.zano.shareride.booking.entities.BookingResponse;

@Path("/bookingService")
public class BookingService extends BaseService {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/uploadRequest")
	public BookingResponse uploadRequest(BookingRequest bookingRequest){
		
		
		
		return new BookingResponse();
	}



}

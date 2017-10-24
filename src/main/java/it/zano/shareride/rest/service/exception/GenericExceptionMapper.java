package it.zano.shareride.rest.service.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import it.zano.shareride.utils.Constants;

/**
 * @author a.zanotti
 * Unchecked Exceptions should be used for everything else. This is the case of technical exceptions
 * The idea is to give the developer an easier way to debug
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	private static final Logger log = Logger.getLogger(GenericExceptionMapper.class.getName());
	
	@Override
	public Response toResponse(Throwable ex) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		//If this is already a WebApplicationException, just return it
		if (ex instanceof WebApplicationException) {
			WebApplicationException webExc = (WebApplicationException) ex;
			log.log(Level.SEVERE, webExc.getResponse().toString(), ex);
			return webExc.getResponse();
		} 
		
		errorResponse.setErrorCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		errorResponse.setStatusCode(Constants.StatusCodes.TECHNICAL_ERROR);
		errorResponse.setStatusMessage(ex.getMessage());
		errorResponse.setDeveloperMessage(ex.toString());
		
		log.log(Level.SEVERE, errorResponse.toString(), ex);
		
		return Response.status(errorResponse.getErrorCode())
				.entity(errorResponse)
				.type(MediaType.APPLICATION_JSON)
				.build();
		
	}

}

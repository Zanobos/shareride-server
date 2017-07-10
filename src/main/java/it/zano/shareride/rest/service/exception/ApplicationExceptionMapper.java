package it.zano.shareride.rest.service.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author a.zanotti
 * Here we send responses of business exceptions
 */
@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

	private static final Logger log = Logger.getLogger(ApplicationExceptionMapper.class.getName());
	
	@Override
	public Response toResponse(ApplicationException exception) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setErrorCode(exception.getErrorCode());
		errorResponse.setStatusCode(exception.getStatusCode());
		errorResponse.setStatusMessage(exception.getStatusMessage());
		errorResponse.setDeveloperMessage(exception.getDeveloperMessage());
		
		log.log(Level.SEVERE, errorResponse.toString(), exception);
		
		return Response.status(errorResponse.getErrorCode())
				.entity(errorResponse)
				.type(MediaType.APPLICATION_JSON)
				.build();
		
	}

}

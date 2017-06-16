package it.zano.shareride.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/forcesService")
public class MainService {

	private static final Logger log = Logger.getLogger(MainService.class.getName());

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/doService")
	public String doService(String request) throws Exception {

		log.log(Level.INFO, "doService");

		return request;
	}

}

package it.zano.shareride.booking.service;

import java.util.logging.Logger;

public abstract class BaseService {
	
	protected Logger log;
	
	public BaseService() {
		log = Logger.getLogger(getClass().getName());
	}

}

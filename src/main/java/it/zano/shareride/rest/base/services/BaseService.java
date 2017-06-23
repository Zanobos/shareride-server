package it.zano.shareride.rest.base.services;

import java.util.logging.Logger;

public abstract class BaseService {
	
	protected Logger log;
	
	public BaseService() {
		log = Logger.getLogger(getClass().getName());
	}

}

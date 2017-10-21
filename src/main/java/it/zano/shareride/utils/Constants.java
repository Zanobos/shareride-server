package it.zano.shareride.utils;

public class Constants {
	
	private Constants() {} //Impossible to instantiate
	
	public class StatusCodes {
		
		private StatusCodes() {} //Impossible to instantiate
		
		public static final String TECHNICAL_ERROR = "TECHNICAL ERROR";
		public static final String BUSINESS_ERROR = "BUSINESS ERROR";
		
		public static final String ACCEPTED = "ACCEPTED";
		public static final String EVALUATING = "EVALUATING";
		public static final String REJECTED = "REJECTED";
	}
	
	public class StatusMessages {
		
		private StatusMessages() {} //Impossible to instantiate
		
		public static final String ACCEPTED = "Your request has been accepted";
		public static final String EVALUATING = "Your request is under evaluation. Wait for further information";
		public static final String REJECTED = "Your request has been declined. We are not able to grant the service requested";
	}

	public class FileSystem {
		
		private FileSystem() {} //Impossible to instantiate
		
		public static final String SEPARATOR = "/";
		public static final String DIR_CONFIG = "config";
		public static final String DIR_ROUTE_REQUESTS = "routerequests";
		public static final String DIR_BOOKING_REQUESTS = "bookingrequests";
		public static final String DIR_VEHICLES = "vehicles";
		public static final String FILE_PROPERTIES = "server.properties";
		public static final String FILE_HIBERNATE = "hibernate.cfg.xml";
		
		
	}
}

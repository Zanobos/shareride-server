package it.zano.shareride;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import it.zano.shareride.persistence.PersistenceController;
import it.zano.shareride.persistence.entities.VehicleEntity;
import it.zano.shareride.rest.service.booking.io.BookingRequest;
import it.zano.shareride.rest.service.booking.io.BookingResponse;
import it.zano.shareride.rest.service.exception.ApplicationException;
import it.zano.shareride.rest.services.BookingService;
import it.zano.shareride.utils.EnumStatus;

public class RestTest {
	
	private static BookingService service;
	private static PersistenceController persistenceController;
	
	@BeforeClass
	public static void setUp() {
		service = new BookingService();
		persistenceController = PersistenceController.getInstance();
	}
	
	@Test
	public void testBookingService1() throws ApplicationException, InterruptedException {
		
		//TODO - recreate the requests with date/time
		VehicleEntity vehicle = TestUtils.createVehicle("01");
		assertNotNull(vehicle);
//		persistenceController.saveVehicle(vehicle);
		
//		BookingRequest bookingRequest = TestUtils.createRequest("01");
//		BookingResponse bookingResponse = service.uploadRequest(bookingRequest);
//		
//		assertEquals(EnumStatus.ACCEPTED,bookingResponse.getStatus());
		
	}

}

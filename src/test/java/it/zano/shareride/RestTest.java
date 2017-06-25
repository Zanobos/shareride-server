package it.zano.shareride;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import it.zano.shareride.rest.service.booking.io.BookingRequest;
import it.zano.shareride.rest.service.booking.io.BookingResponse;
import it.zano.shareride.rest.services.BookingService;

public class RestTest {
	
	private static BookingService service;
	
	@BeforeClass
	public static void setUp() {
		service = new BookingService();
	}
	
	@Test
	public void testBookingService1() {
		
		BookingRequest bookingRequest = TestUtils.createRequest("01");
		BookingResponse bookingResponse = service.uploadRequest(bookingRequest);
		
		assertNotNull(bookingResponse);
		
	}

}

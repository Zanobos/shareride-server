package it.zano.shareride;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.BeforeClass;
import org.junit.Test;

import it.zano.shareride.persistence.PersistenceController;
import it.zano.shareride.persistence.entities.LocationEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;

public class HibernateTest {
	
	private static PersistenceController controller;
	
	@BeforeClass
	public static void setUp() {
		controller = PersistenceController.getInstance();
	}
	
	@Test
	public void testHibernateRequests() {
		
		LocalDate date = new LocalDate();
		LocalTime time = new LocalTime();
		String areaId = "TO";
		
		UserRequestEntity userRequest = new UserRequestEntity();
		
		userRequest.setAreaId(areaId);
		userRequest.setLocalDate(date);
		userRequest.setNeedAssistance(false);
		userRequest.setNumberOfSeats(1);
		userRequest.setUserName("Andrea Zanotti");
		
		LocationEntity pickup = new LocationEntity();
		pickup.setAddress("Via della Lepre, 43");
		pickup.setDate(date);
		pickup.setTime(time);
		pickup.setLat(44.2189271);
		pickup.setLocationName("Casa di Vale");
		pickup.setLon(11.9965901);
		userRequest.setPickup(pickup);
		
		LocationEntity delivery = new LocationEntity();
		delivery.setAddress("Via Cassirano, 54");
		delivery.setDate(date);
		delivery.setTime(time);
		delivery.setLat(44.2457233);
		delivery.setLocationName("Casa di Andrea");
		delivery.setLon(12.0042451);
		userRequest.setDelivery(delivery);
		
		controller.saveNewRequest(userRequest);
		
		List<UserRequestEntity> previousRequests = controller.loadPreviousRequests(date, areaId);
		
		assertEquals(1,previousRequests.size());
		
		assertEquals("Casa di Vale", previousRequests.get(0).getPickup().getLocationName());
	}

}

package it.zano.shareride;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import it.zano.shareride.persistence.PersistenceController;
import it.zano.shareride.persistence.entities.UserRequestEntity;

public class HibernateTest {
	
	private static PersistenceController controller;
	
	@BeforeClass
	public static void setUp() {
		controller = PersistenceController.getInstance();
	}
	
	@Test
	public void testLoadPreviousRequests() {
		DateTime dateTime = new DateTime();
		String areaId = "TO";
		List<UserRequestEntity> previousRequests = controller.loadPreviousRequests(dateTime, areaId);
		assertNotNull(previousRequests);
	}

}

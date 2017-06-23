package it.zano.shareride.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.joda.time.DateTime;

import it.zano.shareride.optimization.io.RouteDoabilityResponse;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.persistence.entities.VehicleEntity;

public class PersistenceController {

	public static final String LOCATIONS = "LOCATIONS";
	public static final String USER_REQUESTS = "USER_REQUESTS";
	public static final String VEHICLES = "VEHICLES";
	
	private static PersistenceController instance;
	private static final Logger log = Logger.getLogger(PersistenceController.class.getName());

	private SessionFactory sessionFactory;

	private PersistenceController() {
		log.log(Level.INFO, "Initializing Hibernate");
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() /* configures settings from hibernate.cfg.xml */
				.build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
			log.log(Level.SEVERE, "Hibernate initialization failed",e);
		}
	}

	public static PersistenceController getInstance() {
		if (instance == null) {
			instance = new PersistenceController();
		}
		return instance;
	}

	/**
	 * I load from the db the previous requests
	 * 
	 * @param input
	 * @return
	 */
	public List<UserRequestEntity> loadPreviousRequests(DateTime date, String areaId) {
		//TODO date
		String hql = "FROM " + USER_REQUESTS + " R WHERE E.areaId = :area_id";
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setParameter(":area_id", areaId);
		
		@SuppressWarnings("unchecked")
		List<UserRequestEntity> list = query.list();
		
		session.getTransaction().commit();
        session.close();
        
		return list;
	}

	/**
	 * Saving a new request
	 * 
	 * @param newRequest
	 * @param doabilityResponse
	 */
	public void saveNewRequest(UserRequestEntity newRequest, RouteDoabilityResponse doabilityResponse) {
		// TODO Auto-generated method stub

	}

	public List<VehicleEntity> loadAvailableTransports(DateTime date, String areaId) {
		// TODO Auto-generated method stub
		return null;
	}

}

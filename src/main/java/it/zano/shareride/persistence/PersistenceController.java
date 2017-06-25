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

import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.persistence.entities.VehicleEntity;
import it.zano.shareride.utils.Constants;

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
				.configure(Constants.FileSystem.DIR_CONFIG + Constants.FileSystem.SEPARATOR + Constants.FileSystem.FILE_HIBERNATE) 
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
	 * Loading from the db the previous requests
	 * @param input
	 * @return
	 */
	public List<UserRequestEntity> loadPreviousRequests(DateTime date, String areaId) {

		String hql = "FROM UserRequestEntity R WHERE R.areaId = :area_id";
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(hql);
		query.setParameter("area_id", areaId);
		
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
	 */
	public void saveNewRequest(UserRequestEntity newRequest) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(newRequest);
		
		session.getTransaction().commit();
		session.close();

	}

	/**
	 * Loading from the db available transports
	 * @param date
	 * @param areaId
	 * @return
	 */
	public List<VehicleEntity> loadAvailableTransports(DateTime date, String areaId) {

		String hql = "FROM VehicleEntity V WHERE V.areaId = :area_id";
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(hql);
		query.setParameter("area_id", areaId);
		
		@SuppressWarnings("unchecked")
		List<VehicleEntity> list = query.list();
		
		session.getTransaction().commit();
        session.close();
        
		return list;
	}

}

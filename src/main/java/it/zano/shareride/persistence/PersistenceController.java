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
import org.joda.time.LocalDate;

import it.zano.shareride.persistence.entities.LocationEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.persistence.entities.VehicleEntity;
import it.zano.shareride.persistence.entities.VehicleTypeEntity;
import it.zano.shareride.utils.Constants;
import it.zano.shareride.utils.PropertiesLoader;

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
			String environment = PropertiesLoader.getProperty("environment");
			if(environment.equals("TEST")){
				log.log(Level.INFO, "Adding a default vehicle to the db");
				saveDefaultVehicle(); //In order to test
			}
			
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
			log.log(Level.SEVERE, "Hibernate initialization failed",e);
		}
	}

	public static synchronized PersistenceController getInstance() {
		if (instance == null) {
			instance = new PersistenceController();
		}
		return instance;
	}

	/**
	 * Loading from the db the previous requests
	 * @return
	 */
	public List<UserRequestEntity> loadPreviousRequests(LocalDate date, String areaId) {

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
	 * Loading from the db the previous requests
	 * @return
	 */
	public List<UserRequestEntity> loadPreviousRequests(LocalDate date) {

		String hql = "FROM UserRequestEntity";
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(hql);
		
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
	public List<VehicleEntity> loadAvailableTransports(LocalDate date, String areaId) {

		String hql = "FROM VehicleEntity";
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(hql);
		
		@SuppressWarnings("unchecked")
		List<VehicleEntity> list = query.list();
		
		session.getTransaction().commit();
        session.close();
        
		return list;
	}
	
	/**
	 * Saving a new request
	 * 
	 * @param vehicleEntity
	 */
	public void saveVehicle(VehicleEntity vehicleEntity) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(vehicleEntity);
		
		session.getTransaction().commit();
		session.close();

	}
	
	/**
	 * Default vehicle loaded in test environment
	 */
	private void saveDefaultVehicle() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		VehicleEntity vehicle = new VehicleEntity();
		vehicle.setVehicleId("Van");
		VehicleTypeEntity type = new VehicleTypeEntity();
		type.setCapacity(8);
		type.setTypeId("van");
		vehicle.setType(type);
		LocationEntity startAddress = new LocationEntity();
		startAddress.setAddress("Via Gianfrancesco Fiochetto, 23, 10152 Torino TO");
		startAddress.setLat(45.076108);
		startAddress.setLon(7.688482);
		startAddress.setLocationName("Autorimessa");
		vehicle.setStartAddress(startAddress);
		session.save(vehicle);
		
		session.getTransaction().commit();
		session.close();
		
	}

}

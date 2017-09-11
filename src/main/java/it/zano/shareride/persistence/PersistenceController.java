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
import it.zano.shareride.persistence.entities.RouteEntity;
import it.zano.shareride.persistence.entities.RouteLocationEntity;
import it.zano.shareride.persistence.entities.UserRequestEntity;
import it.zano.shareride.persistence.entities.VehicleEntity;
import it.zano.shareride.persistence.entities.VehicleTypeEntity;
import it.zano.shareride.utils.Constants;
import it.zano.shareride.utils.EnumRouteStatus;
import it.zano.shareride.utils.EnumStatus;
import it.zano.shareride.utils.PropertiesLoader;

public class PersistenceController {

	public static final String LOCATIONS = "LOCATIONS";
	public static final String USER_REQUESTS = "USER_REQUESTS";
	public static final String VEHICLES = "VEHICLES";
	public static final String VEHICLE_TYPES = "VEHICLE_TYPES";
	public static final String ROUTES = "ROUTES";
	public static final String ROUTE_LOCATIONS = "ROUTE_LOCATIONS";
	
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
	public List<UserRequestEntity> loadPreviousRequests(String userId, LocalDate localDate) {

		String hql = "FROM UserRequestEntity R WHERE R.userId = :userId AND R.status = :status";
		
		if(localDate!= null) {
			hql = hql + " AND R.localDate = :localDate";
		}
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("status", EnumStatus.CONFIRMED);
		if(localDate!=null) {
			query.setParameter("localDate", localDate);
		}
		
		@SuppressWarnings("unchecked")
		List<UserRequestEntity> list = query.list();
		
		session.getTransaction().commit();
        session.close();
        
		return list;
	}
	
	/**
	 * Loading from the db the previous requests of the same date, and CONFIRMED
	 * @return
	 */
	public List<UserRequestEntity> loadPreviousRequests(LocalDate date) {

		String hql = "FROM UserRequestEntity R WHERE R.localDate = :local_date AND R.status = :status";
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(hql);
		query.setParameter("local_date", date);
		query.setParameter("status", EnumStatus.CONFIRMED);
		
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
	public void saveRequest(UserRequestEntity newRequest) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(newRequest);
		
		session.getTransaction().commit();
		session.close();

	}
	
	/**
	 * Retrieving the request
	 * @param requestId
	 * @return
	 */
	public UserRequestEntity loadRequest(String requestId) {
		
		String hql = "FROM UserRequestEntity R WHERE R.id = :id";
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(hql);
		query.setParameter("id", requestId);
		
		UserRequestEntity userRequest = (UserRequestEntity) query.uniqueResult();
		
		session.getTransaction().commit();
		session.close();
		
		return userRequest;
	}
	
	/**
	 * Updating the request
	 * (the request must come from loadRequest)
	 * @param request
	 */
	public void updateRequest(UserRequestEntity request) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.update(request);
		
		session.getTransaction().commit();
		session.close();
		
	}

	/**
	 * Loading from the db available transports
	 * @param date
	 * @param areaId
	 * @return
	 */
	public List<VehicleEntity> loadAvailableVehicles(LocalDate date, String areaId) {

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
	 * Load the vehicle identified by its id
	 * @param vehicleId
	 * @return
	 */
	public VehicleEntity loadVehicle(String vehicleId) {

		String hql = "FROM VehicleEntity R WHERE R.id = :id";

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery(hql);
		query.setParameter("id", vehicleId);

		VehicleEntity userRequest = (VehicleEntity) query.uniqueResult();

		session.getTransaction().commit();
		session.close();

		return userRequest;

	}
	
	/**
	 * Saving a new vehicle
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
	 * Saving a new route
	 * @param routeEntity
	 */
	public void saveRoute(RouteEntity routeEntity) {
		
		if(routeEntity == null)
			return;
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		//Assigning the vehicle
		String vehicleId = routeEntity.getVehicleId();
		VehicleEntity vehicle = loadVehicle(vehicleId);
		routeEntity.setVehicle(vehicle);
		
		//Assigning the objects
		for(RouteLocationEntity routeLocation: routeEntity.getRouteLocations()) {
			
			String locationEntityId = routeLocation.getLocationEntityId();
			
			LocationEntity location = loadLocation(locationEntityId);
			
			routeLocation.setLocationEntity(location);
		}
		
		session.save(routeEntity);
		
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Retrieving the route
	 * @param routeId
	 * @return
	 */
	public RouteEntity loadRoute(String routeId) {
		
		String hql = "FROM RouteEntity R WHERE R.id = :id";
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(hql);
		query.setParameter("id", routeId);
		
		RouteEntity route = (RouteEntity) query.uniqueResult();
		
		session.getTransaction().commit();
		session.close();
		
		return route;
	}
	
	/**
	 * Updating the route
	 * Se un altro utente ha fatto richieste, potrebbe aver creato una nuova rotta. Quindi, entro dentro tutte le LocationEntity
	 * di questa rotta. Ad ogni LocationEntity associo questa rotta come quella in corso. Se era già presente una rotta, quella rotta
	 * diventa outdated 
	 * @param route
	 */
	public void updateRoute(RouteEntity route) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		if(route.getRouteStatus().equals(EnumRouteStatus.PLANNED)) {
			
			for(RouteLocationEntity routeLocationEntity : route.getRouteLocations()) {
				
				LocationEntity locationEntity = routeLocationEntity.getLocationEntity();
				RouteEntity oldroute = locationEntity.getRoute();
				if(oldroute != null && !oldroute.getId().equals(route.getId())) {
					oldroute.setRouteStatus(EnumRouteStatus.OUTDATED);
					session.update(oldroute);
				}
				
				locationEntity.setRoute(route);
				session.update(locationEntity);
			}
		}
		
		session.update(route);
		
		session.getTransaction().commit();
		session.close();
		
	}
	
	/**
	 * Load the location identified by its id
	 * @param locationId
	 * @return
	 */
	public LocationEntity loadLocation(String locationId) {
		String hql = "FROM LocationEntity R WHERE R.id = :id";

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery(hql);
		query.setParameter("id", locationId);

		LocationEntity userRequest = (LocationEntity) query.uniqueResult();

		session.getTransaction().commit();
		session.close();

		return userRequest;
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

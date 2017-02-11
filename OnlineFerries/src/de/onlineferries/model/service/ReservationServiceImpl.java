package de.onlineferries.model.service;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.onlineferries.entity.Cabin;
import de.onlineferries.entity.CabinReservation;
import de.onlineferries.entity.Customer;
import de.onlineferries.entity.Reservation;
import de.onlineferries.entity.Route;
import de.onlineferries.entity.ShipCabin;
import de.onlineferries.entity.Travellers;
import de.onlineferries.entity.Trip;
import de.onlineferries.view.CustomerView;
import de.onlineferries.view.ReservationView;
import de.onlineferries.view.RouteView;
import de.onlineferries.view.ShipCabinView;
import de.onlineferries.view.ShipView;
import de.onlineferries.view.TravellerView;
import de.onlineferries.view.TripView;

public class ReservationServiceImpl implements Serializable, ReservationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public double getReservationPrice(int trip_id, List<ShipCabinView> shipCabins, int cars, int travellers) {
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		Trip trip = em.find(Trip.class, trip_id);
		double roomcost = 0.0;
		for (ShipCabinView cab : shipCabins) {
			roomcost += cab.getPrice() * cab.getRes_count();
		}
		return trip.getPrice_car() * cars + trip.getPrice_passenger() * travellers + roomcost;
	}

	@Override
	public void insertReservation(ReservationView res) {
		Reservation reservation = convertReservation(res);
		System.out.println("insert starts");
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		try{
			em.persist(reservation);
		}
		catch(Exception e){
			
		}
		
		em.getTransaction().commit();
	}

	@Override
	public List<ReservationView> getReservations(CustomerView customer) {
		List<ReservationView> reserved = new ArrayList<ReservationView>();
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		Query q1 = em.createQuery("from " + Reservation.class.getName() + " where customer_id = :id");
		q1.setParameter("id", customer.getCustomer_id());
		
		for(Object r : q1.getResultList()){
			Reservation res = (Reservation) r;
			ReservationView reservation = new ReservationView();
			reservation.setCars(res.getCars());
			reservation.setReservation_id(res.getId());
			reservation.setCustomer(customer);
						
			List<TravellerView> travels = new ArrayList<TravellerView>();
			for(Travellers t : res.getTravellers()){
				TravellerView trav = new TravellerView(t.getId(), t.getFullName());
				travels.add(trav);
			}			
			reservation.setTravellerNames(travels);
			
			TripView trip = new TripView(res.getTrip().getId(), res.getTrip().getDate(), res.getTrip().getDeparture(), 
					res.getTrip().getArrival(), res.getTrip().getPrice_car(), res.getTrip().getPrice_passenger());
			reservation.setTrip(trip);
			
			ShipService shipService = new ShipServiceImpl();
			List<ShipCabinView> cabins = new ArrayList<ShipCabinView>();
			Query q2 = em.createQuery("from " + CabinReservation.class.getName() + " where reservation_id = :id");
			q2.setParameter("id", reservation.getReservation_id());
			for(Object c : q2.getResultList()){
				CabinReservation cab = (CabinReservation) c;
				Query q3 = em.createQuery("from " + ShipCabin.class.getName() + " where ship_id = :ship and cabintype_id = :cabin");
				q3.setParameter("ship", res.getTrip().getRoute().getShip());
				q3.setParameter("cabin", cab.getCabin());
				
				System.out.println(res.getTrip().getRoute().getShip().getId());
				System.out.println(cab.getCabin_index());
				ShipCabin shipcabin = (ShipCabin) q3.getSingleResult();
				ShipCabinView shipcab = new ShipCabinView(shipcabin.getCabin().getId(), shipcabin.getCabin().getDescription(), cab.getCount(), shipcabin.getPrice());
				shipcab.setRes_count(cab.getCount());
				
				cabins.add(shipcab);
			}
			Route rout =res.getTrip().getRoute();
			RouteView route = new RouteView(rout.getId(), rout.getStart(), rout.getDestination());
			ShipView ship = new ShipView(res.getTrip().getRoute().getShip().getId(), 
					res.getTrip().getRoute().getShip().getCars(), res.getTrip().getRoute().getShip().getPassengers(),
					res.getTrip().getRoute().getShip().getDescription());
			
			route.setShip(ship);
					
			
			reservation.setRoute(route);
			reservation.setShipCabins(cabins);
			
			reserved.add(reservation);
		}
		
		return reserved;
	}
	
	public void updateReservation(ReservationView res){
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		Reservation reservation = convertReservation(res);
		
		em.getTransaction().begin();
		em.remove(em.find(Reservation.class, res.getReservation_id()));		
		em.getTransaction().commit();
		
		insertReservation(res);
		
	}
	
	private Reservation convertReservation(ReservationView res){
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		Reservation reservation = new Reservation();

		em.getTransaction().begin();
		Customer c = em.find(Customer.class, res.getCustomer().getCustomer_id());
		c.getReservations().add(reservation);
		reservation.setCustomer(c);

		Trip t = em.find(Trip.class, res.getTrip().getTrip_id());
		reservation.setTrip(t);
		
		List<Travellers> travelswith = new ArrayList<Travellers>();
		if(!res.getTravellerNames().isEmpty())
		{
			for(TravellerView trav : res.getTravellerNames()){
				Travellers travels = new Travellers(trav.getName());
				travels.setReservation(reservation);
				travelswith.add(travels);
				em.persist(travels);
			}
		}
		reservation.setTravellers(travelswith);
		
		List<CabinReservation> cabinsbooked = new ArrayList<CabinReservation>();		
		for(ShipCabinView cab : res.getShipCabins()){
			CabinReservation reservated = new CabinReservation();
			reservated.setCount(cab.getCount());
			reservated.setReservation(reservation);
			
			Cabin cabin = em.find(Cabin.class, cab.getCabin_id());
			reservated.setCabin(cabin);
			reservated.setCabin_index(cab.getCabin_index());
			cabinsbooked.add(reservated);
		}		
		reservation.setCabins(cabinsbooked);		
		reservation.setCars(res.getCars());
		em.getTransaction().commit();
		em.close();
		return reservation;
	}
}

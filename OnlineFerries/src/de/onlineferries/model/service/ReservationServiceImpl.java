package de.onlineferries.model.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import de.onlineferries.entity.Customer;
import de.onlineferries.entity.Reservation;
import de.onlineferries.entity.Trip;
import de.onlineferries.view.ReservationView;
import de.onlineferries.view.ShipCabinView;

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
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		Reservation reservation = new Reservation();

		em.getTransaction().begin();
		Customer c = em.find(Customer.class, res.getCustomer().getCustomer_id());
		c.getReservations().add(reservation);
		reservation.setCustomer(c);

		Trip t = em.find(Trip.class, res.getTrip().getTrip_id());
		reservation.setTrip(t);

		reservation.setCars(res.getCars());
		em.persist(c);
		em.persist(reservation);
		em.getTransaction().commit();

	}
}

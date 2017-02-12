package de.onlineferries.model.service;


import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.onlineferries.entity.CabinReservation;
import de.onlineferries.entity.Reservation;
import de.onlineferries.entity.Ship;
import de.onlineferries.entity.ShipCabin;
import de.onlineferries.entity.Trip;
import de.onlineferries.view.ShipCabinView;
import de.onlineferries.view.TripView;

public class FreeSpaceServiceImpl implements FreeSpaceService{

	@Override
	public boolean ismissingspacecars(int count, TripView t) {
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		int cars = 0;
		Query q1 = em.createQuery("from " + Reservation.class.getName() + " where trip_id = :trip");
		q1.setParameter("trip", t.getTrip_id());
		
		for(Object obj : q1.getResultList()){
			Reservation res = (Reservation) obj;
			cars += res.getCars();
		}
		
		Trip trip = em.find(Trip.class, t.getTrip_id());
		if(trip.getRoute().getShip().getCars() >= cars + count)
			return true;
		return false;
	}

	@Override
	public boolean ismissingspacecabin(ShipCabinView cab, TripView t) {
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		int onboard = 0;
		
		Trip trip = em.find(Trip.class, t.getTrip_id());
		Query q1 = em.createQuery("from " + Reservation.class.getName() + " where trip_id = :trip");
		q1.setParameter("trip", t.getTrip_id());
		
		for(Object obj : q1.getResultList()){
			Reservation res = (Reservation) obj;
			Query q2 = em.createQuery("from " + CabinReservation.class.getName() + " where reservation_id = :res and cabintype_id = :cabtype");
			q2.setParameter("res", res.getId());
			q2.setParameter("cabtype", cab.getCabin_id());
			for(Object ob2 : q2.getResultList()){
				CabinReservation rescab = (CabinReservation) ob2;
				onboard += rescab.getCount();
			}
		}
		
		int cabincapacity = 0;
		
		for(ShipCabin shipcab : trip.getRoute().getShip().getShipCabin()){
			if(shipcab.getCabin().getId() == cab.getCabin_id()){
				cabincapacity += shipcab.getCount() * shipcab.getCabin().getPassengers();
			}
		}
		
		if(cabincapacity < onboard + cab.getRes_count()) return false;
		return true;
	}



}

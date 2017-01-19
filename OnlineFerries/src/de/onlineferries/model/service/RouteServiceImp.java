package de.onlineferries.model.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.onlineferries.entity.Route;
import de.onlineferries.entity.Trip;
import de.onlineferries.view.RouteView;
import de.onlineferries.view.TripView;

public class RouteServiceImp implements RouteService, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<RouteView> findAllRoutes() {
		List<RouteView> routelist = new ArrayList<RouteView>();

		for (Route route : getRoutes()) {
			RouteView convRout = new RouteView(route.getId(), route.getStart(), route.getDestination());
			routelist.add(convRout);
		}
		return routelist;
	}

	private List<Route> getRoutes() {
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		TypedQuery<Route> query = em.createNamedQuery("allRoutes", Route.class);
		List<Route> routes = query.getResultList();
		return routes;
	}

	@Override
	public List<TripView> findAllTrips(int route_id) {
		List<Trip> trips = new ArrayList<Trip>();

		for (Route r : getRoutes()) {
			if (r.getId() == route_id)
				trips = r.getTrips();
		}

		List<TripView> triplist = new ArrayList<TripView>();
		for (Trip trip : trips) {
			TripView convTrip = new TripView(trip.getId(), trip.getDate(), trip.getDeparture(), trip.getArrival(), trip.getPrice_car(),
					trip.getPrice_passenger());
			triplist.add(convTrip);
		}
		return triplist;
	}
}

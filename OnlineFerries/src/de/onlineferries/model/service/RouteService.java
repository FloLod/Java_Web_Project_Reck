package de.onlineferries.model.service;

import java.util.List;

import de.onlineferries.view.RouteView;
import de.onlineferries.view.TripView;

public interface RouteService {
	public List<RouteView> findAllRoutes();

	public List<TripView> findAllTrips(int route_id);
}

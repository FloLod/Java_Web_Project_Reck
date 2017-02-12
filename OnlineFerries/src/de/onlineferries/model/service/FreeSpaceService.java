package de.onlineferries.model.service;

import de.onlineferries.entity.Trip;
import de.onlineferries.view.ShipCabinView;
import de.onlineferries.view.TripView;

public interface FreeSpaceService {
	boolean ismissingspacecars(int count, TripView t);
	boolean ismissingspacecabin(ShipCabinView cab, TripView t);
}

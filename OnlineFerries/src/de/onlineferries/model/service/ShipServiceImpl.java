package de.onlineferries.model.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.onlineferries.entity.Route;
import de.onlineferries.entity.Ship;
import de.onlineferries.entity.ShipCabin;
import de.onlineferries.view.ShipCabinView;
import de.onlineferries.view.ShipView;

public class ShipServiceImpl implements Serializable, ShipService {

	private static final long serialVersionUID = 1L;

	@Override
	public ShipView findShip(int route_id) {
		List<Route> routes = getRoutes();
		for (Route r : routes) {
			if (r.getId() == route_id) {
				return new ShipView(r.getShip().getId(), r.getShip().getCars(), r.getShip().getPassengers(), r.getShip().getDescription());
			}
		}
		return null;
	}

	@Override
	public List<ShipCabinView> findAllShipCabins(int ship_id) {
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		TypedQuery<Ship> query = em.createNamedQuery("allSpecialShip", Ship.class);
		query.setParameter("ship", ship_id);
		Ship ship = query.getSingleResult();
		List<ShipCabinView> cabins = new ArrayList<ShipCabinView>();
		for (ShipCabin cab : ship.getShipCabin()) {
			cabins.add(new ShipCabinView(cab.getCabin().getId(), cab.getCabin().getDescription(), cab.getCabin().getPassengers(), cab
					.getPrice()));
		}
		return cabins;
	}

	private List<Route> getRoutes() {
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		TypedQuery<Route> query = em.createNamedQuery("allRoutes", Route.class);
		List<Route> routes = query.getResultList();
		return routes;
	}

}

package de.onlineferries.view;

import java.io.Serializable;
import java.util.List;

public class ReservationView implements Serializable {
	private static final long serialVersionUID = 1L;
	private int reservation_id;
	private TripView trip;
	private CustomerView customer;
	private List<ShipCabinView> shipCabins;
	private int cars;
	private List<TravellerView> travellerNames;
	private RouteView route;
	

	public RouteView getRoute() {
		return route;
	}

	public void setRoute(RouteView route) {
		this.route = route;
	}

	public int getReservation_id() {
		return reservation_id;
	}

	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}

	public TripView getTrip() {
		return trip;
	}

	public void setTrip(TripView trip) {
		this.trip = trip;
	}

	public CustomerView getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerView customer) {
		this.customer = customer;
	}

	public List<ShipCabinView> getShipCabins() {
		return shipCabins;
	}

	public void setShipCabins(List<ShipCabinView> shipCabins) {
		this.shipCabins = shipCabins;
	}

	public int getCars() {
		return cars;
	}

	public void setCars(int cars) {
		this.cars = cars;
	}

	public List<TravellerView> getTravellerNames() {
		return travellerNames;
	}

	public void setTravellerNames(List<TravellerView> travellerNames) {
		this.travellerNames = travellerNames;
	}

}

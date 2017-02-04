package de.onlineferries.controller.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import de.onlineferries.model.service.ReservationService;
import de.onlineferries.model.service.ShipService;
import de.onlineferries.view.ReservationView;
import de.onlineferries.view.ShipCabinView;
import de.onlineferries.view.TravellerView;

@ManagedBean
@SessionScoped
public class ReservationHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{serviceLocatorBean}")
	private ServiceLocator service;
	@ManagedProperty("#{routeHandler}")
	private RouteHandler routeHandler;
	@ManagedProperty("#{tripHandler}")
	private TripHandler tripHandler;
	@ManagedProperty("#{loginHandler}")
	private LoginHandler loginHandler;

	private List<ShipCabinView> shipCabins;
	private int cars;
	private int travellers;
	private double reservationPrice;
	private List<TravellerView> travellerNames;
	private List<ShipCabinView> selectedShipCabins;

	public int[] getTravellerValues() {
		return new int[] { 0, 1, 2, 3, 4, 5, 6 };
	}

	public String saveReservation() {
		try {
			ReservationService resservice = service.getReservationService();
			ReservationView res = new ReservationView();

			res.setCars(cars);
			res.setShipCabins(selectedShipCabins);
			res.setTravellerNames(travellerNames);
			res.setTrip(tripHandler.getTrip());
			res.setCustomer(loginHandler.getCustomer());

			resservice.insertReservation(res);
			return "inserted";
		} catch (Exception e) {
			return "insertionfailed";
		}

	}

	public String reservate() {
		try {
			ShipService shipService = service.getShipService();
			shipCabins = shipService.findAllShipCabins(routeHandler.getShip().getShip_id());
			selectedShipCabins = new ArrayList<ShipCabinView>();
			return "success";
		} catch (Exception e) {
			return "retry";
		}
	}

	public void changeTraveller(ValueChangeEvent ev) {
		travellers = (Integer) ev.getNewValue();
		travellerNames = new ArrayList<TravellerView>(travellers);
		for (ShipCabinView cab : shipCabins) {
			if (cab.getRes_count() > 0)
				selectedShipCabins.add(cab);
		}
		for (int id = 0; id < travellers; id++)
			travellerNames.add(new TravellerView(id, ""));
		FacesContext.getCurrentInstance().renderResponse();
	}

	public String selectCustomerType() {
		try {
			for (ShipCabinView cab : shipCabins) {
				if (cab.getRes_count() > 0)
					selectedShipCabins.add(cab);
			}

			reservationPrice = service.getReservationService().getReservationPrice(tripHandler.getTrip().getTrip_id(), selectedShipCabins,
					cars, travellers);
			return "goToLogin";
		} catch (Exception e) {
			return "retry";
		}

	}

	public void validateTravellerName() {
	}

	public LoginHandler getLoginHandler() {
		return loginHandler;
	}

	public void setLoginHandler(LoginHandler loginHandler) {
		this.loginHandler = loginHandler;
	}

	public double getReservationPrice() {
		return reservationPrice;
	}

	public void setReservationPrice(double reservationPrice) {
		this.reservationPrice = reservationPrice;
	}

	public TripHandler getTripHandler() {
		return tripHandler;
	}

	public void setTripHandler(TripHandler tripHandler) {
		this.tripHandler = tripHandler;
	}

	public List<ShipCabinView> getSelectedShipCabins() {
		return selectedShipCabins;
	}

	public void setSelectedShipCabins(List<ShipCabinView> selectedShipCabins) {
		this.selectedShipCabins = selectedShipCabins;
	}

	public ServiceLocator getService() {
		return service;
	}

	public void setService(ServiceLocator service) {
		this.service = service;
	}

	public RouteHandler getRouteHandler() {
		return routeHandler;
	}

	public void setRouteHandler(RouteHandler routeHandler) {
		this.routeHandler = routeHandler;
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

	public int getTravellers() {
		return travellers;
	}

	public void setTravellers(int travellers) {
		this.travellers = travellers;
	}

	public List<TravellerView> getTravellerNames() {
		return travellerNames;
	}

	public void setTravellerNames(List<TravellerView> travellerNames) {
		this.travellerNames = travellerNames;
	}
}

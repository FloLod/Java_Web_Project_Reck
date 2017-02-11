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
import de.onlineferries.view.CustomerView;
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

	private List<ShipCabinView> shipCabins;
	private int cars;
	private int travellers;
	private double reservationPrice;
	private List<TravellerView> travellerNames;
	private List<ShipCabinView> selectedShipCabins;
	private List<ReservationView> reserved;
	private CustomerView customer;
	private ReservationView reservation;
	
	
	public int[] getTravellerValues() {
		return new int[] { 0, 1, 2, 3, 4, 5, 6 };
	}
	
	public String prepareReservation(ReservationView reservation)
	{
		this.reservation = reservation;
		ShipService shipService = service.getShipService();
		shipCabins = shipService.findAllShipCabins(reservation.getRoute().getShip().getShip_id());
		
		routeHandler.setRoute(reservation.getRoute());
		routeHandler.setShip(reservation.getRoute().getShip());
		
		cars = reservation.getCars();
		travellers = reservation.getTravellerNames().size();
		travellerNames = reservation.getTravellerNames();

		customer = reservation.getCustomer();

		tripHandler.setTrip(reservation.getTrip());
		
		for(ShipCabinView scv : reservation.getShipCabins()){
			for(ShipCabinView sc : shipCabins){
				if(sc.getCabin_index() == scv.getCabin_index())
					sc.setRes_count(scv.getCount());
			}
		}
		travellerNames = new ArrayList<TravellerView>();
		selectedShipCabins = new ArrayList<ShipCabinView>();
		return "editReservation";
	}
	
	public void showRoutes(CustomerView customer)
	{
		reserved = service.getReservationService().getReservations(customer);
		this.customer = customer;
		
	}

	public String reservate() {
		try {
			ShipService shipService = service.getShipService();
			shipCabins = shipService.findAllShipCabins(routeHandler.getShip().getShip_id());
			selectedShipCabins = new ArrayList<ShipCabinView>();
			travellerNames = new ArrayList<TravellerView>();
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
		if(spacefree()) {			
			try {
				for (ShipCabinView cab : shipCabins) {
					if (cab.getRes_count() > 0)
						selectedShipCabins.add(cab);
				}
	
				reservationPrice = service.getReservationService().getReservationPrice(tripHandler.getTrip().getTrip_id(), selectedShipCabins,
						cars, travellers);
				return "goToLogin";
			} catch (Exception e) {
				e.printStackTrace();
				return "retry";
			}
		}
		else
			return "retry";
	}

	public String saveReservation() {
		try {
			ReservationService resservice = service.getReservationService();

			ReservationView res = new ReservationView();
			res.setCars(cars);
			res.setShipCabins(selectedShipCabins);
			res.setTravellerNames(travellerNames);
			res.setTrip(tripHandler.getTrip());
			res.setCustomer(customer);

			if(null != reservation){
				System.out.println("update");
				res.setReservation_id(reservation.getReservation_id());
				res.setRoute(reservation.getRoute());

				resservice.updateReservation(reservation);
				reservation = null;
				
			}else
				resservice.insertReservation(res);
			
			return "inserted";
		} catch (Exception e) {
			e.printStackTrace();
			return "insertionfailed";
		}

	}
	
	public void validateTravellerName() {
	}

	private boolean spacefree(){
		return true;
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

	public List<ReservationView> getReserved() {
		return reserved;
	}

	public void setReserved(List<ReservationView> reserved) {
		this.reserved = reserved;
	}

	public CustomerView getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerView customer) {
		this.customer = customer;
	}
}

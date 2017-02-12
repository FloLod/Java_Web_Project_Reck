package de.onlineferries.controller.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import javax.faces.event.ValueChangeEvent;

import de.onlineferries.model.service.FreeSpaceService;
import de.onlineferries.model.service.ReservationService;
import de.onlineferries.model.service.ShipService;
import de.onlineferries.view.CustomerView;
import de.onlineferries.view.ReservationView;
import de.onlineferries.view.ShipCabinView;
import de.onlineferries.view.TravellerView;

/**
 * @author Florian
 *
 */
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
	
	private boolean missingspacecars;
	private boolean missingspacetravellers;
	private boolean missingspacea1;
	private boolean missingspacea2;
	private boolean missingspaceb1;
	private boolean missingspaceb2;
	private boolean missingspacec1;
	private boolean missingspacec2;
	
	
	public int[] getTravellerValues() {
		return new int[] { 0, 1, 2, 3, 4, 5, 6 };
	}
	
	private void init(){
		missingspacecars = false;
		missingspacetravellers= false;
	    missingspacea1= false;
	    missingspacea2= false;
	    missingspaceb1= false;
	    missingspaceb2= false;
	    missingspacec1= false;
	    missingspacec2= false;
	}
	
	public String prepareReservation(ReservationView reservation)
	{
		init();
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
			init();
			ShipService shipService = service.getShipService();
			shipCabins = shipService.findAllShipCabins(routeHandler.getShip().getShip_id());
			selectedShipCabins = new ArrayList<ShipCabinView>();
			travellerNames = new ArrayList<TravellerView>();
			return "selectCabins";
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
			
			selectedShipCabins = new ArrayList<ShipCabinView>();
			for (ShipCabinView cab : shipCabins) {
				if (cab.getRes_count() > 0)
					selectedShipCabins.add(cab);
			}
			res.setShipCabins(selectedShipCabins);
			res.setTravellerNames(travellerNames);
			res.setTrip(tripHandler.getTrip());
			res.setCustomer(customer);

			if(null != reservation){
				res.setReservation_id(reservation.getReservation_id());
				res.setRoute(reservation.getRoute());

				resservice.updateReservation(res);
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
		FreeSpaceService isfree = service.getFreeSpaceService();
		int bookedcapacity = 0;
		for(ShipCabinView shipcab : shipCabins){
			switch(shipcab.getCabinDescr()){
				case "A1":
					missingspacea1 = !isfree.ismissingspacecabin(shipcab, tripHandler.getTrip());
					break;
				case "A2":
					missingspacea2 = !isfree.ismissingspacecabin(shipcab, tripHandler.getTrip());
					break;
				case "B1":
					missingspaceb1 = !isfree.ismissingspacecabin(shipcab, tripHandler.getTrip());
					break;
				case "B2":
					missingspaceb2 = !isfree.ismissingspacecabin(shipcab, tripHandler.getTrip());
					break;
				case "C1":
					missingspacec1 = !isfree.ismissingspacecabin(shipcab, tripHandler.getTrip());
					break;
				case "C2":
					missingspacec2 = !isfree.ismissingspacecabin(shipcab, tripHandler.getTrip());
					break;
			}
			bookedcapacity += shipcab.getRes_count();
		}
		
		
		if(bookedcapacity < travellerNames.size()){
			missingspacetravellers = true;
		}else{
			missingspacetravellers = false;
		}
		
		missingspacecars = !isfree.ismissingspacecars(this.cars, tripHandler.getTrip());
		
		if(missingspacea1) return false;
		if(missingspacea2) return false;
		if(missingspaceb1) return false;
		if(missingspaceb2) return false;
		if(missingspacec1) return false;
		if(missingspacec2) return false;
		if(missingspacecars) return false;
		if(missingspacetravellers) return false;
		
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

	public ReservationView getReservation() {
		return reservation;
	}

	public void setReservation(ReservationView reservation) {
		this.reservation = reservation;
	}

	public boolean isMissingspacecars() {
		return missingspacecars;
	}

	public void setMissingspacecars(boolean missingspacecars) {
		this.missingspacecars = missingspacecars;
	}

	public boolean isMissingspacetravellers() {
		return missingspacetravellers;
	}

	public void setMissingspacetravellers(boolean missingspacetravellers) {
		this.missingspacetravellers = missingspacetravellers;
	}

	public boolean isMissingspacea1() {
		return missingspacea1;
	}

	public void setMissingspacea1(boolean missingspacea1) {
		this.missingspacea1 = missingspacea1;
	}

	public boolean isMissingspacea2() {
		return missingspacea2;
	}

	public void setMissingspacea2(boolean missingspacea2) {
		this.missingspacea2 = missingspacea2;
	}

	public boolean isMissingspaceb1() {
		return missingspaceb1;
	}

	public void setMissingspaceb1(boolean missingspaceb1) {
		this.missingspaceb1 = missingspaceb1;
	}

	public boolean isMissingspaceb2() {
		return missingspaceb2;
	}

	public void setMissingspaceb2(boolean missingspaceb2) {
		this.missingspaceb2 = missingspaceb2;
	}

	public boolean isMissingspacec1() {
		return missingspacec1;
	}

	public void setMissingspacec1(boolean missingspacec1) {
		this.missingspacec1 = missingspacec1;
	}

	public boolean isMissingspacec2() {
		return missingspacec2;
	}

	public void setMissingspacec2(boolean missingspacec2) {
		this.missingspacec2 = missingspacec2;
	}
}

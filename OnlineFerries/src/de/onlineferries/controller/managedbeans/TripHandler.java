package de.onlineferries.controller.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import de.onlineferries.view.TripView;

@ManagedBean
@SessionScoped
public class TripHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty("#{routeHandler}")
	private RouteHandler routeHandler;
	@ManagedProperty("#{serviceLocatorBean}")
	private ServiceLocatorBean service;
	private TripView trip;
	private List<TripView> trips;

	public String trips() {
		try {
			trips = service.getRouteService().findAllTrips(routeHandler.getRoute().getRoute_id());
			trip = trips.get(0);
			return "showTrips";
		} catch (Exception e) {
			e.printStackTrace();
			return "retry";
		}
	}

	public TripView getTrip() {
		return trip;
	}

	public void setTrip(TripView trip) {
		this.trip = trip;
	}

	public List<TripView> getTrips() {
		return trips;
	}

	public void setTrips(List<TripView> trips) {
		this.trips = trips;
	}

	public void setRouteHandler(RouteHandler routeHandler) {
		this.routeHandler = routeHandler;
	}

	public RouteHandler getRouteHandler() {
		return routeHandler;
	}

	public void setService(ServiceLocatorBean service) {
		this.service = service;
	}

	public Converter getTripViewConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				for (int i = 0; i < trips.size(); i++) {
					TripView r = trips.get(i);
					if ((new Integer(r.getTrip_id()).toString()).equals(value))
						return r;
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				return (new Integer(((TripView) value).getTrip_id())).toString();
			}
		};
	}
}

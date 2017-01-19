package de.onlineferries.controller.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeEvent;

import de.onlineferries.model.service.ShipService;
import de.onlineferries.view.RouteView;
import de.onlineferries.view.ShipView;

@ManagedBean
@SessionScoped
public class RouteHandler implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{serviceLocatorBean}")
	private ServiceLocator service;

	private ShipView ship;
	private List<RouteView> list;
	private RouteView route;

	public String routes() {
		try {
			list = service.getRouteService().findAllRoutes();
			route = list.get(0);
			ship = service.getShipService().findShip(route.getRoute_id());
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "retry";
		}
	}

	public void routeChanged(ValueChangeEvent vce) {
		try {
			System.out.println("route changed");
			RouteView rout = (RouteView) vce.getNewValue();
			ShipService shipService = service.getShipService();
			ship = shipService.findShip(rout.getRoute_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ShipView getShip() {
		return ship;
	}

	public void setShip(ShipView ship) {
		this.ship = ship;
	}

	public void setService(ServiceLocator service) {
		this.service = service;
	}

	public List<RouteView> getList() {
		return list;
	}

	public void setList(List<RouteView> list) {
		this.list = list;
	}

	public RouteView getRoute() {
		return route;
	}

	public void setRoute(RouteView route) {
		this.route = route;
	}

	public Converter getRouteViewConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				for (int i = 0; i < list.size(); i++) {
					RouteView r = list.get(i);
					if ((new Integer(r.getRoute_id()).toString()).equals(value))
						return r;
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				return (new Integer(((RouteView) value).getRoute_id())).toString();
			}
		};
	}

}

package de.onlineferries.controller.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.onlineferries.model.service.FreeSpaceService;
import de.onlineferries.model.service.FreeSpaceServiceImpl;
import de.onlineferries.model.service.LoginService;
import de.onlineferries.model.service.LoginServiceImpl;
import de.onlineferries.model.service.ReservationService;
import de.onlineferries.model.service.ReservationServiceImpl;
import de.onlineferries.model.service.RouteService;
import de.onlineferries.model.service.RouteServiceImp;
import de.onlineferries.model.service.ShipService;
import de.onlineferries.model.service.ShipServiceImpl;

@ManagedBean
@ApplicationScoped
public class ServiceLocatorBean implements ServiceLocator, Serializable {

	private static final long serialVersionUID = 1L;
	private LoginService loginService;
	private RouteService routeService;
	private ShipService shipService;
	private ReservationService reservationService;
	private FreeSpaceService freeSpaceService;

	public ServiceLocatorBean() {
		loginService = new LoginServiceImpl();
		routeService = new RouteServiceImp();
		shipService = new ShipServiceImpl();
		reservationService = new ReservationServiceImpl();
		freeSpaceService = new FreeSpaceServiceImpl();
	}

	@Override
	public LoginService getLoginService() {
		return loginService;
	}

	@Override
	public RouteService getRouteService() {
		return routeService;
	}

	@Override
	public ShipService getShipService() {
		return shipService;
	}

	@Override
	public ReservationService getReservationService() {
		return reservationService;
	}

	@Override
	public FreeSpaceService getFreeSpaceService() {
		return freeSpaceService;
	}

}

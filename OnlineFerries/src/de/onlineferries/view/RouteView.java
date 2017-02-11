package de.onlineferries.view;

import java.io.Serializable;

public class RouteView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int route_id;
	String start;
	String destination;
	private ShipView ship;

	public RouteView(int route_id, String start, String destination) {
		super();
		this.route_id = route_id;
		this.start = start;
		this.destination = destination;
	}

	public ShipView getShip() {
		return ship;
	}

	public void setShip(ShipView ship) {
		this.ship = ship;
	}

	@Override
	public String toString() {
		return "RouteView [route_id=" + route_id + ", start=" + start + ", destitation=" + destination + "]";
	}

	public int getRoute_id() {
		return route_id;
	}

	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}

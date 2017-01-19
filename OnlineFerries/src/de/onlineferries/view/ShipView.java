package de.onlineferries.view;

import java.io.Serializable;

public class ShipView implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ship_id;
	private int cars;
	private int passengers;
	private String description;

	public ShipView(int ship_id, int cars, int passengers, String description) {
		super();
		this.ship_id = ship_id;
		this.cars = cars;
		this.passengers = passengers;
		this.description = description;
	}

	public int getShip_id() {
		return ship_id;
	}

	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}

	public int getCars() {
		return cars;
	}

	public void setCars(int cars) {
		this.cars = cars;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desscription) {
		this.description = desscription;
	}

}

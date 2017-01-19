package de.onlineferries.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TripView implements Serializable {
	private static final long serialVersionUID = 1L;
	private int trip_id;
	private Date date;
	private Date departure;
	private Date arrival;
	private double price_car;
	private double price_passenger;

	public TripView(int trip_id, Date date, Date departure, Date arrival, double price_car, double price_passenger) {
		super();
		this.trip_id = trip_id;
		this.date = date;
		this.departure = departure;
		this.arrival = arrival;
		this.price_car = price_car;
		this.price_passenger = price_passenger;
	}

	public int getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public Date getArrival() {
		return arrival;
	}

	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	public double getPrice_car() {
		return price_car;
	}

	public void setPrice_car(double price_car) {
		this.price_car = price_car;
	}

	public double getPrice_passenger() {
		return price_passenger;
	}

	public void setPrice_passenger(double price_passenger) {
		this.price_passenger = price_passenger;
	}

	public String getTripLabel() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("EEE, dd, MMM yyyy");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

		return sdfDate.format(date) + ": " + sdfTime.format(departure) + " - " + sdfTime.format(arrival);
	}
}

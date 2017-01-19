package de.onlineferries.view;

import java.io.Serializable;

public class ShipCabinView implements Serializable {

	private static final long serialVersionUID = 1L;
	private int cabin_id;
	private String cabinDescr;
	private int count;
	private double price;
	private int res_count;

	public ShipCabinView(int cabin_id, String cabinDescr, int count, double price) {
		super();
		this.cabin_id = cabin_id;
		this.cabinDescr = cabinDescr;
		this.count = count;
		this.price = price;
	}

	public int getCabin_id() {
		return cabin_id;
	}

	public void setCabin_id(int cabin_id) {
		this.cabin_id = cabin_id;
	}

	public String getCabinDescr() {
		return cabinDescr;
	}

	public void setCabinDescr(String cabinDescr) {
		this.cabinDescr = cabinDescr;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getRes_count() {
		return res_count;
	}

	public void setRes_count(int res_count) {
		this.res_count = res_count;
	}

}

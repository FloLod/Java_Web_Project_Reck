package de.onlineferries.view;

import java.io.Serializable;

public class TravellerView implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;

	public TravellerView(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

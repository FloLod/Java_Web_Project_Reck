package de.onlineferries.view;

import java.io.Serializable;
 
public class CustomerView implements Serializable {

	private static final long serialVersionUID = 1L;

	private int customer_id;
	private String firstname;
	private String lastname;
	private String password;

	public CustomerView() {}
	
	public CustomerView(int customer_id, String firstname, String lastname, String password) {
		super();
		this.customer_id = customer_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

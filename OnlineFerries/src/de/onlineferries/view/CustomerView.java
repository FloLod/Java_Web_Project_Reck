package de.onlineferries.view;

import java.io.Serializable;
 
public class CustomerView implements Serializable {

	private static final long serialVersionUID = 1L;

	private int customer_id;
	private String firstname;
	private String name;
	private String password;

	private String street;
	private String zipcode;
	private String city;
	private String email;
	private int account_nr;
	private BankView bank;

	public CustomerView() {
		bank = new BankView();
	}
	
	public CustomerView(int customer_id, String firstname, String lastname, String password) {
		super();
		this.customer_id = customer_id;
		this.firstname = firstname;
		this.name = lastname;
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

	public String getName() {
		return name;
	}

	public void setName(String lastname) {
		this.name = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BankView getBank() {
		return bank;
	}

	public void setBank(BankView bank) {
		this.bank = bank;
	}

	public int getAccount_nr() {
		return account_nr;
	}

	public void setAccount_nr(int account_nr) {
		this.account_nr = account_nr;
	}	
	
	
}

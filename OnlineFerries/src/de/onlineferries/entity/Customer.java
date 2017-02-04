package de.onlineferries.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@NamedQueries( {
	@NamedQuery(
		name="loginCustomer", 
		query="select c from Customer c where c.name = :username and c.password = :password")
})
@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	
	String name;
	String firstname;
	String street;
	String zipcode;
	String city;
	String email;
	int account_nr;
	Bank bank;
	String password;
	
	Set<Reservation> reservations;
	
	public Customer() {}

	public Customer(String name, String firstname, String password) {
		super();
		this.name = name;
		this.firstname = firstname;
		this.password = password;
	}

	@Id
//	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
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

	@OneToMany(mappedBy="customer")
	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="bank_id")
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	public int getAccount_nr() {
		return account_nr;
	}

	public void setAccount_nr(int account_nr) {
		this.account_nr = account_nr;
	}
	
	

}

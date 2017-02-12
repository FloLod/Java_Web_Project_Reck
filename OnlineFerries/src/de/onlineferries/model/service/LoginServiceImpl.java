package de.onlineferries.model.service;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import de.onlineferries.entity.Bank;
import de.onlineferries.entity.Customer;
import de.onlineferries.entity.Reservation;
import de.onlineferries.entity.Trip;
import de.onlineferries.view.BankView;
import de.onlineferries.view.CustomerView;

public class LoginServiceImpl implements LoginService, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public CustomerView login(String username, String password) {

		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		CustomerView customerView = null;

		try {
			Query q = em.createNamedQuery("loginCustomer", Customer.class);
			q.setParameter("username", username);
			q.setParameter("password", password);
			Customer customer = (Customer) q.getSingleResult();
			customerView = new CustomerView(customer.getId(), customer.getFirstname(), customer.getName(), customer.getPassword());
			BankView bank = new BankView();
			customerView.setAccount_nr(customer.getAccount_nr());
			bank.setId(customer.getBank().getId());
			bank.setDescription(customer.getBank().getDescription());
			
			customerView.setBank(bank);
		} catch (NoResultException ex) {
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
		}

		return customerView;
	}

	public boolean register(CustomerView customer){
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		boolean done = false;
		try{					
			em.getTransaction().begin();
			
			Query q1 = em.createQuery("SELECT MAX(id) FROM " + Customer.class.getName());
			int id = (int) q1.getSingleResult() + 1;
			
			Bank bank = new Bank();
			bank.setId(customer.getBank().getId());
			bank.setDescription(customer.getBank().getDescription());
			
			Customer custom = new Customer();
			custom.setId(id);
			
			custom.setBank(bank);
			custom.setAccount_nr(customer.getAccount_nr());
			custom.setCity(customer.getCity());
			custom.setEmail(customer.getEmail());
			custom.setFirstname(customer.getEmail());
			custom.setName(customer.getName());
			custom.setPassword(customer.getPassword());
			custom.setStreet(customer.getStreet());
			custom.setZipcode(customer.getZipcode());
			
			em.persist(bank);
			em.persist(custom);
			em.getTransaction().commit();
			done = true;
		} catch (NoResultException ex) {
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
		}
		return done;		
	}


	
	
	public CustomerView getUser(String email) {
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		CustomerView customerView = null;

		try {
			Query q = em.createQuery("from " + Customer.class.getName() + " where email = :email");
			q.setParameter("email", email);
			Customer customer = (Customer) q.getSingleResult();
			System.out.println("a");
			if(null != customer){
				System.out.println("b");
				customerView = new CustomerView(customer.getId(), customer.getFirstname(), customer.getName(), customer.getPassword());
				customerView.setEmail(email);
				customerView.setStreet(customer.getStreet());
				customerView.setCity(customer.getCity());
				customerView.setZipcode(customer.getZipcode());
				BankView bank = new BankView();
				customerView.setAccount_nr(customer.getAccount_nr());
				bank.setId(customer.getBank().getId());
				bank.setDescription(customer.getBank().getDescription());
				
				customerView.setBank(bank);
			}else{
				System.out.println("c");
				customerView = new CustomerView();
				customerView.setBank(new BankView());
			}
		} catch (NoResultException ex) {
			System.out.println("d");
			customerView = new CustomerView();
			customerView.setBank(new BankView());
		} catch (Exception ex) {
			System.out.println("e");
			ex.printStackTrace();
		} finally {
			em.close();
		}

		return customerView;
	}


	
	public void updateUser(CustomerView user) {
		EntityManager em = EntityManagerFactoryService.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		Customer c = em.find(Customer.class, user.getCustomer_id());
		c.setAccount_nr(user.getAccount_nr());
		c.setCity(user.getCity());
		c.setEmail(user.getEmail());
		c.setFirstname(user.getFirstname());
		c.setName(user.getName());
		c.setPassword(user.getPassword());
		c.setStreet(user.getStreet());
		c.setZipcode(user.getZipcode());
		
		Bank b = null;
		try{
			b = em.find(Bank.class, user.getBank().getId());
			b.setDescription(user.getBank().getDescription());
		}
		catch(Exception e){
			b = new Bank();
			b.setDescription(user.getBank().getDescription());
			b.setId(user.getBank().getId());
		}
		c.setBank(b);
		em.persist(c);
		em.getTransaction().commit();
	}



}

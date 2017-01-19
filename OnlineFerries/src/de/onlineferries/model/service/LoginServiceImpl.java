package de.onlineferries.model.service;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import de.onlineferries.entity.Customer;
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
		} catch (NoResultException ex) {
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
		}

		return customerView;
	}

}

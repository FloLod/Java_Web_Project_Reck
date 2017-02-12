package de.onlineferries.model.service;

import de.onlineferries.view.CustomerView;

public interface LoginService {

	CustomerView login(String username, String password);
	
	boolean register(CustomerView customer);
	
	CustomerView getUser(String username);
	
	void updateUser(CustomerView user);
}

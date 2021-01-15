package com.crm.services;

import java.util.List;

import com.crm.entity.Customer;

public interface CustomerService {
	
List<Customer> getCustomers();
	
	Customer getCustomer(int id);
	
	void saveCustomer(Customer customer);
	
	void deleteCustomer(int id);

}

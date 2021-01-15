package com.crm.dao;

import java.util.List;

import com.crm.entity.Customer;

public interface CustomerDao {
	
	List<Customer> getCustomers();
	
	Customer getCustomer(int ids);
	
	void saveCustomer(Customer customer);
	
	void deleteCustomer(int id);

}

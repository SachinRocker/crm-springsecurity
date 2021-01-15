package com.crm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.crm.entity.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		Session session = sessionFactory.getCurrentSession();
		Query<Customer> theQuery = session.createQuery("from Customer order by lastName", Customer.class);

		List<Customer> customers = theQuery.getResultList();
		return customers;
	}

	@Override
	public Customer getCustomer(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query<Customer> theQuery = session.createQuery("from Customer where id=:theId", Customer.class);
		theQuery.setParameter("theId", id);
		Customer customer = theQuery.getSingleResult();
		return customer;
	}

	@Override
	public void saveCustomer(Customer customer) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// save/upate the customer
		currentSession.saveOrUpdate(customer);

	}

	@Override
	public void deleteCustomer(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query<Customer> theQuery = session.createQuery("delete from Customer where id=:theId", Customer.class);
		theQuery.setParameter("theId", id);
		theQuery.executeUpdate();

	}

}

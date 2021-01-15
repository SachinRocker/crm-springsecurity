package com.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.entity.Customer;
import com.crm.services.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String showCustomerList(Model model) {
		System.out.println(" inside showCustomerList");

		List<Customer> customers = customerService.getCustomers();
		model.addAttribute("customers", customers);

		return "customer-list";

	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer, Model model) {

		customerService.saveCustomer(customer);
		return "redirect:/customer/list";

	}

	@GetMapping("/showFormForAdd")
	public String showForm(Model model) {

		model.addAttribute("customer", new Customer());
		return "customer-form";
	}
	
	
	
	@GetMapping("/updateCustomer")
	public String updateForm(@RequestParam("customerId") int id, Model theModel) {
		
		// save the customer using our service
		Customer customer = customerService.getCustomer(id);
		
		theModel.addAttribute("customer", customer);
	
		
		return "customer-form";
	}

}

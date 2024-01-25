package com.citi.WebConfiguratorService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.citi.WebConfiguratorService.model.Customer;

@Service
public interface CustomerServiceDao {

	List<Customer> getCustomerByID(String id);
	List<Customer> getAllCustomer();
	List<Customer> getCustomerByFirstNameOrLastName(String firstName);
	Customer saveCustomer(Customer cust);
	
}


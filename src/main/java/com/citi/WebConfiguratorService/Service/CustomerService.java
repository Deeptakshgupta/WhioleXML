package com.citi.WebConfiguratorService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.WebConfiguratorService.model.Customer;
import com.citi.WebConfiguratorService.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService implements CustomerServiceDao{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> getCustomerByID(String id) 
	{
		return customerRepository.findBycustomerIdContainingIgnoreCase(id);
	}
	
	@Override
	public List<Customer> getAllCustomer() {
		log.info("vyvubknlkm");
		return customerRepository.findAll();
	}
	
	@Override
	public Customer saveCustomer(Customer cust) {
		log.info("Save customer info"+cust);
		return customerRepository.save(cust);
	}

	
	@Override
	public List<Customer> getCustomerByFirstNameOrLastName(String firstName) {
		log.info("findByCustomerNameContainingIgnoreCase.......");
		return customerRepository.findByCustomerNameContainingIgnoreCase(firstName);
	}	
}

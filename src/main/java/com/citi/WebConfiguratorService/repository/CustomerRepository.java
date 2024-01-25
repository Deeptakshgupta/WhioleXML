package com.citi.WebConfiguratorService.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citi.WebConfiguratorService.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>
{
	List<Customer> findAll();
	
	List<Customer> findByCustomerNameContainingIgnoreCase(String name);
	
	List<Customer> findBycustomerIdContainingIgnoreCase(String customerId);
	
	
}

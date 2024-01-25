package com.citi.WebConfiguratorService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.WebConfiguratorService.model.Components;
import com.citi.WebConfiguratorService.model.Customer;
import com.citi.WebConfiguratorService.repository.ComponentsRepository;
import com.citi.WebConfiguratorService.repository.CustomerRepository;

@Service
public class ComponentsServiceImpl implements ComponentsServiceDao {

	@Autowired
	public ComponentsRepository componentsRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Components> getOrderByComponentsNameDsc(String componentName,int a,int b,String customeId) {
		StringBuffer componentNameLike = new StringBuffer();
		componentNameLike.append("%");
		componentNameLike.append(componentName.toLowerCase());
		componentNameLike.append("%");
		return componentsRepository.getResultListcomponentName(componentNameLike.toString(),a,b,customeId);
	}

//	@Override
//	public Components getTopByOrderByComponentsIdDsc(String componentName) {
//
//		return componentsRepository.findTopBycomponentNameContainingIgnoreCaseAndIsPublishedOrderByVersionDesc(componentName,"Y");
//	}

	@Override
	public List<Components> getAllBycustomerId(String customerId,int a,int b) {
		StringBuffer customerIdLike = new StringBuffer();
		customerIdLike.append("%");
		customerIdLike.append(customerId.toLowerCase());
		customerIdLike.append("%");
		return componentsRepository.getResultList(customerIdLike.toString(),a,b);
	}

	@Override
	public Components saveComponents(String customerId,Components components) {
		 List<Customer> customer=customerRepository.findBycustomerIdContainingIgnoreCase(customerId);
		 Customer cust = new Customer();
		 cust.setCustomerId(customer.get(0).getCustomerId());
		 components.setCustomer(cust);
		 customerRepository.flush();
		return componentsRepository.saveAndFlush(components);
		
	}

}

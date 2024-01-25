package com.citi.WebConfiguratorService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.WebConfiguratorService.model.Customer;
import com.citi.WebConfiguratorService.model.Layouts;
import com.citi.WebConfiguratorService.repository.CustomerRepository;
import com.citi.WebConfiguratorService.repository.LayoutRepository;

@Service
public class LayoutServiceImpl implements LayoutServiceDao {
	
	@Autowired
	private LayoutRepository layoutRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Layouts> findOrderByLayout(String layoutName,int a,int b,String customeId) {
		// TODO Auto-generated method stub
		StringBuffer layoutNameLike = new StringBuffer();
		layoutNameLike.append("%");
		layoutNameLike.append(layoutName.toLowerCase());
		layoutNameLike.append("%");
		
		return layoutRepository.getResultListLayoutName(layoutNameLike.toString(),a,b,customeId);
	}

	@Override
	public Layouts saveLayouts(String customerId,Layouts lay) {
		 List<Customer> customer=customerRepository.findBycustomerIdContainingIgnoreCase(customerId);
		 Customer cust1 = new Customer();
		 cust1.setCustomerId(customer.get(0).getCustomerId());
		 lay.setLayCustomer(cust1);
		 customerRepository.flush();
		return layoutRepository.saveAndFlush(lay);
	}

//	@Override
//	public Layouts findTopByOrderByLayout(String layoutName) {
//		// TODO Auto-generated method stub
//		return layoutRepository.findTopBylayoutNameContainingIgnoreCaseAndIsPublishedOrderByLayoutVersionDesc(layoutName, "Y");
//	}

	@Override
	public List<Layouts> findAllBycustomerIdCustom(String customerId,int a,int b) {
		StringBuffer customerIdLike = new StringBuffer();
		customerIdLike.append("%");
		customerIdLike.append(customerId.toLowerCase());
		customerIdLike.append("%");
        List<Layouts> resultList = layoutRepository.getResultList(customerIdLike.toString(),a,b);
		return resultList;
	}

}

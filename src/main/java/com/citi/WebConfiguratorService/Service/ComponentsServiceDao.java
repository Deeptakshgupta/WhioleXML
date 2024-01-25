package com.citi.WebConfiguratorService.Service;

import java.util.List;

import com.citi.WebConfiguratorService.model.Components;



public interface ComponentsServiceDao {

	List<Components> getOrderByComponentsNameDsc(String componentName,int a,int b,String customeId);
	
	//Components getTopByOrderByComponentsIdDsc(String componentName,int a,int b);
	
	List<Components> getAllBycustomerId(String customerId,int a,int b);
	
	Components saveComponents(String customerId,Components components);
}

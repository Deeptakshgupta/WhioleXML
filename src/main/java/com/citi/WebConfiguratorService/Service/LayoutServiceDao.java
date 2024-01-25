package com.citi.WebConfiguratorService.Service;

import java.util.List;

import com.citi.WebConfiguratorService.model.Layouts;

public interface LayoutServiceDao {

	List<Layouts> findOrderByLayout(String layoutName,int a,int b,String customeId);
	
	//Layouts findTopByOrderByLayout(String layoutName);
	
	List<Layouts> findAllBycustomerIdCustom(String customerId,int a,int b);
	
	Layouts saveLayouts(String customerId, Layouts lay);
	
}

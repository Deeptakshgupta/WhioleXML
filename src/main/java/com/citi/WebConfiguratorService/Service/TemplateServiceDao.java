package com.citi.WebConfiguratorService.Service;

import java.util.List;
import java.util.Map;

import com.citi.WebConfiguratorService.DTO.TemplateDTO;
import com.citi.WebConfiguratorService.model.Customer;

public interface TemplateServiceDao {
    
	void templatePostRequest(TemplateDTO templateRequestPayload);
	
	
}

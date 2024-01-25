package com.citi.WebConfiguratorService.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citi.WebConfiguratorService.DTO.TemplateDTO;
import com.citi.WebConfiguratorService.Service.ComponentsServiceDao;
import com.citi.WebConfiguratorService.Service.CustomerServiceDao;
import com.citi.WebConfiguratorService.Service.LayoutServiceDao;
import com.citi.WebConfiguratorService.Service.TemplatePreviewService;
import com.citi.WebConfiguratorService.Service.TemplateServiceDao;
import com.citi.WebConfiguratorService.exceptionHandler.ErrorDetails;
import com.citi.WebConfiguratorService.exceptionHandler.ResourceNotFoundException;
import com.citi.WebConfiguratorService.model.Components;
import com.citi.WebConfiguratorService.model.Customer;
import com.citi.WebConfiguratorService.model.Layouts;
import com.citi.WebConfiguratorService.model.Reports;

import io.swagger.models.Response;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/")
@Slf4j
public class WebConfiguratorController {

	@Autowired
	private CustomerServiceDao service;
	@Autowired
	private LayoutServiceDao layoutService;
	
	@Autowired
	private ComponentsServiceDao componentsService;
	
	
	@Autowired
	private TemplateServiceDao templateDesignService;
	
	@Autowired
	private TemplatePreviewService previewService;
	
	@GetMapping("/customers/*")
	public Map<String,List<Customer>> getAllCustomers(HttpServletRequest httpServletRequest) {
		
		Map<String, List<Customer>> dataSet = new HashMap<>();
		try{
			String link = httpServletRequest.getRequestURL().toString();

			 if(link.contains("customers/*")) { 
				 List<Customer> customerList = service.getAllCustomer(); 
				 
					dataSet.put("data",customerList);
					if(customerList.isEmpty()) {
						dataSet.clear();
						throw new ResourceNotFoundException("Data Not Found");
					}
				 }
			 else {
				 dataSet.clear();
				throw new ResourceNotFoundException("Invalid Path requested");
			}
		}
		catch(ResourceNotFoundException ex) {
			ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), "");
			Customer cust = new Customer();
			cust.setMessage(errorDetails.getMessage());
			List<Customer> list = new ArrayList<Customer>();
			list.add(cust);
			dataSet.put("data",list);
			log.info(""+ex);
			log.info(""+dataSet);
			return  dataSet;
		}
		catch(Exception ex1) {
			ErrorDetails errorDetails = new ErrorDetails(new Date(), ex1.getMessage(), "");
			Customer cust = new Customer();
			cust.setMessage(errorDetails.getMessage());
			List<Customer> list = new ArrayList<Customer>();
			list.add(cust);
			dataSet.put("data",list);
			log.info(""+ex1);
			log.info(""+dataSet);
			return  dataSet;
		} 
		return dataSet;
	}
	
	@GetMapping("/customerDetail/{customerId}")
    public ResponseEntity<Map<String, List<Customer>>> getCustomerById(@PathVariable(value = "customerId") String customerId) {
		
		Map<String, List<Customer>> dataSet = new HashMap<>();
		
		try {
			
			List<Customer> getCustomerDetail =  service.getCustomerByID(customerId);
			
			
			
			dataSet.put("data",getCustomerDetail);
			
			if(dataSet.isEmpty()) {
				dataSet.clear();
				throw new ResourceNotFoundException("Customer not found for this id :" + customerId);
			}
		
		}
		catch(Exception ex) {
			Customer cust = new Customer();
				cust.setMessage(ex.getMessage());
				 List<Customer> lst= new ArrayList<Customer>();
				 lst.add(cust);
				dataSet.put("data",lst);
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
		}
		return ResponseEntity.ok().body(dataSet);
		
    }
	@GetMapping("/customerDetailByName/{customerName}")
	public ResponseEntity<Map<String, List<Customer>>> getCustomerByFirstNameOrLastName(@PathVariable(value = "customerName") String name)
	{
		
		Map<String, List<Customer>> dataSet = new HashMap<>();
		
		try {
			
			List<Customer> getCustomerDetailByFirstNameOrLastName =  service.getCustomerByFirstNameOrLastName(name);
			
			
			dataSet.put("data",getCustomerDetailByFirstNameOrLastName);
			
			if(dataSet.isEmpty()) {
				dataSet.clear();
				throw new ResourceNotFoundException("Customer not found for this First Name or Last Name :" + name);
			}
			
		}
		catch(ResourceNotFoundException ex1) {
			Customer cust = new Customer();
			cust.setMessage(ex1.getMessage());
			List<Customer> lst = new ArrayList<Customer>();
			lst.add(cust);
			dataSet.put("data",lst);
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
		}
		catch(Exception ex) {
			Customer cust = new Customer();
			cust.setMessage(ex.getMessage());
			List<Customer> lst = new ArrayList<Customer>();
			lst.add(cust);
			dataSet.put("data",lst);
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
		}
		return ResponseEntity.ok().body(dataSet);
		
	}
	 @PostMapping("/customerDetail/add")
	    public ResponseEntity<?> registerUser(@RequestBody Customer cust){

	        service.saveCustomer(cust);

	        return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);

	    }
	 
	 @PostMapping("/layoutsDetail/add/{customerId}")
	 public ResponseEntity<?> addLayouts(@PathVariable(value = "customerId") String customerId,@RequestBody Layouts lay){
		 
		 layoutService.saveLayouts(customerId,lay);
		 
		 return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
		 
	 }
	 
	 @GetMapping("/layoutsDetailByLayoutName/{customerId}/{layoutName}/{fromIndex}/{toIndex}")
		public ResponseEntity<Map<String, List<Layouts>>> findByIdOrderBylayoutVersionDesc(
				@PathVariable(value = "customerId") String customerId,
				@PathVariable(value = "layoutName") String layoutName,
				@PathVariable(value = "fromIndex") int fromIndex,
				@PathVariable(value = "toIndex") int toIndex)
		{
			
			Map<String, List<Layouts>> dataSet = new HashMap<>();
			List<Layouts> dataList = new ArrayList<Layouts>();
			
			try {
				
				dataList =  layoutService.findOrderByLayout(layoutName,fromIndex,toIndex,customerId);
				
				dataSet.put("data",dataList);
				
				if(dataSet.isEmpty()) {
					dataSet.clear();
					throw new ResourceNotFoundException("Layouts not found for this Layout Name :" + layoutName);
				}
				
			}
			catch(ResourceNotFoundException ex1) {
				Layouts lay = new Layouts();
				lay.setMessage(ex1.getMessage());
				List<Layouts> lst = new ArrayList<Layouts>();
				lst.add(lay);
				dataSet.put("data",lst);
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
			}
			catch(Exception ex) {
				Layouts lay = new Layouts();
				lay.setMessage(ex.getMessage());
				List<Layouts> lst = new ArrayList<Layouts>();
				lst.add(lay);
				dataSet.put("data",lst);
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
			}
			return ResponseEntity.ok().body(dataSet);
			
		}
//	 @GetMapping("/layoutsDetailTopByLayoutName/{layoutName}")
//	 public ResponseEntity<Map<String, List<Layouts>>> findByIdTopByOrderBylayoutVersionDesc(@PathVariable(value = "layoutName") String layoutName)
//	 {
//		 
//		 Map<String, List<Layouts>> dataSet = new HashMap<>();
//		 List<Layouts> dataList = new ArrayList<Layouts>();
//		 
//		 try {
//			 
//			 Layouts getlay =  layoutService.findTopByOrderByLayout(layoutName);
//			 dataList.add(getlay);
//			 dataSet.put("data",dataList);
//			 
//			 if(dataSet.isEmpty()) {
//				 dataSet.clear();
//				 throw new ResourceNotFoundException("Layouts not found for this Layout Name :" + layoutName);
//			 }
//			 
//		 }
//		 catch(ResourceNotFoundException ex1) {
//			 Layouts lay = new Layouts();
//			 lay.setMessage(ex1.getMessage());
//			 List<Layouts> lst = new ArrayList<Layouts>();
//			 lst.add(lay);
//			 dataSet.put("data",lst);
//			 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
//		 }
//		 catch(Exception ex) {
//			 Layouts lay = new Layouts();
//			 lay.setMessage(ex.getMessage());
//			 List<Layouts> lst = new ArrayList<Layouts>();
//			 lst.add(lay);
//			 dataSet.put("data",lst);
//			 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
//		 }
//		 return ResponseEntity.ok().body(dataSet);
//		 
//	 }
	 
	 @GetMapping("/layoutsDetailByCustomerId/{customerId}/{fromIndex}/{toIndex}")
		public ResponseEntity<Map<String, List<Layouts>>> getLayoutsByCustomerId(@PathVariable(value = "customerId") String customerId,
				@PathVariable(value = "fromIndex") int fromIndex,@PathVariable(value = "toIndex") int toIndex)
		{
			
			Map<String, List<Layouts>> dataSet = new HashMap<>();
			
			try {
							
				List<Layouts> findAllBycustomerId =  layoutService.findAllBycustomerIdCustom(customerId,fromIndex,toIndex);
				
				dataSet.put("data",findAllBycustomerId);
				
				if(dataSet.isEmpty()) {
					dataSet.clear();
					throw new ResourceNotFoundException("Layouts not found for this customer ID :" + customerId);
				}
				
			}
			catch(ResourceNotFoundException ex1) {
				Layouts lay = new Layouts();
				lay.setMessage(ex1.getMessage());
				List<Layouts> lst = new ArrayList<Layouts>();
				lst.add(lay);
				dataSet.put("data",lst);
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
			}
			catch(Exception ex) {
				Layouts lay = new Layouts();
				lay.setMessage(ex.getMessage());
				List<Layouts> lst = new ArrayList<Layouts>();
				lst.add(lay);
				dataSet.put("data",lst);
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
			}
			return ResponseEntity.ok().body(dataSet);
			
		}
	 
	 @PostMapping("/componentsDetail/add/{customerId}")
	 public ResponseEntity<?> addComponents(@PathVariable(value = "customerId") String customerId,@RequestBody Components component){
		
		 componentsService.saveComponents(customerId,component);
		 
		 return new ResponseEntity<>("Component added successfully", HttpStatus.OK);
		 
	 }
	 
	 /** List of all Components w.r.t Name**/
	 
	 @GetMapping("/componentsDetailBycompnentName/{customerId}/{componentName}/{fromIndex}/{toIndex}")
		public ResponseEntity<Map<String, List<Components>>> findByIdTopByOrderByComponentVersionDesc(
				@PathVariable(value = "customerId") String custId,
				@PathVariable(value = "componentName") String componentName,
				@PathVariable(value = "fromIndex") int fromIndex,
				@PathVariable(value = "toIndex") int toIndex)
		{
			
			Map<String, List<Components>> dataSet = new HashMap<>();
			List<Components> dataList = new ArrayList<Components>();
			
			try {
				
				dataList =  componentsService.getOrderByComponentsNameDsc(componentName,fromIndex,toIndex,custId);
				
				dataSet.put("data",dataList);
				
				if(dataSet.isEmpty()) {
					dataSet.clear();
					throw new ResourceNotFoundException("Components not found for this Component name :" + componentName);
				}
				
			}
			catch(ResourceNotFoundException ex1) {
				Components component = new Components();
				component.setMessage(ex1.getMessage());
				List<Components> lst = new ArrayList<Components>();
				lst.add(component);
				dataSet.put("data",lst);
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
			}
			catch(Exception ex) {
				Components comp = new Components();
				comp.setMessage(ex.getMessage());
				List<Components> lst = new ArrayList<Components>();
				lst.add(comp);
				dataSet.put("data",lst);
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
			}
			return ResponseEntity.ok().body(dataSet);
			
		}
	 
	 
	 /** Specific Component w.r.t ID **/
	 
//	 @GetMapping("/componentsDetailTopByComponentName/{componentName}")
//	 public ResponseEntity<Map<String, List<Components>>> findByIdTopByOrderByComopnentVersionDesc(@PathVariable(value = "componentName") String componentName)
//	 {
//		 
//		 Map<String, List<Components>> dataSet = new HashMap<>();
//		 List<Components> dataList = new ArrayList<Components>();
//		 
//		 try {
//			 
//			 Components components =  componentsService.getTopByOrderByComponentsIdDsc(componentName);
//			 dataList.add(components);
//			 dataSet.put("data",dataList);
//			 
//			 if(dataSet.isEmpty()) {
//				 dataSet.clear();
//				 throw new ResourceNotFoundException("Components not found for this component id :" + componentName);
//			 }
//			 
//		 }
//		 catch(ResourceNotFoundException ex1) {
//			 Components component = new Components();
//			 component.setMessage(ex1.getMessage());
//			 List<Components> componentlst = new ArrayList<Components>();
//			 componentlst.add(component);
//			 dataSet.put("data",componentlst);
//			 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
//		 }
//		 catch(Exception ex) {
//			 Components component = new Components();
//			 component.setMessage(ex.getMessage());
//			 List<Components> componentlst = new ArrayList<Components>();
//			 componentlst.add(component);
//			 dataSet.put("data",componentlst);
//			 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
//		 }
//		 return ResponseEntity.ok().body(dataSet);
//		 
//	 }
//	 
	 
	 /**Get all Components w.r.t Customer Id**/ 

	 @GetMapping("/compnentsDetailByCustomerId/{customerId}/{fromIndex}/{toIndex}")
		public ResponseEntity<Map<String, List<Components>>> getComponentsByCustomerId(@PathVariable(value = "customerId") String customerId,
				@PathVariable(value = "fromIndex") int fromIndex,@PathVariable(value = "toIndex") int toIndex)
		{
			
			Map<String, List<Components>> dataSet = new HashMap<>();
			
			try {

				List<Components> findAllBycustomerId =  componentsService.getAllBycustomerId(customerId,fromIndex,toIndex);
				
				
				dataSet.put("data",findAllBycustomerId);
				
				if(dataSet.isEmpty()) {
					dataSet.clear();
					throw new ResourceNotFoundException("Components not found for this customer ID :" + customerId);
				}
				
			}
			catch(ResourceNotFoundException ex1) {
				Components component = new Components();
				component.setMessage(ex1.getMessage());
				List<Components> componentlst = new ArrayList<Components>();
				componentlst.add(component);
				dataSet.put("data",componentlst);
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
			}
			catch(Exception ex) {
				Components component = new Components();
				component.setMessage(ex.getMessage());
				List<Components> componentlst = new ArrayList<Components>();
				componentlst.add(component);
				dataSet.put("data",componentlst);
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataSet);
			}
			return ResponseEntity.ok().body(dataSet);
			
		}
	 @PostMapping("/templateDesignPost")
	 public ResponseEntity<?> templateDesign(@RequestBody TemplateDTO tempDto){
		 
		 templateDesignService.templatePostRequest(tempDto);
		 
		 
		 return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
		 
	 }
	 
	 @PostMapping("/previewTemplate/{templateName}")
	 public ResponseEntity<List<Reports>> previewTemplate(@PathVariable("templateName") String templateName)
	 {
		 try {
		 List<Reports> report = previewService.previewTemplate(templateName);
		 
		 return ResponseEntity.ok(report);
		 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return null;
		 
		 
	 }
}

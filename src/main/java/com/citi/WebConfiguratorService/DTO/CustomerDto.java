package com.citi.WebConfiguratorService.DTO;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class CustomerDto {
	
	@NotBlank
	private long id;
    
	@NotBlank
    private String customerName;
    
	@NotBlank
    private String customerType;
    
	@NotBlank
    private String customerAddress;
    
	@NotBlank
	@Email
    private String customerEmail;
    
	@NotBlank
	@Max(value = 10, message = "Please Do Not Insert +91")
	@Min(value = 10, message = "Phone No must be 10 digit")
    private String customerPhone;
	
	
    private Date timestamp;
	
	private String message;
    
    
    	public CustomerDto() {
		
	}


		public CustomerDto(Date timestamp, String message) {
		super();
		this.timestamp = timestamp;
		this.message = message;
	}


		public CustomerDto(long id, String customerName, String customerType, String customerAddress,
				String customerEmail, String customerPhone) {
			super();
			this.id = id;
			this.customerName = customerName;
			this.customerType = customerType;
			this.customerAddress = customerAddress;
			this.customerEmail = customerEmail;
			this.customerPhone = customerPhone;
		}


		public long getId() {
			return id;
		}


		public void setId(long id) {
			this.id = id;
		}


		public String getCustomerName() {
			return customerName;
		}


		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}


		public String getCustomerType() {
			return customerType;
		}


		public void setCustomerType(String customerType) {
			this.customerType = customerType;
		}


		public String getCustomerAddress() {
			return customerAddress;
		}


		public void setCustomerAddress(String customerAddress) {
			this.customerAddress = customerAddress;
		}


		public String getCustomerEmail() {
			return customerEmail;
		}


		public void setCustomerEmail(String customerEmail) {
			this.customerEmail = customerEmail;
		}


		public String getCustomerPhone() {
			return customerPhone;
		}


		public void setCustomerPhone(String customerPhone) {
			this.customerPhone = customerPhone;
		}


		public Date getTimestamp() {
			return timestamp;
		}


		public void setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
		}


		public String getMessage() {
			return message;
		}


		public void setMessage(String message) {
			this.message = message;
		}
    
}

package com.citi.WebConfiguratorService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Date {

	@JsonProperty("Format")
	private String Format;
	@JsonProperty("Left")
	private String  Left;
	@JsonProperty("Top")
	private String Top;
}


/*

"Date":{
					"Format":"",
					"Left":"",
					"Top":""
					} 
*/
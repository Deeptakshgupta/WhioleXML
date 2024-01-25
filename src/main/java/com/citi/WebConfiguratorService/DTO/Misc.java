package com.citi.WebConfiguratorService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Misc {
	
	@JsonProperty("date")
	private Date date;
	@JsonProperty("hyperLinks")
	private Hyperlinks hyperLinks;
}

/*
"Misc":
					{
				  "Date":
						{	
						"Format":"", 
						"Left ":"",
						"Top":"",
						}
				  "Hyperlinks":
							{
							"link#1":""	
							}
					} 
*/
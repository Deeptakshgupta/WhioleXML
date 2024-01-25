package com.citi.WebConfiguratorService.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperSize {

	@JsonProperty("Height")
	private String Height;
	@JsonProperty("Width")
	private String Width;
	@JsonProperty("Type")
	private String Type;
}


/*
 
 "PaperSize":{
						"Height":"794",
						"Width":"1123",
						"Type":"5"
					   }
*/
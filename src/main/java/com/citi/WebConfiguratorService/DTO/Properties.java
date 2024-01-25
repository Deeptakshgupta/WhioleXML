package com.citi.WebConfiguratorService.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Properties {

	//private PageBorder pageBorder;
	@JsonProperty("BackColor") 
	private String BackColor;
	@JsonProperty("PageObjectId") 
	private String PageObjectId;
}

/*
 "Properties":
				  {
					"PageBorder":
						{.......}
				  "BackColor":"16777215",
				  "PageObjectId":"10001"
				  }
*/
package com.citi.WebConfiguratorService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayLayout {

	@JsonProperty("DisplayReportName")
	private String DisplayReportName;
	
	@JsonProperty("DisplayPageName")
	private String DisplayPageName;
	
	@JsonProperty("IsGlobalTemplate")
	private String IsGlobalTemplate;
}



/*
"DisplayLayout":{
	"DisplayReportName":"",
	"DisplayPageName":"",
	"IsGlobalTemplate":""
   }
*/
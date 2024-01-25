package com.citi.WebConfiguratorService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Components {

	@JsonProperty("componentName")
	private String componentName;
	@JsonProperty("top")
	private String top;
	@JsonProperty("left")
	private String left;
	@JsonProperty("componentXml")
	private String componentXml;
	@JsonProperty("version")
	private String version;
	@JsonProperty("comp_flag")
	private String comp_flag;
	/*
	 f- first time save-> dimensions
	 u- update -> dimensions,
	 d- delete -> layerTag, PageHeader, Page Footer 
	 */
	
}

/*
 "components":[{
							"componentName": "",
							"top": 0,-> for Dimension 
							"left": 0, -> for Dimension
							"componentXml":"",
							"version":""
						}]
*/
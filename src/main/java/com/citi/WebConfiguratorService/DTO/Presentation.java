package com.citi.WebConfiguratorService.DTO; 

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Presentation {

	@JsonProperty("type") 
	private String Type;// Template
	
	@JsonProperty("reportName") 
	private String reportName;
	
	@JsonProperty("displayLayout") 
	private DisplayLayout displayLayout;
	
	@JsonProperty("supressHeaderFooterOnFirstPage") 
	private String SupressHeaderFooterOnFirstPage;
	
	@JsonProperty("paperSize") 
	private PaperSize paperSize; 
	
	@JsonProperty("pageNumbering") 
	private String PageNumbering;
	
	@JsonProperty("maxId")
	private String MAXID;
	
	@JsonProperty("cycle") 
	private Cycle cycle; 
	
	@JsonProperty("conditionalAnnexures")
	private String ConditionalAnnexures;
	
	@JsonProperty("date")
	private Date date;
	
	@JsonProperty("createdBy")
	private String createdBy;
	
	@JsonProperty("pages")
	private List<Pages> pages;
	
	@JsonProperty("presentation_flag")
	private String presentation_flag;
	/*
	 f- first time save-> dimensions
	 u- update -> dimensions,
	 d- delete -> layerTag, PageHeader, Page Footer 
	 */
	
}



/*
Presentation{
"DisplayLayout":{
					"DisplayReportName":"",
					"DisplayPageName":"",
					"IsGlobalTemplate":""
				   }
"SupressHeaderFooterOnFirstPage":""
"PaperSize":{
				"Height":"794",
				"Width":"1123",
				"Type":"5"
			   }
	"PageNumbering":"",
	"MAXID":"1",
	"Cycle":
		{
		"FieldName":"",
		"TableName":""
		}
	"ConditionalAnnexures":"",		   
	"Date":{
			"Format":"",
			"Left":"",
			"Top":""
			}		   
	"createdBy":"",
	
	"Pages": -> list of Pages
	}
*/
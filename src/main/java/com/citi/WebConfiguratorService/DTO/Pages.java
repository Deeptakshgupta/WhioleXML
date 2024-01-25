package com.citi.WebConfiguratorService.DTO; 
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pages {

	@JsonProperty("PageId")
	private String PageId;
	
	@JsonProperty("PageName")
	private String PageName;
	
	@JsonProperty("DefaultHeaderFooter")
	private String DefaultHeaderFooter;
	
	@JsonProperty("PageObjectId")
	private String PageObjectId;
	
	@JsonProperty("components")
	private List<Components> components;
	
	@JsonProperty("layoutId")
	private String layoutId;
	
	@JsonProperty("layoutName")
	private String layoutName;
	
	@JsonProperty("layoutXml")
	private String layoutXml;
	
	@JsonProperty("layoutVersion")
	private String layoutVersion;
	
	@JsonProperty("properties")
	private Properties properties ;
	
	@JsonProperty("misc")
	private Misc misc;
	
}

/* 
   "Pages":[{
				"PageId":"",
				"PageName":"", 
				"DefaultHeaderFooter":"",
				"PageObjectId":"",
				"DFL":"", 
				"Barcodes":"", 
				"Annotations":
					{
					  "ImageAnnotations":"", 
					  "RectAnnotations":"", 
					  "TriangleAnnotations":"", 
					  "LineAnnotations":"", 
					  "CircleAnnotations":"", 
					  "CaptionAnnotations":"",
					}
				"Compnents":[{
							"componentName": "",
							"top": 0,
							"left": 0, 
							"componentXml":"",
							"version":""
						}]
				"layoutId": "",
				"layoutName": "",
				"layoutXml": "",
				"layoutVersion": ""		
				"Properties":
				  {
					"PageBorder":
						{
						"Applied":"",
						"LeftMargin":"",
						"TopMargin":"",
						"RightMargin":"",
						"BottomMargin":"",
						"LeftLine":
						  {
						  "LineColor":"",
						  "LineWidth":"",
						  "Visible":""
						  }
						"RightLine":
						  {
						  "LineColor":"",
						  "LineWidth":"",
						  "Visible":""
						  }
						"TopLine":
						  {
						  "LineColor":"",
						  "LineWidth":"",
						  "Visible":""
						  }
						"BottomLine":
						  {
						  "LineColor":"",
						  "LineWidth":"",
						  "Visible":""
						  }
						}
				  "BackColor":"16777215",
				  "PageObjectId":"10001"
				  }
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
				"Rules": 
						[{
							"RuleId":"",
							"RuleName":""
						}]
				"PageHeader":
				{
				  "Height":"20",
				  "BackColor":"16777215",
				  "Annotations":
				  {
					"ImageAnnotations": "",
					"RectAnnotations": "",
					"TriangleAnnotations":"", 
					"LineAnnotations": "",
					"CircleAnnotations": "",
					"CaptionAnnotations":""
				  } 
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
				  "DFL":
				}  
				"PageFooter":
				{
				  "Height":"20",
				  "BackColor":"16777215",
				  "Annotations":
				  {
					"ImageAnnotations": "",
					"RectAnnotations": "",
					"TriangleAnnotations":"", 
					"LineAnnotations": "",
					"CircleAnnotations": "",
					"CaptionAnnotations":""
				  } 
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
				  "DFL":""
				} 
				
			}]

		}
   
*/
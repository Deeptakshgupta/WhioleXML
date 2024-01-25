package com.citi.WebConfiguratorService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cycle {
    
	@JsonProperty("FieldName")
	private String  FieldName;
	@JsonProperty("TableName")
	private String TableName;
}


/*
    "Cycle":
				{
				"FieldName":"",
				"TableName":""
				}*/

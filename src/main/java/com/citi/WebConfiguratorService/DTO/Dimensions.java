package com.citi.WebConfiguratorService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dimensions {

	@JsonProperty("Left")
	private String Left;
	@JsonProperty("Top")
	private String Top;
	@JsonProperty("Height")
	private String Height;
	@JsonProperty("Width")
	private String Width;
	@JsonProperty("PageType")
	private String PageType;

}

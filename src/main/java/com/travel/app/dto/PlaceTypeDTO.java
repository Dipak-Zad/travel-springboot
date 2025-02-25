package com.travel.app.dto;

import com.travel.app.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceTypeDTO {

	private Long Id;
	
	private String type;
	
	private String description;
	
	private Status status;
	
	private String fieldName;

	private String fieldValue;
}

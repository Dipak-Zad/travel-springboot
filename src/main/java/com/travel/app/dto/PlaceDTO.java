package com.travel.app.dto;

import com.travel.app.enums.Status;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlaceDTO {

	private Long id;
	
	private String placeName;
	
	private Boolean availability;
	
	private Long placeTypeId;
	
	//private String timing;
	
	@Size(max = 500)
	private String placeAddress;
	
	@Size(max = 1000)
	private String description;
	
	private Status status;
	
	private String fieldName;

	private String fieldValue;
	
	//for eg.
	//@Email(message = "Invalid email format")
	//@Min(value = 10000, message = "Salary must be at least 10,000")
}

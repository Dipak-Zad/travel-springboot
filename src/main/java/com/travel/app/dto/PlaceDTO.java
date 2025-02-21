package com.travel.app.dto;

import com.travel.app.enums.Status;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlaceDTO {

	private String placeName;
	
	private Boolean availability;
	
	private String placeTypeId;
	
	//private String timing;
	
	@Size(max = 500)
	private String placeAddress;
	
	@Size(max = 1000)
	private String description;
	
	private Status status;
	
	
	//for eg.
	//@Email(message = "Invalid email format")
	//@Min(value = 10000, message = "Salary must be at least 10,000")
}

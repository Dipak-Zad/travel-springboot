package com.travel.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlaceDTO {

	@NotBlank(message = "place's name is required")
	private String placeName;
	
	@NotNull(message = "place's availability status is required")
	private Boolean availability;
	
	@NotBlank(message = "place's type is required")
	private String placeTypeId;
	
	private String timing;
	
	@NotNull(message = "place's address is required")
	@Size(max = 500)
	private String placeAddress;
	
	@Size(max = 1000)
	private String description;
	
	@NotNull(message = "place's status is required")
	private String status;
	
	
	//for eg.
	//@Email(message = "Invalid email format")
	//@Min(value = 10000, message = "Salary must be at least 10,000")
}

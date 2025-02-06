package com.travel.app.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDTO {

	@NotBlank(message = "place's name is required")
	private String place_name;
	@NotBlank(message = "place's availability status is required")
	private Boolean availability;
	@NotBlank(message = "place's type is required")
	private Long place_type;
	@NotNull(message = "place's location is required")
	private List<String> location;
	private String description;
	private String status;
	
	
	//for eg.
	//@Email(message = "Invalid email format")
	//@Min(value = 10000, message = "Salary must be at least 10,000")
}

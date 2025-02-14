package com.travel.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceTypeDTO {

	@NotBlank(message="place type is required")
	private String type;
	
	private String description;

	@NotBlank(message="place type status is required")
	private String status;
}

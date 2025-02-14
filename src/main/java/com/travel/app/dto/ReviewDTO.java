package com.travel.app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {

	@NotNull(message = "review user is required")
	private Long userId;
	
	@NotNull(message = "review place is required")
	private Long placeId; 
	
	@NotNull(message = "place rating is required")
	@Min(1)
	@Max(5)
	private Integer rating;
	
	@Size(max = 1500)
	private String comment;
	
	@NotNull(message = "review status is required")
	private String status;
	
}

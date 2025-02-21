package com.travel.app.dto;

import com.travel.app.enums.Status;

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

	private Long userId;
	
	private Long placeId; 
	
	@Min(1)
	@Max(5)
	private Integer rating;
	
	@Size(max = 1500)
	private String review;
	
	private Status status;
	
}

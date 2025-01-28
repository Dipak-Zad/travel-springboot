package com.travel.app.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDTO {

	private String place_name;
	private Boolean availability;
	private Long place_type;
	private List<String> location;
	private String description;
	private String status;
}

package com.travel.app.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceTypeDTO {

	private String type;
	private List<String> location;
	private String description;
	private String status;
}

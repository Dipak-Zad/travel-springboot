package com.travel.app.dto;

import com.travel.app.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {

	private Long Id;
	
	private String role;

	private Status status;
	
	private String fieldName;

	private String fieldValue;
}

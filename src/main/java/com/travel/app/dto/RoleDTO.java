package com.travel.app.dto;

import com.travel.app.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {

	private String role;

	private Status status;
}

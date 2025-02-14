package com.travel.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {

	@NotBlank(message="role is required")
	private String role;

	@NotNull(message="role status is required")
	private String status;
}

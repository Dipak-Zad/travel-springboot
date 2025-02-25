package com.travel.app.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.travel.app.enums.Status;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	private Long Id;
	
	//@NotBlank(message = "user's name is required")
	@Size(min = 2, max = 100)
	private String userName;
	
	//@NotBlank
	@Size(min = 8, message = "password must be atleast 8 characters long")
	private String password;
	
	//@NotNull(message = "user's date of birth is required for age calculation")
	private LocalDateTime dob;
	
	//@NotBlank(message = "user's email is required")
	@Email
	private String email;
	
	//@NotBlank(message = "user's mobile number is required")
	@Pattern(regexp = "\\d{10}")
	@Size(min = 2, max = 100)
	private Long mobileNo;
	
	private String homeAddress;
	
	//@NotNull(message = "user's status is required")
	private Status status;
	
	private String fieldName;

	private String fieldValue;
}

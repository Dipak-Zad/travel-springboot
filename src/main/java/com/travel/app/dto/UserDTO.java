package com.travel.app.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private String user_name;
	private LocalDateTime dob;
	private String email;
	private Long mobile_no;
	private List<String> location;
	private String description;
	private String status;
}

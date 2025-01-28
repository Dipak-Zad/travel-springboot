package com.travel.app.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.Email;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String user_name;
	private String password; 
	private LocalDateTime dob;
	@Email
	private String email;
	private Long mobile_no;
	private List<String> home_location;
	@Column(nullable=false)
	private String status="ACTIVE";
	@CreationTimestamp
	private LocalDateTime created_date;
	private String created_by;
	@UpdateTimestamp
	private LocalDateTime modified_date;
	private String modified_by;
	
}

package com.travel.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.Email;

import jakarta.persistence.*;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.travel.app.enums.*;

@Entity
@Table(name="users")
@Data
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="user_name", nullable = false)
	private String userName;

	@Column(nullable = false)
	private String password;

//	@JsonIgnore
	//@JsonBackReference(value = "user-role")
	@JsonIgnoreProperties({"users"})
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	//@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(nullable = false)
	private LocalDate dob;
	
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(name="mobile_no", nullable = false, unique = true, length = 10)
	private Long mobileNo;
	
	@Column(name="home_address")
	private String homeAddress;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable=false)
	private Status status= Status.ACTIVE;
    
	@CreationTimestamp
	@Column(name="created_date")
	private LocalDateTime createdDate;

    @Column(name="created_by")
	private String createdBy;
    
	@UpdateTimestamp
	@Column(name="modified_date", nullable=false)
	private LocalDateTime modifiedDate;

    @Column(name="modified_by", nullable = false)
	private String modifiedBy;
    
    @JsonManagedReference(value = "user-reviews")
    //@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();
	
}

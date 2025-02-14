package com.travel.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	
	@Column(name="user_name", nullable = false)
	private String userName;

	@Column(nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	@Column(nullable = false)
	private LocalDateTime dob;
	
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(name="mobile_no", nullable = false, unique = true, length = 10)
	private Long mobileNo;
	
	@Column(name="home_address")
	private String homeAddress;
	
    @Column(nullable=false)
	private String status="ACTIVE";
    
	@CreationTimestamp
	@Column(name="created_date", nullable=false)
	private LocalDateTime createdDate;

    @Column(name="created_by", nullable = false)
	private String createdBy;
    
	@UpdateTimestamp
	@Column(name="modified_date", nullable=false)
	private LocalDateTime modifiedDate;

    @Column(name="modified_by", nullable = false)
	private String modifiedBy;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();
	
}

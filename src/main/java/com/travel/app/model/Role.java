package com.travel.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.travel.app.enums.*;

@Entity
@Table(name="role")
@Data
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false, unique = true)
	private String role; //SuperAdmin/Admin/User etc
    
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
	
    @JsonManagedReference(value = "user-role")
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<Users> users = new ArrayList<>();	
}

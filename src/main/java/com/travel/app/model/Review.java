package com.travel.app.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.travel.app.enums.Status;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="review")
@Data
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

	@ManyToOne
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;

	@Column(nullable = false)
	private Integer rating;
	
	@Column(length = 1500)
	private String review;
	
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
	
	
}
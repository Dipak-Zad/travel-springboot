package com.travel.app.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="review")
@Data
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long user_id;
	private Long place_id; //true = avail/false = unavail
	private Long rating;
	private String comment;
	@Column(nullable=false)
	private String status="ACTIVE";
	@CreationTimestamp
	private LocalDateTime created_date;
	private String created_by;
	@UpdateTimestamp
	private LocalDateTime modified_date;
	private String modified_by;
	
}

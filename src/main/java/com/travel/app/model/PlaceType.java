package com.travel.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="place_type")
@Data
public class PlaceType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String type;
	
	private String description;
	
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
	
	@OneToMany(mappedBy = "placeType", cascade = CascadeType.ALL)
	private List<Place> places = new ArrayList<>();
	
}

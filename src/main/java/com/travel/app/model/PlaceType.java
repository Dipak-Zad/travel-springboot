package com.travel.app.model;

import java.time.LocalDateTime;
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
	private String type;
	private List<String> location;
	private String description;
	@Column(nullable=false)
	private String status="ACTIVE";
	@CreationTimestamp
	private LocalDateTime created_date;
	private String created_by;
	@UpdateTimestamp
	private LocalDateTime modified_date;
	private String modified_by;
	
}

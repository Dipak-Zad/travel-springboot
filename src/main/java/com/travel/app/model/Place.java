package com.travel.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="place")
@Data
public class Place {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="place_name", nullable = false)
	private String placeName;

	@Column(nullable = false)
	private Boolean availability; //true = avail/false = unavail

	@ManyToOne
	@JoinColumn(name = "place_type_id", nullable = false)
	private PlaceType placeType;
	
	private List<String> timing; //opening days & hrs
	
	@Column(name ="place_address", nullable = false)
	private String placeAddress; 

	//private List<String> location; //coordinates should be saved , will try later on 
	
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
	
	@OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();

}

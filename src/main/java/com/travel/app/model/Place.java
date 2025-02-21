package com.travel.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

import com.travel.app.enums.*;

@Entity
@Table(name="place")
@Data
public class Place {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="place_name")
	private String placeName;

	@Column(nullable = false)
	private Boolean availability; //true = avail/false = unavail

	@ManyToOne
	@JoinColumn(name = "place_type_id")
	private PlaceType placeType;
	
	//private String timing; //opening days & hrs
	
	@Column(name ="place_address")
	private String placeAddress; 

	//private List<String> location; //coordinates should be saved , will try later on 
	
	private String description;
	
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
	
	@OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();

}

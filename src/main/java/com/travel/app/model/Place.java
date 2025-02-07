package com.travel.app.model;

import java.time.LocalDateTime;
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
	private String place_name;
	private Boolean availability; //true = avail/false = unavail
	private String place_type;
	private String place_address; 
	//private List<String> location; //coordinates should be saved , will try later on 
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

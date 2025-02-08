package com.travel.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.app.dto.PlaceDTO;
import com.travel.app.model.ApiResponse;
import com.travel.app.model.Place;
import com.travel.app.service.PlaceService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/places")
public class PlaceController {

	@Autowired
	private PlaceService PlaceServ;
	
	@PostMapping
	public ResponseEntity<ApiResponse<Place>> savePlace(@Valid @RequestBody PlaceDTO placeDTO)
	{
		Place place = PlaceServ.saveSinglePlace(placeDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "New place saved successfully", place));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<Place>>> getAllPlace()
	{
		List<Place> places = PlaceServ.findAllPlaces();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All places fetched successfully", places));
	}
	
	@GetMapping("/{p_id}")
	public ResponseEntity<ApiResponse<Optional<Place>>> getPlaceById(@PathVariable Long id)
	{
		Optional<Place> place = PlaceServ.findPlaceById(id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Place found", place));
	}
	
	@PutMapping("/{p_id}")
	public ResponseEntity<ApiResponse<Place>> updatePlace(@PathVariable("p_id") Long p_id, @Valid @RequestBody PlaceDTO placeDTO)
	{
		Place place = PlaceServ.updatePlace(p_id, placeDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","Place updated succesfully", place));
	}
	
	@DeleteMapping("/{p_id}")
	public ResponseEntity<ApiResponse<Void>> deletePlace(@PathVariable Long p_id)
	{
		PlaceServ.deletePlaceById(p_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Place deleted succesfully", null));
	}
}

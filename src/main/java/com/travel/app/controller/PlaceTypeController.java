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

import com.travel.app.dto.PlaceTypeDTO;
import com.travel.app.model.ApiResponse;
import com.travel.app.model.PlaceType;
import com.travel.app.service.PlaceTypeService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/placeTypes")
public class PlaceTypeController {

	@Autowired
	private PlaceTypeService PlaceTypeServ;
	
	@PostMapping("/savePlaceType")
	public ResponseEntity<ApiResponse<PlaceType>> savePlaceType(@Valid @RequestBody PlaceTypeDTO placeTypeDTO)
	{
		PlaceType placeType = PlaceTypeServ.saveSinglePlaceType(placeTypeDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "New placeType saved successfully", placeType));
	}
	
	@GetMapping("/getAllPlaceTypes")
	public ResponseEntity<ApiResponse<List<PlaceType>>> getAllPlaceType()
	{
		List<PlaceType> placeTypes = PlaceTypeServ.findAllPlaceTypes();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All placeTypes fetched successfully", placeTypes));
	}
	
	@GetMapping("/findPlaceType/{p_id}")
	public ResponseEntity<ApiResponse<Optional<PlaceType>>> getPlaceTypeById(@PathVariable Long id)
	{
		Optional<PlaceType> placeType = PlaceTypeServ.findPlaceTypeById(id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "PlaceType found", placeType));
	}
	
	@PutMapping("/updatePlaceType/{pt_id}")
	public ResponseEntity<ApiResponse<PlaceType>> updatePlaceType(@PathVariable("p_id") Long pt_id, @Valid @RequestBody PlaceTypeDTO placeTypeDTO)
	{
		PlaceType placeType = PlaceTypeServ.updatePlaceType(pt_id, placeTypeDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","PlaceType updated succesfully", placeType));
	}
	
	@DeleteMapping("deletePlaceType/{pt_id}")
	public ResponseEntity<ApiResponse<Void>> deletePlaceType(@PathVariable Long pt_id)
	{
		PlaceTypeServ.deletePlaceTypeById(pt_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "PlaceType deleted succesfully", null));
	}
}

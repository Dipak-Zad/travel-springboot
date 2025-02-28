package com.travel.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.app.dto.PlaceDTO;
import com.travel.app.dto.PlaceDTO;
import com.travel.app.model.ApiResponse;
import com.travel.app.model.Place;
import com.travel.app.model.Place;
import com.travel.app.service.PlaceService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/places")
public class PlaceController {

	@Autowired
	private PlaceService PlaceServ;
	
	@PostMapping("/savePlace")
	public ResponseEntity<ApiResponse<Place>> savePlace(@Valid @RequestBody PlaceDTO placeDTO)
	{
		Place place = PlaceServ.saveSinglePlace(placeDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "New place saved successfully", place));
	}
	
	@PostMapping("/saveAllPlace")
	public ResponseEntity<ApiResponse<List<Place>>> saveAllPlace(@Valid @RequestBody List<PlaceDTO> placeDTO)
	{
		List<Place> placeList = PlaceServ.saveAllPlace(placeDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "All places saved successfully", placeList));
	}
	
	@GetMapping("/findPlace/{p_id}")
	public ResponseEntity<ApiResponse<Optional<Place>>> getPlaceById(@PathVariable("p_id") Long id)
	{
		Optional<Place> place = PlaceServ.findPlaceById(id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Place found", place));
	}
	
	@GetMapping("/findPlaceByType/{p_type}")
	public ResponseEntity<ApiResponse<Optional<Place>>> getPlaceByType(@PathVariable("p_type") Long id)
	{
		Optional<Place> place = PlaceServ.findPlaceByType(id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Place found", place));
	}
	
	@GetMapping("/findPlaceByField")
	public <T> ResponseEntity<ApiResponse<List<Place>>> getPlaceByField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		List<Place> places = PlaceServ.findPlaceByField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success", "All places fetched successfully", places));
	}
	
	@GetMapping("/getAllPlaces")
	public ResponseEntity<ApiResponse<List<Place>>> getAllPlace()
	{
		List<Place> places = PlaceServ.findAllPlaces();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All places fetched successfully", places));
	}
	
	@GetMapping("/getAllPlacesInPages")
	public ResponseEntity<ApiResponse<Page<Place>>> getAllPlaceInPages(@RequestParam(value="page_number", defaultValue = "0") int pageNumber, @RequestParam(value="page_size", defaultValue = "10") int pageSize, @RequestParam(value="sort_by_field", defaultValue = "id") String sortByField, @RequestParam(value="sort_direction", defaultValue = "asc") String sortDirection)
	{
		Page<Place> places = PlaceServ.findAllPlaceInPages(pageNumber, pageSize, sortByField, sortDirection);
		return ResponseEntity.ok(new ApiResponse<>("Success", "All places fetched successfully", places));
	}
	
	@PutMapping("/updatePlace/{p_id}")
	public ResponseEntity<ApiResponse<Place>> updatePlace(@PathVariable("p_id") Long p_id, @Valid @RequestBody PlaceDTO placeDTO)
	{
		Place place = PlaceServ.updatePlace(p_id, placeDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","Place updated succesfully", place));
	}
	
	@PutMapping("/updateAllPlace")
	public ResponseEntity<ApiResponse<List<Place>>> updateAllPlace(@Valid @RequestBody List<PlaceDTO> placeDTO) //@RequestBody List<Long> pt_id,
	{
		List<Place> place = PlaceServ.updateAllPlaces(placeDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","Place updated succesfully", place));
	}
	
	@PutMapping("/updateAllPlaceBySingleField")
	public <T> ResponseEntity<ApiResponse<List<Place>>> updateAllPlaceBySingleField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		List<Place> place = PlaceServ.updateAllPlaceBySingleField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success","Place updated succesfully", place));
	}
	
	@DeleteMapping("deletePlace/{p_id}")
	public ResponseEntity<ApiResponse<Void>> deletePlace(@PathVariable("p_id") Long p_id)
	{
		PlaceServ.deletePlaceById(p_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Place deleted succesfully", null));
	}
	
	@DeleteMapping("/deleteAllPlace")
	public ResponseEntity<ApiResponse<Void>> deleteAllPlace() throws Exception
	{
		PlaceServ.deleteAllPlaces();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All Places deleted succesfully", null));
	}
	
	@DeleteMapping("/deletePlaceByField")
	public <T> ResponseEntity<ApiResponse<Void>> deletePlaceByField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		PlaceServ.deletePlaceByField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Place deleted succesfully", null));
	}
}

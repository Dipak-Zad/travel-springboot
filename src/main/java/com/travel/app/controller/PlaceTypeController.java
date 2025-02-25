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
	
	@PostMapping("/saveAllPlaceType")
	public ResponseEntity<ApiResponse<List<PlaceType>>> saveAllPlaceType(@Valid @RequestBody List<PlaceTypeDTO> placeTypeDTO)
	{
		List<PlaceType> placeTypeList = PlaceTypeServ.saveAllPlaceType(placeTypeDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "All placeTypes saved successfully", placeTypeList));
	}
	
	@GetMapping("/findPlaceType/{pt_id}")
	public ResponseEntity<ApiResponse<Optional<PlaceType>>> getPlaceTypeById(@PathVariable("pt_id") Long pt_id)
	{
		Optional<PlaceType> placeType = PlaceTypeServ.findPlaceTypeById(pt_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "PlaceType found", placeType));
	}
	
	@GetMapping("/findPlaceTypeByField")
	public <T> ResponseEntity<ApiResponse<List<PlaceType>>> getPlaceTypeByField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		List<PlaceType> placeTypes = PlaceTypeServ.findPlaceTypeByField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success", "All placeTypes fetched successfully", placeTypes));
	}
	
	@GetMapping("/getAllPlaceTypes")
	public ResponseEntity<ApiResponse<List<PlaceType>>> getAllPlaceType()
	{
		List<PlaceType> placeTypes = PlaceTypeServ.findAllPlaceTypes();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All placeTypes fetched successfully", placeTypes));
	}
	
	@GetMapping("/getAllPlaceTypesInPages")
	public ResponseEntity<ApiResponse<Page<PlaceType>>> getAllPlaceTypeInPages(@RequestParam(value="page_number", defaultValue = "0") int pageNumber, @RequestParam(value="page_size", defaultValue = "10") int pageSize, @RequestParam(value="sort_by_field", defaultValue = "id") String sortByField, @RequestParam(value="sort_direction", defaultValue = "asc") String sortDirection)
	{
		Page<PlaceType> placeTypes = PlaceTypeServ.findAllPlaceTypeInPages(pageNumber, pageSize, sortByField, sortDirection);
		return ResponseEntity.ok(new ApiResponse<>("Success", "All placeTypes fetched successfully", placeTypes));
	}
	
	@PutMapping("/updatePlaceType/{pt_id}")
	public ResponseEntity<ApiResponse<PlaceType>> updatePlaceType(@PathVariable("pt_id") Long pt_id, @Valid @RequestBody PlaceTypeDTO placeTypeDTO)
	{
		PlaceType placeType = PlaceTypeServ.updatePlaceType(pt_id, placeTypeDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","PlaceType updated succesfully", placeType));
	}
	
	@PutMapping("/updateAllPlaceType")
	public ResponseEntity<ApiResponse<List<PlaceType>>> updateAllPlaceType(@Valid @RequestBody List<PlaceTypeDTO> placeTypeDTO) //@RequestBody List<Long> pt_id,
	{
		List<PlaceType> placeType = PlaceTypeServ.updateAllPlaceTypes(placeTypeDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","PlaceType updated succesfully", placeType));
	}
	
//	@PutMapping("/updateAllPlaceTypeByField")
//	public ResponseEntity<ApiResponse<List<PlaceType>>> updateAllPlaceTypeByFields(@Valid @RequestBody List<PlaceTypeDTO> placeTypeDTO)
//	{
//		List<PlaceType> placeType = PlaceTypeServ.updateAllPlaceTypeByFields(placeTypeDTO);
//		return ResponseEntity.ok(new ApiResponse<>("Success","PlaceType updated succesfully", placeType));
//	}
	
	@PutMapping("/updateAllPlaceTypeBySingleField")
	public <T> ResponseEntity<ApiResponse<List<PlaceType>>> updateAllPlaceTypeBySingleField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		List<PlaceType> placeType = PlaceTypeServ.updateAllPlaceTypeBySingleField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success","PlaceType updated succesfully", placeType));
	}
	
	@DeleteMapping("/deletePlaceType/{pt_id}")
	public ResponseEntity<ApiResponse<Void>> deletePlaceType(@PathVariable("pt_id") Long pt_id)
	{
		PlaceTypeServ.deletePlaceTypeById(pt_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "PlaceType deleted succesfully", null));
	}
	
	@DeleteMapping("/deleteAllPlaceType")
	public ResponseEntity<ApiResponse<Void>> deleteAllPlaceType() throws Exception
	{
		PlaceTypeServ.deleteAllPlaceTypes();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All PlaceTypes deleted succesfully", null));
	}
	
	@DeleteMapping("/deletePlaceTypeByField")
	public <T> ResponseEntity<ApiResponse<Void>> deletePlaceTypeByField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		PlaceTypeServ.deletePlaceTypeByField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success", "PlaceType deleted succesfully", null));
	}
}

package com.travel.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.app.dto.PlaceDTO;
import com.travel.app.model.Place;
import com.travel.app.service.PlaceService;
@RestController
@RequestMapping("/api/places")
public class PlaceController {

	private final PlaceService PlaceServ;

	public PlaceController(PlaceService PlaceServ)
	{
		this.PlaceServ = PlaceServ;
	}
	
	@GetMapping
	public List<Place> getAllPlace()
	{
		return PlaceServ.findAllPlaces();
	}
	
	@PostMapping
	public Place savePlace(@RequestBody PlaceDTO placeDTO)
	{
		return PlaceServ.saveSinglePlace(placeDTO);
	}
	
	@PutMapping
	public Place updatePlace(@RequestBody PlaceDTO placeDTO,@PathVariable("p_id") Long p_id)
	{
		return PlaceServ.updatePlace(placeDTO, p_id);
	}
	
	@DeleteMapping
	public String deletePlace(@PathVariable Long p_id)
	{
		PlaceServ.deletePlaceById(p_id);
		return null;
	}
}

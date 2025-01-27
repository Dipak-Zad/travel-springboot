package com.travel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.service.PlaceService;
import com.travel.model.Place;
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
		return PlaceServ.getAllPlaces();
	}
	
	@PostMapping
	public Place savePlace(@RequestBody Place place)
	{
		return PlaceServ.savePlace(place);
	}
}

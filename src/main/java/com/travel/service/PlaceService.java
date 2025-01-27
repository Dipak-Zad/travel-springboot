package com.travel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.travel.repository.PlaceRepository;
import com.travel.model.Place;

@Service
public class PlaceService {

	private final PlaceRepository PlaceRepo;
	
	public PlaceService(PlaceRepository PlaceRepo)
	{
		this.PlaceRepo = PlaceRepo;
	}
	
	public List<Place> getAllPlaces()
	{
		return PlaceRepo.findAll();
	}
	
	public Place savePlace(Place place)
	{
		return PlaceRepo.save(place);
	}
}

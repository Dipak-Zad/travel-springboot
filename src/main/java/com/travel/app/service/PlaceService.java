package com.travel.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.app.model.Place;
import com.travel.app.repository.PlaceRepository;

@Service
public class PlaceService {

	@Autowired
	private PlaceRepository PlaceRepo;
	
	public List<Place> getAllPlaces()
	{
		return PlaceRepo.findAll();
	}
	
	public Place savePlace(Place place)
	{
		return PlaceRepo.save(place);
	}
	
	public Place saveSinglePlace(Place place)
	{
		try {
			
			Place plc = PlaceRepo.save(place);
			
			return plc == null ? null : plc;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Place getSinglePlace(Long place_id)
	{
		try {
			
			Place plc = PlaceRepo.getById(place_id);
			
			return plc == null ? null : plc;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Place updateSinglePlace(Long place_id,Place place)
	{
		try {
			
			Place plc = PlaceRepo.getById(place_id);
			plc = place;
			return plc == null ? null : plc;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}

package com.travel.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.app.dto.PlaceDTO;
import com.travel.app.model.Place;
import com.travel.app.repository.PlaceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PlaceServiceImpl implements PlaceService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PlaceRepository PlaceRepo;
	
	@Override
	public Place saveSinglePlace(PlaceDTO placeDTO)
	{
		try
		{
			//String pName = placeDTO.get
			//if()
			Place plc = modelMapper.map(placeDTO, Place.class);
			plc = PlaceRepo.save(plc);
			return plc == null ? null : plc;	
		}
		catch(Exception e)
		{
			//throw new CustomException("Error saving place: "+e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<Place> saveAllPlace(List<PlaceDTO> placesDTO)
	{
		try
		{
			List<Place> places = new ArrayList<Place>();
			for(PlaceDTO tempPlcDTO : placesDTO)
			{
				Place tempPlc = modelMapper.map(tempPlcDTO, Place.class);
				places.add(tempPlc);
			}
			places = PlaceRepo.saveAll(places);
			return places == null ? null : places; 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Optional<Place> findPlaceById(Long id)
	{
		try
		{
			Optional<Place> plc = PlaceRepo.findById(id);
			return plc == null ? null : plc;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Place> findAllPlaces()
	{
		try
		{
			List<Place> plcs = PlaceRepo.findAll();
			return plcs == null ? null : plcs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Place updatePlace(Long id, PlaceDTO placeDTO)
	{
		try
		{
			Place plc = PlaceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No place found with ID "+id));
			if(plc!=null)
			{
				plc = modelMapper.map(placeDTO, Place.class);
			}
			
			return plc == null ? null : plc;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deletePlaceById(Long id) {
		try
		{
			PlaceRepo.deleteById(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteAllPlaces() {
		try
		{
			PlaceRepo.deleteAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Optional<Place> findPlaceByNameAndLocation(String pName, String pLocation)
	{
		try{
			Optional<Place> plc = PlaceRepo.findPlaceByNameAndLocation(pName, pLocation);
			return plc == null ? null : plc;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
//	private Place DtoToPlace(PlaceDTO placeDTO) {
//		Place place = modelMapper.map(placeDTO, Place.class);
//		return place;
//	}
//	
//	private PlaceDTO CategoryToDto(Place place) {
//		PlaceDTO placeDTO = modelMapper.map(place, PlaceDTO.class);
//		return placeDTO;
//	}
	

}

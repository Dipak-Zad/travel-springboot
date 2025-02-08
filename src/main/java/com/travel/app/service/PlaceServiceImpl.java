package com.travel.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.travel.app.dto.PlaceDTO;
import com.travel.app.exception.DuplicateEntityException;
import com.travel.app.exception.SaveEntityException;
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
			String pName = placeDTO.getPlace_name();
			String pAddress = placeDTO.getPlace_address();
 			Optional<Place> pCheck = PlaceRepo.findPlaceByNameAndAddress(pName, pAddress);
 			Place plc = new Place();
			if(pCheck == null)
			{
				plc = modelMapper.map(placeDTO, Place.class);
				plc = PlaceRepo.save(plc);
				if(plc != null)
				{
					//return plc == null ? null : plc;
					return plc;
				}
				else
				{
					throw new SaveEntityException("Failed to save place");	
				}
			}
			else
			{
				throw new DuplicateEntityException("Place with name '"+placeDTO.getPlace_name()+
													"'at location '"+placeDTO.getPlace_address()+"' already exists.");	
			}
			
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save place");
			//e.printStackTrace();
		}
		
	}
	
	@Override
	public List<Place> saveAllPlace(List<PlaceDTO> placesDTO)
	{
		try
		{
			List<Place> places = new ArrayList<Place>();
			Optional<Place> OptPlace = null;
			String pName,pAddress;
			for(PlaceDTO plcDTO : placesDTO)
			{
				pName = plcDTO.getPlace_name();
				pAddress = plcDTO.getPlace_address();
				OptPlace = PlaceRepo.findPlaceByNameAndAddress(pName, pAddress);
				if(OptPlace==null)
				{
					Place tempPlc = modelMapper.map(plcDTO, Place.class);
					places.add(tempPlc);
				}
				else
				{
					throw new DuplicateEntityException("Place with name '"+plcDTO.getPlace_name()+
							"'at location '"+plcDTO.getPlace_address()+"' already exists.");
				}
				
			}

			places = PlaceRepo.saveAll(places);
			if(places != null)
			{
				return places;
			}
			else
			{
				throw new SaveEntityException("Failed to save given places");
			}
			//return places == null ? null : places; 
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save given places");
			//e.printStackTrace();
		}
		
		//return null;
	}
	
	@Override
	public Optional<Place> findPlaceById(Long id)
	{
		try
		{
			Optional<Place> plc = PlaceRepo.findById(id);
			if(plc!=null)
			{
				return plc;
			}
			else
			{
				throw new EntityNotFoundException("Place with '"+id+"' not found");
			}
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Place with '"+id+"' not found");
		}
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
			throw new EntityNotFoundException("Failed to All places");
		}
	}
	
	@Override
	public Place updatePlace(Long id, PlaceDTO placeDTO)
	{
		try
		{
			Place plc = PlaceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No place found with ID "+id));
			if(plc==null)
			{
				plc = modelMapper.map(placeDTO, Place.class);
				return plc;
			}
			else
			{
				throw new SaveEntityException("Failed to save place");
			}
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save place");
		}
	}

	@Override
	public void deletePlaceById(Long id) {
		try
		{
			PlaceRepo.deleteById(id);
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to All places");
		}
		
	}

	@Override
	public void deleteAllPlaces() throws Exception {
		try
		{
			PlaceRepo.deleteAll();
		}
		catch(Exception e)
		{
			throw new Exception("Failed to delete all the places");
		}
		
	}
	
	@Override
	public Optional<Place> findPlaceByNameAndLocation(String pName, String pLocation)
	{
		try{
			Optional<Place> plc = PlaceRepo.findPlaceByNameAndAddress(pName, pLocation);
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

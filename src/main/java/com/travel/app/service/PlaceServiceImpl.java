package com.travel.app.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.travel.app.dto.PlaceDTO;
import com.travel.app.exception.DuplicateEntityException;
import com.travel.app.exception.SaveEntityException;
import com.travel.app.model.Place;
import com.travel.app.repository.PlaceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PlaceServiceImpl<T> implements PlaceService {

	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	private PlaceRepository PlaceRepo;
	
	
	@Override
	public Place saveSinglePlace(PlaceDTO placeDTO)
	{
		try
		{
			String pName = placeDTO.getPlaceName();
			String pAddress = placeDTO.getPlaceAddress();
 			Optional<Place> pCheck = PlaceRepo.findPlaceByNameAndAddress(pName, pAddress);
 			Place place = new Place();
 			
			if(pCheck.isPresent())
			{
				throw new DuplicateEntityException("Place with name '"+placeDTO.getPlaceName()+
						"'at location '"+placeDTO.getPlaceAddress()+"' already exists.");
			}
			
			place = modelMapper.map(placeDTO, Place.class);
			place = PlaceRepo.save(place);
			
			if(place == null)
			{
				throw new SaveEntityException("Failed to save place");
				
			}
			return place;
			
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
			for(PlaceDTO placeDTO : placesDTO)
			{
				pName = placeDTO.getPlaceName();
				pAddress = placeDTO.getPlaceAddress();
				OptPlace = PlaceRepo.findPlaceByNameAndAddress(pName, pAddress);
				
				if(OptPlace.isPresent())
				{
					throw new DuplicateEntityException("Place with name '"+placeDTO.getPlaceName()+
							"'at location '"+placeDTO.getPlaceAddress()+"' already exists.");
				}
					
				Place tempPlc = modelMapper.map(placeDTO, Place.class);
				places.add(tempPlc);
			}

			places = PlaceRepo.saveAll(places);
			if(places == null)
			{
				throw new SaveEntityException("Failed to save given places");
			}
			
			//return places == null ? null : places;
			return places;
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
			Optional<Place> place = PlaceRepo.findById(id);
			if(place==null)
			{
				throw new EntityNotFoundException("Place with '"+id+"' not found");
				
			}
			
			return place;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Place with '"+id+"' not found");
		}
	}
	

//	@Override
//	public <T> List<Place> findPlaceByField(T fieldName,T fieldValue)
//	{
//		try
//		{
//			List<Place> places = PlaceRepo.searchByField(fieldName, fieldValue);
//			if(places==null)
//			{
//				throw new EntityNotFoundException("Place with '"+fieldName+"' '"+fieldValue+"' not found");
//			}
//			return places;
//		}
//		catch(Exception e)
//		{
//			throw new EntityNotFoundException("Place with '"+fieldName+"' '"+fieldValue+"' not found");
//		}
//	}
	
	
	@Override
	public Optional<Place> findPlaceByNameAndAddress(String pName, String pLocation)
	{
		try{
			Optional<Place> place = PlaceRepo.findPlaceByNameAndAddress(pName, pLocation);
			return place == null ? null : place;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Place with name '"+pLocation+"' not found");
		}
	}
	
	
	@Override
	public List<Place> findAllPlaces()
	{
		try
		{
			List<Place> places = PlaceRepo.findAll();
			return places == null ? null : places;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all places");
		}
	}
	
	
	@Override
	public <T> Page<Place> findAllPlaceInPages(int page, int size, T sortBy, String sortDir)
	{
		try
		{
			Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(String.valueOf(sortBy)).ascending() : Sort.by(String.valueOf(sortBy)).descending();
			Pageable pageable = PageRequest.of(page, size, sort);
			return PlaceRepo.findAll(pageable);
					
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all places");
		}
	}
	
	
	@Override
	public Place updatePlace(Long id, PlaceDTO placeDTO)
	{
		try
		{
			Place place = PlaceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No place found with ID "+id));
		
			if(placeDTO.getPlaceName()!=null && !placeDTO.getPlaceName().trim().isEmpty())
			{
				place.setPlaceName(placeDTO.getPlaceName());
			}
			
			if(placeDTO.getAvailability()!=null)
			{
				place.setPlaceName(placeDTO.getPlaceName());
			}
			
			if(placeDTO.getPlaceAddress()!=null && !placeDTO.getPlaceAddress().trim().isEmpty())
			{
				place.setPlaceAddress(placeDTO.getPlaceAddress());
			}
			
			if(placeDTO.getDescription()!=null && !placeDTO.getDescription().trim().isEmpty())
			{
				place.setDescription(placeDTO.getDescription());
			}
			
			if(placeDTO.getStatus()!=null)
			{
				place.setStatus(placeDTO.getStatus());
			}
			
			place.setModifiedDate(LocalDateTime.now());
			place.setModifiedBy("new current session user");
			
			return PlaceRepo.save(place);
			
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update place");
		}
	}
	
	
	@Override
	public List<Place> updateAllPlaces(List<Long> idList, List<PlaceDTO> placesDTO)
	{
		try
		{
			if(idList.size() != placesDTO.size())
			{
				throw new IllegalArgumentException("ID list & DTO list must have the same size");
			}
			
			List<Place> places = new ArrayList<>();
			
			for(int i=0; i<idList.size();i++)
			{
				final Long placeId = idList.get(i);
				Place place = PlaceRepo.findById(placeId).orElseThrow(() -> new EntityNotFoundException("No place found with ID "+placeId));
				PlaceDTO placeDTO = placesDTO.get(i);
				
				if(placeDTO.getPlaceName()!=null && !placeDTO.getPlaceName().trim().isEmpty())
				{
					place.setPlaceName(placeDTO.getPlaceName());
				}
				
				if(placeDTO.getAvailability()!=null)
				{
					place.setPlaceName(placeDTO.getPlaceName());
				}
				
				if(placeDTO.getPlaceAddress()!=null && !placeDTO.getPlaceAddress().trim().isEmpty())
				{
					place.setPlaceAddress(placeDTO.getPlaceAddress());
				}
				
				if(placeDTO.getDescription()!=null && !placeDTO.getDescription().trim().isEmpty())
				{
					place.setDescription(placeDTO.getDescription());
				}
				
				if(placeDTO.getStatus()!=null)
				{
					place.setStatus(placeDTO.getStatus());
				}
				
				place.setModifiedDate(LocalDateTime.now());
				place.setModifiedBy("new current session user");
				
				places.add(PlaceRepo.save(place));		
			}
			
			return places;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update place");
		}
	}
	
	
//	@Override
//	public <T> List<Place> updateAllPlaceByFields(List<T> placeFieldList, List<T> placeValueList, List<PlaceDTO> updatePlacesDTO)
//	{
//		if(placeFieldList.size() != placeValueList.size() || placeValueList.size() != updatePlacesDTO.size())
//		{
//			throw new IllegalArgumentException("ID list & DTO list must have the same size");
//		}
//		try
//		{
//			List<Place> updatedPlaces = new ArrayList<>();
//			
//			for(int i=0;i<placeFieldList.size();i++)
//			{
//				T fieldName = placeFieldList.get(i);
//				T fieldValue = placeValueList.get(i);
//				PlaceDTO placeDTO = updatePlacesDTO.get(i);
//				updatedPlaces = findPlaceByField(fieldName,fieldValue);
//				modelMapper.map(placeDTO, updatedPlaces.get(i));
//				updatedPlaces.add(PlaceRepo.save(updatedPlaces.get(i)));
//				
//				throw new EntityNotFoundException("No place found with "+fieldName+" : '"+fieldValue+"'");
//		
//			}
//			
//			return updatedPlaces;
//		}
//		catch(Exception e)
//		{
//			throw new SaveEntityException("Failed to update given places" +e.getMessage());
//		}
//	}
	
	
//	@Override
//	public <T> List<Place> updateAllPlaceBySingleField(T fieldName, T fieldValue)
//	{
//		
//		try
//		{
//			List<Place> places = PlaceRepo.findAll();
//			for(Place place : places)
//			{
//				setFieldValue(place, fieldName, fieldValue);
//				PlaceRepo.save(place);
//			}
//			return places;
//		}
//		catch(Exception e)
//		{
//			throw new SaveEntityException("Failed to update all place's "+fieldName+" with '"+fieldValue+"'");
//		}
//	}
	
	
//	@Override
//	public <T> void setFieldValue(Place place, T fieldName, T fieldValue)
//	{
//		try
//		{
//			Field field = Place.class.getDeclaredField(fieldName.toString());
//			field.setAccessible(true);
//			field.set(place, fieldValue);
//		}
//		catch(NoSuchFieldException | IllegalAccessException e)
//		{
//			throw new IllegalArgumentException("Invalid field: "+fieldName);
//		}
//	}
 
	
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

	
//	@Override
//	public <T> void deletePlaceByField(T fieldName, T fieldValue)
//	{
//		try
//		{
//			List<Place> places = findPlaceByField(fieldName, fieldValue);
//			PlaceRepo.deleteAll(places);
//		}
//		catch(Exception e)
//		{
//			throw new IllegalArgumentException("Failed to delete place by "+fieldName+" : '"+fieldValue+"'");
//		}
//		
//	}
	
	
	
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

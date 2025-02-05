package com.travel.app.service;

import java.util.List;
import java.util.Optional;

import com.travel.app.dto.PlaceDTO;
import com.travel.app.model.Place;

public interface PlaceService {
	
	Place saveSinglePlace(PlaceDTO placeDTO);
	List<Place> saveAllPlace(List<PlaceDTO> placesDTO);
	Optional<Place> findPlaceById(Long id);
	//find place by other field
	List<Place> findAllPlaces();
	//find all pageable 
	Place updatePlace(PlaceDTO placeDTO, Long id);
	//custom update all query
	void deletePlaceById(Long id);
	void deleteAllPlaces();
	//delete place by other field

}

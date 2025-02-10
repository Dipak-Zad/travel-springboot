package com.travel.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.travel.app.dto.PlaceDTO;
import com.travel.app.model.Place;

public interface PlaceService {
	
	Place saveSinglePlace(PlaceDTO placeDTO);
	List<Place> saveAllPlace(List<PlaceDTO> placesDTO);
	Optional<Place> findPlaceById(Long id);
	<T> List<Place> findPlaceByField(T fieldname,T fieldvalue);
	Optional<Place> findPlaceByNameAndLocation(String pName, String pLocation);
	List<Place> findAllPlaces();
	<T> Page<Place> findAllPlaceInPages(int page, int size, T sortBy, String sortDir);
	Place updatePlace(Long id, PlaceDTO placeDTO);
	List<Place> updateAllPlaces(List<Long> idList, List<PlaceDTO> placeDTO);
	//updateAllrandom field
	void deletePlaceById(Long id);
	void deleteAllPlaces() throws Exception;
	//delete place by other field

}

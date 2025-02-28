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
	<T> List<Place> findPlaceByField(String fieldname,T fieldvalue);
	Optional<Place> findPlaceByNameAndAddress(String pName, String pAddress);
	Optional<Place> findPlaceByType(Long TypeId);
	List<Place> findAllPlaces();
	<T> Page<Place> findAllPlaceInPages(int pageNumber, int pageSize, String sortByField, String sortDirection);
	Place updatePlace(Long id, PlaceDTO placeDTO);
	List<Place> updateAllPlaces(List<PlaceDTO> placeDTOList);
	//<T> List<Place> updateAllPlaceByFields(List<T> fieldList, List<T> valueList, List<PlaceDTO> placesDTO);
	<T> List<Place> updateAllPlaceBySingleField(String fieldName, T fieldValue);
	<T> void setFieldValue(Place place, String fieldlName, T fieldValue);
	void deletePlaceById(Long id);
	void deleteAllPlaces() throws Exception;
	<T> void deletePlaceByField(String fieldName, T fieldValue);

}

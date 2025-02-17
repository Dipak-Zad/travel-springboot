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
	Optional<Place> findPlaceByNameAndAddress(String pName, String pAddress);
	List<Place> findAllPlaces();
	<T> Page<Place> findAllPlaceInPages(int pageNumber, int pageSize, T sortByField, String sortDirection);
	Place updatePlace(Long id, PlaceDTO placeDTO);
	List<Place> updateAllPlaces(List<Long> idList, List<PlaceDTO> placeDTOList);
	<T> List<Place> updateAllPlaceByFields(List<T> fieldList, List<T> valueList, List<PlaceDTO> placesDTO);
	<T> List<Place> updateAllPlaceBySingleField(T fieldName, T fieldValue);
	<T> void setFieldValue(Place place, T fiedlName, T fieldValue);
	void deletePlaceById(Long id);
	void deleteAllPlaces() throws Exception;
	<T> void deletePlaceByField(T fieldName, T fieldValue);

}

package com.travel.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.travel.app.dto.PlaceTypeDTO;
import com.travel.app.model.PlaceType;

public interface PlaceTypeService {
	
	PlaceType saveSinglePlaceType(PlaceTypeDTO placeTypeDTO);
	List<PlaceType> saveAllPlaceType(List<PlaceTypeDTO> placeTypesDTO);
	Optional<PlaceType> findPlaceTypeById(Long id);
	//<T> List<PlaceType> findPlaceTypeByField(String fieldName,T fieldvalue);
	//Optional<PlaceType> findPlaceTypeByNameAndLocation(String pName, String pAddress);
	List<PlaceType> findAllPlaceTypes();
	<T> Page<PlaceType> findAllPlaceTypeInPages(int pageNumber, int pageSize, T sortByField, String sortDirection);
	PlaceType updatePlaceType(Long id, PlaceTypeDTO placeTypeDTO);
	List<PlaceType> updateAllPlaceTypes(List<Long> idList, List<PlaceTypeDTO> placeTypeDTOList);
	//<T> List<PlaceType> updateAllPlaceTypeByFields(List<T> fieldList, List<T> valueList, List<PlaceTypeDTO> placeTypeDTOList);
	//<T> List<PlaceType> updateAllPlaceTypeBySingleField(T fieldName, T fieldValue);
	//<T> void setFieldValue(PlaceType placeType, String fiedlName, T fieldValue);
	void deletePlaceTypeById(Long id);
	void deleteAllPlaceTypes() throws Exception;
	//<T> void deletePlaceTypeByField(String fieldName, T fieldValue);

}

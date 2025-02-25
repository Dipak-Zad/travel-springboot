package com.travel.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.app.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	//Page<Place> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query("SELECT p FROM Place p WHERE p.placeName = :placeName AND p.placeAddress = :placeAddress")
	Optional<Place> findPlaceByNameAndAddress(@Param("placeName") String placeName, @Param("placeAddress") String placeAddress);
	
	//@Query(value = "SELECT * FROM Place WHERE ?1 LIKE %?2%", nativeQuery = true)
	//<T> List<Place> searchByField(T fieldName, T fieldValue);
	
	//<T> void setFieldValue(Place place, T fieldName, T fieldValue);

}

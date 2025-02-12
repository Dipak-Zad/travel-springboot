package com.travel.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.app.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	Page<Place> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query("SELECT p FROM place p WHERE p.place_name = :place_name AND p.address = :address")
	Optional<Place> findPlaceByNameAndAddress(@Param("place_name") String place_name,@Param("address") String place_address);
	
	@Query(value = "SELECT * FROM places WHERE ?1 LIKE %?2%", nativeQuery = true)
	<T> List<Place> searchByField(T fieldName, T fieldValue);
	
	<T> void setFieldValue(Place place, T fieldName, T fieldValue);

}

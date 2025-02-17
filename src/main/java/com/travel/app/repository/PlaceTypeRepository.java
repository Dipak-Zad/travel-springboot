package com.travel.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.app.model.PlaceType;

@Repository
public interface PlaceTypeRepository extends JpaRepository<PlaceType, Long> {

	@Query("SELECT pt from PlaceType pt WHERE pt.type = :type AND pt.description = :description")
	Optional<PlaceType> findUserByTypeAndDesc(@Param("type") String type, @Param("description") String description);

	@Query(value = "SELECT * FROM PlaceType WHERE ?1 LIKE %?2%", nativeQuery = true)
	<T> List<PlaceType> searchByField(T fieldName, T fieldValue);
	
}

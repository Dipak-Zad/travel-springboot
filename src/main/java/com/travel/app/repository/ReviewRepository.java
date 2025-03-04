package com.travel.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.app.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.place.id = :placeId") 
	List<Review> findReviewByUserAndPlace(@Param("userId") Long userId, @Param("placeId") Long placeId);
	
	@Query("SELECT r FROM Review r WHERE r.user.id = :userId") 
	List<Review> findReviewByUser(@Param("userId") Long userId);
	
	@Query("SELECT r FROM Review r WHERE r.place.id = :placeId") 
	List<Review> findReviewByPlace(@Param("placeId") Long placeId);

//	@Query("SELECT r FROM Review r WHERE r.user = :userId AND r.place = :placeId")
//	Optional<Review> findReviewByUserAndPlace(@Param("userId") Long userId, @Param("placeId") Long placeId);
	
//	@Query(value = "SELECT * FROM Review WHERE ?1 LIKE %?2%", nativeQuery = true)
//	<T> List<Review> searchByField(T fieldName, T fieldValue);
	
}

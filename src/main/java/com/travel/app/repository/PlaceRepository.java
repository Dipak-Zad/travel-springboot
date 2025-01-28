package com.travel.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.app.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	Page<Place> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

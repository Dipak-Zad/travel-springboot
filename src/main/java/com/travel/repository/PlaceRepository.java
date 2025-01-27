package com.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

}

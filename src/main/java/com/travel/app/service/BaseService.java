package com.travel.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, ID> {

	List<T> findAll();
	Page<T> findAll(Pageable pageable);
	Optional<T> findById(ID id);
	T save(T entity);
	List<T> saveAll(List<T> entities);
	void deleteById(ID id);
	void deleteAll();
	
}

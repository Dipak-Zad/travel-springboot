package com.travel.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

	protected final JpaRepository<T, ID> repository;
	
	public BaseServiceImpl(JpaRepository<T, ID> repository)
	{
		this.repository = repository;
	}
	
	@Override
	public List<T> findAll()
	{
		return repository.findAll();
	}
	
	@Override
	public Page<T> findAll(Pageable pageable)
	{
		return repository.findAll(pageable);
	}
	
	@Override
	public Optional<T> findById(ID id)
	{
		return repository.findById(id);
	}
	
	@Override
	public T save(T entity)
	{
		return repository.save(entity);
	}
	
	@Override
	public List<T> saveAll(List<T> entities)
	{
		return repository.saveAll(entities);
	}
	
	@Override
	public void deleteById(ID id)
	{
		repository.deleteById(id);
	}
	
	@Override
	public void deleteAll()
	{
		repository.deleteAll();
	}
	
}

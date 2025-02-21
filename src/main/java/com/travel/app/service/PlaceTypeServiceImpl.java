package com.travel.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.travel.app.dto.PlaceTypeDTO;
import com.travel.app.exception.DuplicateEntityException;
import com.travel.app.exception.SaveEntityException;
import com.travel.app.model.PlaceType;
import com.travel.app.repository.PlaceTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PlaceTypeServiceImpl<T> implements PlaceTypeService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PlaceTypeRepository PlaceTypeRepo;
	
	@Override
	public PlaceType saveSinglePlaceType(PlaceTypeDTO placeTypeDTO)
	{
		try
		{
			String pType = placeTypeDTO.getType();
			String pDesc = placeTypeDTO.getDescription();
			Optional<PlaceType> pTypeCheck = PlaceTypeRepo.findUserByTypeAndDesc(pType, pDesc);
 			PlaceType plcType = new PlaceType();
 			
			if(pTypeCheck.isPresent())
			{
				throw new DuplicateEntityException("PlaceType with '"+placeTypeDTO.getType()+"' already exists.");
			}
				
			plcType = modelMapper.map(placeTypeDTO, PlaceType.class);
			plcType.setCreatedDate(LocalDateTime.now());
			plcType.setCreatedBy("current session user");
			plcType.setModifiedDate(LocalDateTime.now());
			plcType.setModifiedBy("current session user");
			plcType = PlaceTypeRepo.save(plcType);
				
			if(plcType == null)
			{
				throw new SaveEntityException("Failed to save placeType");
					
			}
			return plcType;
			
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save placeType");
			//e.printStackTrace();
		}
		
	}
	
	@Override
	public List<PlaceType> saveAllPlaceType(List<PlaceTypeDTO> placeTypeDTOList)
	{
		try
		{
			List<PlaceType> placeTypes = new ArrayList<PlaceType>();
			Optional<PlaceType> OptPlaceType = null;
			String  pType,pDesc;
			for(PlaceTypeDTO plcTypeDTO : placeTypeDTOList)
			{
				pType = plcTypeDTO.getType();
				pDesc = plcTypeDTO.getDescription();
				OptPlaceType = PlaceTypeRepo.findUserByTypeAndDesc(pType, pDesc);
				
				if(OptPlaceType.isPresent())
				{
					throw new DuplicateEntityException("PlaceType with '"+pType+"' already exists.");
				}
				
				PlaceType tempPlcType = modelMapper.map(plcTypeDTO, PlaceType.class);
				tempPlcType.setCreatedDate(LocalDateTime.now());
				tempPlcType.setCreatedBy("current session user");
				tempPlcType.setModifiedDate(LocalDateTime.now());
				tempPlcType.setModifiedBy("current session user");
				placeTypes.add(tempPlcType);
				
			}

			placeTypes = PlaceTypeRepo.saveAll(placeTypes);
			
			if(placeTypes != null)
			{
				throw new SaveEntityException("Failed to save given placeTypes");
			}

			return placeTypes; 
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save given placeTypes");
			
		}
		
	}
	
	@Override
	public Optional<PlaceType> findPlaceTypeById(Long id)
	{
		try
		{
			System.out.println("asd"+id);
			Optional<PlaceType> plc = PlaceTypeRepo.findById(id);
			System.out.println("asd"+plc);
			if(plc.isPresent())
			{
				return plc;
			}
			else
			{
				throw new EntityNotFoundException("PlaceType with ID '"+id+"' not found");
			}
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("PlaceType with ID '"+id+"' not found");
		}
	}
	

//	@Override
//	public <T> List<PlaceType> findPlaceTypeByField(String fieldName,T fieldValue)
//	{
//		try
//		{
//			List<PlaceType> placeTypes = PlaceTypeRepo.searchByField(fieldName, fieldValue);
//			if(placeTypes!=null)
//			{
//				return placeTypes;
//			}
//			else
//			{
//				throw new EntityNotFoundException("PlaceType with '"+fieldName+"' '"+fieldValue+"' not found");
//			}
//			
//		}
//		catch(Exception e)
//		{
//			throw new EntityNotFoundException("PlaceType with '"+fieldName+"' '"+fieldValue+"' not found");
//		}
//	}
	
//	@Override
//	public Optional<PlaceType> findPlaceTypeByNameAndLocation(String pName, String pLocation)
//	{
//		try{
//			Optional<PlaceType> plc = PlaceTypeRepo.findPlaceTypeByNameAndAddress(pName, pLocation);
//			return plc == null ? null : plc;
//		}
//		catch(Exception e)
//		{
//			throw new EntityNotFoundException("PlaceType with name '"+pLocation+"' not found");
//		}
//	}
	
	@Override
	public List<PlaceType> findAllPlaceTypes()
	{
		try
		{
			List<PlaceType> plcTypes = PlaceTypeRepo.findAll();
			return plcTypes == null ? null : plcTypes;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all placeTypes");
		}
	}
	
	@Override
	public <T> Page<PlaceType> findAllPlaceTypeInPages(int page, int size, T sortBy, String sortDir)
	{
		try
		{
			Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(String.valueOf(sortBy)).ascending() : Sort.by(String.valueOf(sortBy)).descending();
			Pageable pageable = PageRequest.of(page, size, sort);
			return PlaceTypeRepo.findAll(pageable);
					
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all placeTypes");
		}
	}
	
	@Override
	public PlaceType updatePlaceType(Long id, PlaceTypeDTO placeTypeDTO)
	{
		try
		{
			PlaceType placeType = PlaceTypeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No placeType found with ID "+id));
			
			if(placeTypeDTO.getType()!=null && !placeTypeDTO.getType().trim().isEmpty())
			{
				placeType.setType(placeTypeDTO.getType());
			}
			
			if(placeTypeDTO.getDescription()!=null && !placeTypeDTO.getDescription().trim().isEmpty())
			{
				placeType.setDescription(placeTypeDTO.getDescription());
			}
			
			if(placeTypeDTO.getStatus()!=null)
			{
				placeType.setStatus(placeTypeDTO.getStatus());
			}
			
			placeType.setModifiedDate(LocalDateTime.now());
			placeType.setModifiedBy("new current session user");
			
			return PlaceTypeRepo.save(placeType);
			
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update placeType");
		}
	}
	
	@Override
	public List<PlaceType> updateAllPlaceTypes(List<Long> idList, List<PlaceTypeDTO> placeTypesDTO)
	{
		try
		{
			if(idList.size() != placeTypesDTO.size())
			{
				throw new IllegalArgumentException("ID list & DTO list must have the same size");
			}
			
			List<PlaceType> placeTypes = new ArrayList<>();
			
			for(int i=0; i<idList.size();i++)
			{
				final Long placeTypeId = idList.get(i);
				PlaceType placeType = PlaceTypeRepo.findById(placeTypeId).orElseThrow(() -> new EntityNotFoundException("No placeType found with ID "+placeTypeId));
				PlaceTypeDTO placeTypeDTO = placeTypesDTO.get(i);
				
				if(placeTypeDTO.getType()!=null && !placeTypeDTO.getType().trim().isEmpty())
				{
					placeType.setType(placeTypeDTO.getType());
				}
				
				if(placeTypeDTO.getDescription()!=null && !placeTypeDTO.getDescription().trim().isEmpty())
				{
					placeType.setDescription(placeTypeDTO.getDescription());
				}
				
				if(placeTypeDTO.getStatus()!=null)
				{
					placeType.setStatus(placeTypeDTO.getStatus());
				}
				
				placeType.setModifiedDate(LocalDateTime.now());
				placeType.setModifiedBy("new current session user");
				
				placeTypes.add(PlaceTypeRepo.save(placeType));		
			}
			
			return placeTypes;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update placeType");
		}
	}
	
//	@Override
//	public <T> List<PlaceType> updateAllPlaceTypeByFields(List<T> placeTypeFieldList, List<T> placeTypeValueList, List<PlaceTypeDTO> updatePlaceTypesDTO)
//	{
//		if(placeTypeFieldList.size() != placeTypeValueList.size() || placeTypeValueList.size() != updatePlaceTypesDTO.size())
//		{
//			throw new IllegalArgumentException("ID list & DTO list must have the same size");
//		}
//		try
//		{
//			List<PlaceType> updatedPlaceTypes = new ArrayList<>();
//			
//			for(int i=0;i<placeTypeFieldList.size();i++)
//			{
//				T fieldName = placeTypeFieldList.get(i);
//				T fieldValue = placeTypeValueList.get(i);
//				PlaceTypeDTO placeTypeDTO = updatePlaceTypesDTO.get(i);
//				updatedPlaceTypes = findPlaceTypeByField(fieldName,fieldValue);
//				modelMapper.map(placeTypeDTO, updatedPlaceTypes.get(i));
//				updatedPlaceTypes.add(PlaceTypeRepo.save(updatedPlaceTypes.get(i)));
//				
//				throw new EntityNotFoundException("No placeType found with "+fieldName+" : '"+fieldValue+"'");
//		
//			}
//			
//			return updatedPlaceTypes;
//		}
//		catch(Exception e)
//		{
//			throw new SaveEntityException("Failed to update given placeTypes" +e.getMessage());
//		}
//	}

//	@Override
//	public <T> List<PlaceType> updateAllPlaceTypeBySingleField(T fieldName, T fieldValue)
//	{
//		
//		try
//		{
//			List<PlaceType> placeTypes = PlaceTypeRepo.findAll();
//			for(PlaceType plc : placeTypes)
//			{
//				setFieldValue(plc, fieldName, fieldValue);
//				PlaceTypeRepo.save(plc);
//			}
//			return placeTypes;
//		}
//		catch(Exception e)
//		{
//			throw new SaveEntityException("Failed to update all placeType's "+fieldName+" with '"+fieldValue+"'");
//		}
//	}
	
//	@Override
//	public <T> void setFieldValue(PlaceType placeType, T fieldName, T fieldValue)
//	{
//		try
//		{
//			Field field = PlaceType.class.getDeclaredField(fieldName.toString());
//			field.setAccessible(true);
//			field.set(placeType, fieldValue);
//		}
//		catch(NoSuchFieldException | IllegalAccessException e)
//		{
//			throw new IllegalArgumentException("Invalid field: "+fieldName);
//		}
//	}
 
	@Override
	public void deletePlaceTypeById(Long id) {
		try
		{
			PlaceTypeRepo.deleteById(id);
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to All placeTypes");
		}
		
	}

	@Override
	public void deleteAllPlaceTypes() throws Exception {
		try
		{
			PlaceTypeRepo.deleteAll();
		}
		catch(Exception e)
		{
			throw new Exception("Failed to delete all the placeTypes");
		}
		
	}

//	@Override
//	public <T> void deletePlaceTypeByField(String fieldName, T fieldValue)
//	{
//		try
//		{
//			List<PlaceType> placeTypes = findPlaceTypeByField(fieldName, fieldValue);
//			PlaceTypeRepo.deleteAll(placeTypes);
//		}
//		catch(Exception e)
//		{
//			throw new IllegalArgumentException("Failed to delete placeType by "+fieldName+" : '"+fieldValue+"'");
//		}
//		
//	}
	
	
	
//	private PlaceType DtoToPlaceType(PlaceTypeDTO placeTypeDTO) {
//		PlaceType placeType = modelMapper.map(placeTypeDTO, PlaceType.class);
//		return placeType;
//	}
//	
//	private PlaceTypeDTO CategoryToDto(PlaceType placeType) {
//		PlaceTypeDTO placeTypeDTO = modelMapper.map(placeType, PlaceTypeDTO.class);
//		return placeTypeDTO;
//	}
	

}

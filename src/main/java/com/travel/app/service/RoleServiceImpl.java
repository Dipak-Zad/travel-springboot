package com.travel.app.service;

import java.lang.reflect.Field;
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

import com.travel.app.dto.RoleDTO;
import com.travel.app.exception.DuplicateEntityException;
import com.travel.app.exception.SaveEntityException;
import com.travel.app.model.Role;
import com.travel.app.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl<T> implements RoleService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepository RoleRepo;
	
	@Override
	public Role saveSingleRole(RoleDTO roleDTO)
	{
		try
		{
			T rValue = (T) roleDTO.getRole();
			T rField = (T) "role";
			List<Role> rCheck = RoleRepo.searchByField(rField, rValue);
 			Role rl = new Role();
			if(rCheck == null)
			{
				rl = modelMapper.map(roleDTO, Role.class);
				rl = RoleRepo.save(rl);
				if(rl != null)
				{
					//return rl == null ? null : rl;
					return rl;
				}
				else
				{
					throw new SaveEntityException("Failed to save role");	
				}
			}
			else
			{
				throw new DuplicateEntityException(rValue+" role already exists.");	
			}
			
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save role");
			//e.printStackTrace();
		}
		
	}
	
	@Override
	public List<Role> saveAllRole(List<RoleDTO> rolesDTO)
	{
		try
		{
			List<Role> roles = new ArrayList<Role>();
			List<Role> OptRole = null;
			T rField,rValue;
			for(RoleDTO rlDTO : rolesDTO)
			{
				rValue = (T) rlDTO.getRole();
				rField = (T) "role";
				OptRole = RoleRepo.searchByField(rField, rValue);
				if(OptRole==null)
				{
					Role tempPlc = modelMapper.map(rlDTO, Role.class);
					roles.add(tempPlc);
				}
				else
				{
					throw new DuplicateEntityException(rValue+" role already exists.");
				}
				
			}

			roles = RoleRepo.saveAll(roles);
			if(roles != null)
			{
				return roles;
			}
			else
			{
				throw new SaveEntityException("Failed to save given roles");
			}
			//return roles == null ? null : roles; 
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save given roles");
			//e.printStackTrace();
		}
		
		//return null;
	}
	
	@Override
	public Optional<Role> findRoleById(Long id)
	{
		try
		{
			Optional<Role> rl = RoleRepo.findById(id);
			if(rl!=null)
			{
				return rl;
			}
			else
			{
				throw new EntityNotFoundException("Role with '"+id+"' not found");
			}
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Role with '"+id+"' not found");
		}
	}
	

	@Override
	public <T> List<Role> searchByField(T fieldName,T fieldValue)
	{
		try
		{
			List<Role> roles = RoleRepo.searchByField(fieldName, fieldValue);
			if(roles!=null)
			{
				return roles;
			}
			else
			{
				throw new EntityNotFoundException("Role with '"+fieldName+"' '"+fieldValue+"' not found");
			}
			
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Role with '"+fieldName+"' '"+fieldValue+"' not found");
		}
	}
	
//	@Override
//	public Optional<Role> findRoleByNameAndLocation(String pName, String pLocation)
//	{
//		try{
//			Optional<Role> rl = RoleRepo.findRoleByNameAndAddress(pName, pLocation);
//			return rl == null ? null : rl;
//		}
//		catch(Exception e)
//		{
//			throw new EntityNotFoundException("Role with name '"+pLocation+"' not found");
//		}
//	}
	
	@Override
	public List<Role> findAllRoles()
	{
		try
		{
			List<Role> rls = RoleRepo.findAll();
			return rls == null ? null : rls;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all roles");
		}
	}
	
	@Override
	public <T> Page<Role> findAllRoleInPages(int page, int size, T sortBy, String sortDir)
	{
		try
		{
			Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(String.valueOf(sortBy)).ascending() : Sort.by(String.valueOf(sortBy)).descending();
			Pageable pageable = PageRequest.of(page, size, sort);
			return RoleRepo.findAll(pageable);
					
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all roles");
		}
	}
	
	@Override
	public Role updateRole(Long id, RoleDTO roleDTO)
	{
		try
		{
			Role rl = RoleRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No role found with ID "+id));
			if(rl==null)
			{
				rl = modelMapper.map(roleDTO, Role.class);
				return RoleRepo.save(rl);
			}
			else
			{
				throw new SaveEntityException("Failed to update role");
			}
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update role");
		}
	}
	
	@Override
	public List<Role> updateAllRoles(List<Long> idList, List<RoleDTO> rolesDTO)
	{
		try
		{
			if(idList.size() != rolesDTO.size())
			{
				throw new IllegalArgumentException("ID list & DTO list must have the same size");
			}
			List<Role> roles = new ArrayList<>();
			for(int i=0; i<idList.size();i++)
			{
				final Long roleId = idList.get(i);
				Role role = RoleRepo.findById(roleId).orElseThrow(() -> new EntityNotFoundException("No role found with ID "+roleId));
				modelMapper.map(rolesDTO.get(i), role);
				roles.add(RoleRepo.save(role));		
			}
			return roles;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update role");
		}
	}
	
	@Override
	public <T> List<Role> updateAllRoleByFields(List<T> roleFieldList, List<T> roleValueList, List<RoleDTO> updateRolesDTO)
	{
		if(roleFieldList.size() != roleValueList.size() || roleValueList.size() != updateRolesDTO.size())
		{
			throw new IllegalArgumentException("ID list & DTO list must have the same size");
		}
		try
		{
			List<Role> updatedRoles = new ArrayList<>();
			
			for(int i=0;i<roleFieldList.size();i++)
			{
				T fieldName = roleFieldList.get(i);
				T fieldValue = roleValueList.get(i);
				RoleDTO roleDTO = updateRolesDTO.get(i);
				updatedRoles = searchByField(fieldName,fieldValue);
				modelMapper.map(roleDTO, updatedRoles.get(i));
				updatedRoles.add(RoleRepo.save(updatedRoles.get(i)));
				
				throw new EntityNotFoundException("No role found with "+fieldName+" : '"+fieldValue+"'");
		
			}
			
			return updatedRoles;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update given roles" +e.getMessage());
		}
	}
	
	@Override
	public <T> List<Role> updateAllRoleBySingleField(T fieldName, T fieldValue)
	{
		
		try
		{
			List<Role> roles = RoleRepo.findAll();
			for(Role rl : roles)
			{
				setFieldValue(rl, fieldName, fieldValue);
				RoleRepo.save(rl);
			}
			return roles;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update all role's "+fieldName+" with '"+fieldValue+"'");
		}
	}
	
	@Override
	public <T> void setFieldValue(Role role, T fieldName, T fieldValue)
	{
		try
		{
			Field field = Role.class.getDeclaredField(fieldName.toString());
			field.setAccessible(true);
			field.set(role, fieldValue);
		}
		catch(NoSuchFieldException | IllegalAccessException e)
		{
			throw new IllegalArgumentException("Invalid field: "+fieldName);
		}
	}
 
	@Override
	public void deleteRoleById(Long id) {
		try
		{
			RoleRepo.deleteById(id);
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to All roles");
		}
		
	}

	@Override
	public void deleteAllRoles() throws Exception {
		try
		{
			RoleRepo.deleteAll();
		}
		catch(Exception e)
		{
			throw new Exception("Failed to delete all the roles");
		}
		
	}

	@Override
	public <T> void deleteRoleByField(T fieldName, T fieldValue)
	{
		try
		{
			List<Role> roles = searchByField(fieldName, fieldValue);
			RoleRepo.deleteAll(roles);
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Failed to delete role by "+fieldName+" : '"+fieldValue+"'");
		}
		
	}
	
	
	
//	private Role DtoToRole(RoleDTO roleDTO) {
//		Role role = modelMapper.map(roleDTO, Role.class);
//		return role;
//	}
//	
//	private RoleDTO CategoryToDto(Role role) {
//		RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
//		return roleDTO;
//	}
	

}

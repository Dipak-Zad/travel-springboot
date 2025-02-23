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
			String role = roleDTO.getRole();
			Optional<Role> roleCheck = RoleRepo.findRoleByRole(role);
 			Role newRole = new Role();
 			
			if(roleCheck.isPresent())
			{
				throw new DuplicateEntityException(role+" role already exists.");	
			}
			
			modelMapper.map(roleDTO, newRole);
			newRole = RoleRepo.save(newRole);
			
			if(role == null)
			{
				throw new SaveEntityException("Failed to save role");
			}

			return newRole;
		
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save role");
		}
		
	}
	
	@Override
	public List<Role> saveAllRole(List<RoleDTO> rolesDTO)
	{
		try
		{
			List<Role> roles = new ArrayList<Role>();
			Optional<Role> OptRole = null;
			String role;
			for(RoleDTO roleDTO : rolesDTO)
			{
				role = roleDTO.getRole();
				OptRole = RoleRepo.findRoleByRole(role);
				if(OptRole.isPresent())
				{
					throw new DuplicateEntityException(role+" role already exists.");
				}
				
				Role tempPlc = modelMapper.map(roleDTO, Role.class);
				roles.add(tempPlc);
			}

			roles = RoleRepo.saveAll(roles);
			
			if(roles == null)
			{
				throw new SaveEntityException("Failed to save given roles");
			}

			return roles;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save given roles");
		}
		
	}
	
	@Override
	public Optional<Role> findRoleById(Long id)
	{
		try
		{
			Optional<Role> role = RoleRepo.findById(id);
			if(role!=null)
			{
				throw new EntityNotFoundException("Role with '"+id+"' not found");
			}
			
			return role;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Role with '"+id+"' not found");
		}
	}
	

//	@Override
//	public <T> List<Role> searchByField(T fieldName,T fieldValue)
//	{
//		try
//		{
//			List<Role> roles = RoleRepo.searchByField(fieldName, fieldValue);
//			if(roles==null)
//			{
//				throw new EntityNotFoundException("Role with '"+fieldName+"' '"+fieldValue+"' not found");
//			}
//			
//			return roles;
//		}
//		catch(Exception e)
//		{
//			throw new EntityNotFoundException("Role with '"+fieldName+"' '"+fieldValue+"' not found");
//		}
//	}
	
	
	
	@Override
	public Optional<Role> findRoleByRole(String role)
	{
		try{
			Optional<Role> roleCheck = RoleRepo.findRoleByRole(role);
			return roleCheck == null ? null : roleCheck;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Role with name '"+role+"' not found");
		}
	}
	
	
	@Override
	public List<Role> findAllRoles()
	{
		try
		{
			List<Role> roles = RoleRepo.findAll();
			return roles == null ? null : roles;
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
			Role role = RoleRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No role found with ID "+id));
			
			if(roleDTO.getRole()!=null && !roleDTO.getRole().trim().isEmpty())
			{
				role.setRole(roleDTO.getRole());
			}
			
			if(roleDTO.getStatus()!=null)
			{
				role.setStatus(roleDTO.getStatus());
			}
			
			role.setModifiedDate(LocalDateTime.now());
			role.setModifiedBy("new current session user");
			
			return RoleRepo.save(role);
			
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
				RoleDTO roleDTO = rolesDTO.get(i);
				
				if(roleDTO.getRole()!=null && !roleDTO.getRole().trim().isEmpty())
				{
					role.setRole(roleDTO.getRole());
				}
				
				if(roleDTO.getStatus()!=null)
				{
					role.setStatus(roleDTO.getStatus());
				}
				
				role.setModifiedDate(LocalDateTime.now());
				role.setModifiedBy("new current session user");
				
				roles.add(RoleRepo.save(role));		
			}
			
			return roles;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update role");
		}
	}
	
	
//	@Override
//	public <T> List<Role> updateAllRoleByFields(List<T> roleFieldList, List<T> roleValueList, List<RoleDTO> updateRolesDTO)
//	{
//		if(roleFieldList.size() != roleValueList.size() || roleValueList.size() != updateRolesDTO.size())
//		{
//			throw new IllegalArgumentException("ID list & DTO list must have the same size");
//		}
//		try
//		{
//			List<Role> updatedRoles = new ArrayList<>();
//			
//			for(int i=0;i<roleFieldList.size();i++)
//			{
//				T fieldName = roleFieldList.get(i);
//				T fieldValue = roleValueList.get(i);
//				RoleDTO roleDTO = updateRolesDTO.get(i);
//				updatedRoles = searchByField(fieldName,fieldValue);
//				modelMapper.map(roleDTO, updatedRoles.get(i));
//				updatedRoles.add(RoleRepo.save(updatedRoles.get(i)));
//				
//				throw new EntityNotFoundException("No role found with "+fieldName+" : '"+fieldValue+"'");
//		
//			}
//			
//			return updatedRoles;
//		}
//		catch(Exception e)
//		{
//			throw new SaveEntityException("Failed to update given roles" +e.getMessage());
//		}
//	}
	
//	@Override
//	public <T> List<Role> updateAllRoleBySingleField(T fieldName, T fieldValue)
//	{
//		
//		try
//		{
//			List<Role> roles = RoleRepo.findAll();
//			for(Role role : roles)
//			{
//				setFieldValue(role, fieldName, fieldValue);
//				RoleRepo.save(role);
//			}
//			return roles;
//		}
//		catch(Exception e)
//		{
//			throw new SaveEntityException("Failed to update all role's "+fieldName+" with '"+fieldValue+"'");
//		}
//	}
	
//	@Override
//	public <T> void setFieldValue(Role role, T fieldName, T fieldValue)
//	{
//		try
//		{
//			Field field = Role.class.getDeclaredField(fieldName.toString());
//			field.setAccessible(true);
//			field.set(role, fieldValue);
//		}
//		catch(NoSuchFieldException | IllegalAccessException e)
//		{
//			throw new IllegalArgumentException("Invalid field: "+fieldName);
//		}
//	}
 
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

//	@Override
//	public <T> void deleteRoleByField(T fieldName, T fieldValue)
//	{
//		try
//		{
//			List<Role> roles = searchByField(fieldName, fieldValue);
//			RoleRepo.deleteAll(roles);
//		}
//		catch(Exception e)
//		{
//			throw new IllegalArgumentException("Failed to delete role by "+fieldName+" : '"+fieldValue+"'");
//		}
//		
//	}
	
	
	
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

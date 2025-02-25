package com.travel.app.service;

import java.lang.reflect.Field;
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
import com.travel.app.model.PlaceType;
import com.travel.app.model.Role;
import com.travel.app.repository.RoleRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl<T> implements RoleService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EntityManager entityManager;
	
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
			newRole.setCreatedDate(LocalDateTime.now());
			newRole.setCreatedBy("current session user");
			newRole.setModifiedDate(LocalDateTime.now());
			newRole.setModifiedBy("current session user");
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
				
				Role rl = modelMapper.map(roleDTO, Role.class);
				rl.setCreatedDate(LocalDateTime.now());
				rl.setCreatedBy("current session user");
				rl.setModifiedDate(LocalDateTime.now());
				rl.setModifiedBy("current session user");
				roles.add(rl);
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
			if(role.isPresent())
			{
				return role;
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
	public <T> List<Role> searchByField(String fieldName,T fieldValue)
	{
		try
		{
			String query = "SELECT * FROM role WHERE " + fieldName + " = ?";
			 
			 return entityManager.createNativeQuery(query, Role.class)
                    .setParameter(1, fieldValue)
                    .getResultList();
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Role with '"+fieldName+"' '"+fieldValue+"' not found");
		}
	}
	
	
	
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
	public <T> Page<Role> findAllRoleInPages(int page, int size, String sortBy, String sortDir)
	{
		try
		{
			Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
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
	public List<Role> updateAllRoles(List<RoleDTO> rolesDTO)
	{
		try
		{
			
			List<Role> roles = new ArrayList<>();
			
			for(int i=0; i<rolesDTO.size();i++)
			{
				RoleDTO roleDTO = rolesDTO.get(i);
				Long roleId = roleDTO.getId();
				Role role = RoleRepo.findById(roleId).orElseThrow(() -> new EntityNotFoundException("No role found with ID "+roleId));
				
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
	
	@Override
	public <T> List<Role> updateAllRoleBySingleField(String fieldName, T fieldValue)
	{
		
		try
		{
			List<Role> roles = RoleRepo.findAll();

			if (roles.isEmpty()) {
	            throw new EntityNotFoundException("No placeTypes found to update");
	        }
			
			for(Role role : roles)
			{
				setFieldValue(role, fieldName, fieldValue);
				RoleRepo.save(role);
			}
			return roles;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update all role's "+fieldName+" with '"+fieldValue+"'");
		}
	}
	
	@Override
	public <T> void setFieldValue(Role role, String fieldName, T fieldValue)
	{
		try
		{
			Field field = Role.class.getDeclaredField(fieldName.toString());
			field.setAccessible(true);
			if (field.getType().isEnum()) {
	            @SuppressWarnings("unchecked")
	            Class<Enum> enumType = (Class<Enum>) field.getType();
	            Enum enumValue = Enum.valueOf(enumType, fieldValue.toString());
	            field.set(role, enumValue);
	        } else {
	            field.set(role, fieldValue);
	        }
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
	public <T> void deleteRoleByField(String fieldName, T fieldValue)
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
	
	

}

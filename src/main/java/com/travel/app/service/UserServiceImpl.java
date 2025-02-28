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

import com.travel.app.dto.UserDTO;
import com.travel.app.exception.DuplicateEntityException;
import com.travel.app.exception.SaveEntityException;
import com.travel.app.model.User;
import com.travel.app.repository.UserRepository;
import com.travel.app.model.Role;
import com.travel.app.repository.RoleRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Override
	public User saveSingleUser(UserDTO userDTO) {
		try
		{
			String userName = userDTO.getUserName();
			String userMail = userDTO.getEmail();
			Optional<User> uCheck = userRepo.findUserByNameAndMail(userName, userMail);
			User user = new User();
			if(uCheck.isPresent())
			{
				throw new DuplicateEntityException("User with name '"+userDTO.getUserName()+"' and mail '"+userDTO.getEmail()+"' already exists.");
			}
			
			user = modelMapper.map(userDTO, User.class);
			user.setCreatedDate(LocalDateTime.now());
			user.setCreatedBy("current session user");
			user.setModifiedDate(LocalDateTime.now());
			user.setModifiedBy("current session user");
			user = userRepo.save(user);
			
			if(user == null)
			{
				throw new SaveEntityException("Failed to save user");
			}
			return user;
			
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save user");
		}
		
	}

	
	@Override
	public List<User> saveAllUser(List<UserDTO> usersDTO) {
		try
		{
			List<User> users = new ArrayList<User>();
			Optional<User> OptUser = null;
			String uName,uMail;
			for(UserDTO usrDTO : usersDTO)
			{
				uName = usrDTO.getUserName();
				uMail = usrDTO.getEmail();
				OptUser = userRepo.findUserByNameAndMail(uName, uMail);
				
				if(OptUser.isPresent())
				{
					throw new DuplicateEntityException("User with name '"+usrDTO.getUserName()+
							"'at mail '"+usrDTO.getEmail()+"' already exists.");
				}
				
				User tempPlc = modelMapper.map(usrDTO, User.class);
				tempPlc.setCreatedDate(LocalDateTime.now());
				tempPlc.setCreatedBy("current session user");
				tempPlc.setModifiedDate(LocalDateTime.now());
				tempPlc.setModifiedBy("current session user");
				users.add(tempPlc);
			}

			users = userRepo.saveAll(users);
			
			if(users == null)
			{
				throw new SaveEntityException("Failed to save given users");	
			}
			
			return users; 
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save given users");
		}
	}

	
	@Override
	public Optional<User> findUserById(Long Id) {
		try
		{
			Optional<User> user = userRepo.findById(Id);
			if(user.isPresent())
			{
				return user;
			}
			else
			{
				throw new EntityNotFoundException("User with '"+Id+"' not found");	
			}
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("User with '"+Id+"' not found");
		}
	}

	
	@Override
	public <T> List<User> findUserByField(String fieldName, T fieldValue) {
		try
		{
			String query = "SELECT * FROM users WHERE " + fieldName + " = ?";
			 
			return entityManager.createNativeQuery(query, User.class)
                     .setParameter(1, fieldValue)
                     .getResultList();
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("User with '"+fieldName+"' '"+fieldValue+"' not found");
		}
	}

	
	@Override
	public Optional<User> findUserByNameAndMail(String userName, String userMail) {
		try{
			Optional<User> user = userRepo.findUserByNameAndMail(userName, userMail);
			return user == null ? null : user;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("User with username '"+userName+"' not found");
		}
	}
	
	
	@Override
	public Optional<User> findUserByRole(Long userRoleId) 
	{
		String Role = "";
		try
		{
			Role role = roleRepo.findById(userRoleId).orElseThrow(() -> new EntityNotFoundException("No user role found with ID "+userRoleId));
			Role = role.getRole();
			Optional<User> user = userRepo.findUserByRole(userRoleId);
			return user == null ? null : user;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("User with role '"+Role+"' not found");
		}
	}
	

	@Override
	public List<User> findAllUsers() {
		try
		{
			List<User> users = userRepo.findAll();
			return users == null ? null : users;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all users");
		}
	}
	

	@Override
	public <T> Page<User> findAllUserInPages(int pageNumber, int pageSize, String sortByField, String sortDirection) {
		try
		{
			Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortByField).ascending() : Sort.by(sortByField).descending();
			Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
			return userRepo.findAll(pageable);
					
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all users");
		}
	}
	

	@Override
	public User updateUser(Long id, UserDTO userDTO) {
		try
		{
			User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No user found with ID "+id));
			
			if(userDTO.getUserName()!=null && !userDTO.getUserName().trim().isEmpty())
			{
				user.setUserName(userDTO.getUserName());
			}
			
			if(userDTO.getPassword()!=null && !userDTO.getPassword().trim().isEmpty())
			{
				user.setPassword(userDTO.getPassword());
			}
			
			if(userDTO.getDob()!=null)
			{
				user.setDob(userDTO.getDob());
			}
			
			if(userDTO.getEmail()!=null && !userDTO.getEmail().trim().isEmpty())
			{
				user.setEmail(userDTO.getEmail());
			}
			
			if(userDTO.getMobileNo()!=null && (userDTO.getMobileNo() > 999999999))
			{
				user.setMobileNo(userDTO.getMobileNo());
			}
			
			if(userDTO.getHomeAddress()!=null && !userDTO.getHomeAddress().trim().isEmpty())
			{
				user.setHomeAddress(userDTO.getHomeAddress());
			}
			
			if(userDTO.getStatus()!=null)
			{
				user.setStatus(userDTO.getStatus());
			}
			
			user.setModifiedDate(LocalDateTime.now());
			user.setModifiedBy("new current session user");
			
			return userRepo.save(user);
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update user");
		}
	}

	@Override
	public List<User> updateAllUsers(List<UserDTO> userDTOList) {
		try
		{
			List<User> users = new ArrayList<>();
			for(int i=0; i<userDTOList.size();i++)
			{
				UserDTO userDTO = userDTOList.get(i);
				Long userId = userDTO.getId();
				User user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("No user found with ID "+userId));
				
				if(userDTO.getUserName()!=null && !userDTO.getUserName().trim().isEmpty())
				{
					user.setUserName(userDTO.getUserName());
				}
				
				if(userDTO.getPassword()!=null && !userDTO.getPassword().trim().isEmpty())
				{
					user.setPassword(userDTO.getPassword());
				}
				
				if(userDTO.getDob()!=null)
				{
					user.setDob(userDTO.getDob());
				}
				
				if(userDTO.getEmail()!=null && !userDTO.getEmail().trim().isEmpty())
				{
					user.setEmail(userDTO.getEmail());
				}
				
				if(userDTO.getMobileNo()!=null && (userDTO.getMobileNo() > 999999999))
				{
					user.setMobileNo(userDTO.getMobileNo());
				}
				
				if(userDTO.getHomeAddress()!=null && !userDTO.getHomeAddress().trim().isEmpty())
				{
					user.setHomeAddress(userDTO.getHomeAddress());
				}
				
				if(userDTO.getStatus()!=null)
				{
					user.setStatus(userDTO.getStatus());
				}
				
				user.setModifiedDate(LocalDateTime.now());
				user.setModifiedBy("new current session user");
				
				users.add(userRepo.save(user));		
			}
			
			return users;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update user");
		}
	}
	

	@Override
	public <T> List<User> updateAllUserBySingleField(String fieldName, T fieldValue) {
		try
		{
			List<User> users = userRepo.findAll();
			
			if (users.isEmpty()) {
	            throw new EntityNotFoundException("No user found to update");
	        }
			
			for(User user : users)
			{
				setFieldValue(user, fieldName, fieldValue);
				userRepo.save(user);
			}
			return users;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update all user's "+fieldName+" with '"+fieldValue+"'");
		}
	}

	
	@Override
	public <T> void setFieldValue(User user, String fieldName, T fieldValue) {
		try
		{
			Field field = User.class.getDeclaredField(fieldName.toString());
			field.setAccessible(true);
			if (field.getType().isEnum()) {
	            @SuppressWarnings("unchecked")
	            Class<Enum> enumType = (Class<Enum>) field.getType();
	            Enum enumValue = Enum.valueOf(enumType, fieldValue.toString());
	            field.set(user, enumValue);
	        } else {
	            field.set(user, fieldValue);
	        }
		}
		catch(NoSuchFieldException | IllegalAccessException e)
		{
			throw new IllegalArgumentException("Invalid field: "+fieldName);
		}
		
	}

	
	@Override
	public void deleteUserById(Long id) {
		try
		{
			userRepo.deleteById(id);
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to All users");
		}
	}

	
	@Override
	public void deleteAllUsers() throws Exception {
		try
		{
			userRepo.deleteAll();
		}
		catch(Exception e)
		{
			throw new Exception("Failed to delete all the users");
		}
	}
	

	@Override
	public <T> void deleteUserByField(String fieldName, T fieldValue) {
		try
		{
			List<User> users = findUserByField(fieldName, fieldValue);
			userRepo.deleteAll(users);
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Failed to delete user by "+fieldName+" : '"+fieldValue+"'");
		}
		
	}

}

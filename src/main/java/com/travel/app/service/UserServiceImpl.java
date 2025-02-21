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
import com.travel.app.dto.UserDTO;
import com.travel.app.exception.DuplicateEntityException;
import com.travel.app.exception.SaveEntityException;
import com.travel.app.model.User;
import com.travel.app.model.User;
import com.travel.app.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UserRepository userRepo;
	
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
			user = userRepo.save(user);
			
			if(user == null)
			{
				throw new SaveEntityException("Failed to save user");
			}
			return user;
			
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save place");
		}
		
	}

	
	@Override
	public List<User> saveAllUSer(List<UserDTO> usersDTO) {
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
			if(user==null)
			{
				throw new EntityNotFoundException("User with '"+Id+"' not found");	
			}
			
			return user;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("User with '"+Id+"' not found");
		}
	}

	
//	@Override
//	public <T> List<User> findUserByField(T fieldName, T fieldValue) {
//		try
//		{
//			List<User> users = userRepo.searchByField(fieldName, fieldValue);
//			if(users==null)
//			{
//				throw new EntityNotFoundException("User with '"+fieldName+"' '"+fieldValue+"' not found");
//			}
//				
//			return users;
//		}
//		catch(Exception e)
//		{
//			throw new EntityNotFoundException("User with '"+fieldName+"' '"+fieldValue+"' not found");
//		}
//	}

	
	@Override
	public Optional<User> findUserByNameAndMail(String userName, String userRole) {
		try{
			Optional<User> user = userRepo.findUserByNameAndMail(userName, userRole);
			return user == null ? null : user;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("User with username '"+userName+"' not found");
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
	public <T> Page<User> findAllUserInPages(int pageNumber, int pageSize, T sortByField, String sortDirection) {
		try
		{
			Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(String.valueOf(sortByField)).ascending() : Sort.by(String.valueOf(sortByField)).descending();
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
	public List<User> updateAllUsers(List<Long> idList, List<UserDTO> userDTOList) {
		try
		{
			if(idList.size() != userDTOList.size())
			{
				throw new IllegalArgumentException("ID list & DTO list must have the same size");
			}
			List<User> users = new ArrayList<>();
			for(int i=0; i<idList.size();i++)
			{
				final Long userId = idList.get(i);
				User user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("No user found with ID "+userId));
				UserDTO userDTO = userDTOList.get(i);
				
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

	
//	@Override
//	public <T> List<User> updateAllUserByFields(List<T> userFieldList, List<T> userValueList, List<UserDTO> userDTOList) {
//		if(userFieldList.size() != userValueList.size() || userValueList.size() != userDTOList.size())
//		{
//			throw new IllegalArgumentException("ID list & DTO list must have the same size");
//		}
//		try
//		{
//			List<User> updatedUsers = new ArrayList<>();
//			
//			for(int i=0;i<userFieldList.size();i++)
//			{
//				T fieldName = userFieldList.get(i);
//				T fieldValue = userValueList.get(i);
//				UserDTO userDTO = userDTOList.get(i);
//				updatedUsers = findUserByField(fieldName,fieldValue);
//				modelMapper.map(userDTO, updatedUsers.get(i));
//				updatedUsers.add(userRepo.save(updatedUsers.get(i)));
//				
//				throw new EntityNotFoundException("No user found with "+fieldName+" : '"+fieldValue+"'");
//		
//			}
//			
//			return updatedUsers;
//		}
//		catch(Exception e)
//		{
//			throw new SaveEntityException("Failed to update given users" +e.getMessage());
//		}
//	}
	

//	@Override
//	public <T> List<User> updateAllUserBySingleField(T fieldName, T fieldValue) {
//		try
//		{
//			List<User> users = userRepo.findAll();
//			for(User user : users)
//			{
//				setFieldValue(user, fieldName, fieldValue);
//				userRepo.save(user);
//			}
//			return users;
//		}
//		catch(Exception e)
//		{
//			throw new SaveEntityException("Failed to update all user's "+fieldName+" with '"+fieldValue+"'");
//		}
//	}

	
//	@Override
//	public <T> void setFieldValue(User user, T fieldName, T fieldValue) {
//		try
//		{
//			Field field = User.class.getDeclaredField(fieldName.toString());
//			field.setAccessible(true);
//			field.set(user, fieldValue);
//		}
//		catch(NoSuchFieldException | IllegalAccessException e)
//		{
//			throw new IllegalArgumentException("Invalid field: "+fieldName);
//		}
//		
//	}

	
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
	

//	@Override
//	public <T> void deleteUserByField(T fieldName, T fieldValue) {
//		try
//		{
//			List<User> users = findUserByField(fieldName, fieldValue);
//			userRepo.deleteAll(users);
//		}
//		catch(Exception e)
//		{
//			throw new IllegalArgumentException("Failed to delete user by "+fieldName+" : '"+fieldValue+"'");
//		}
//		
//	}

}

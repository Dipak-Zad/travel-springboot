package com.travel.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.travel.app.dto.UserDTO;
import com.travel.app.model.Users;

public interface UserService {

	Users saveSingleUser(UserDTO userDTO);
	List<Users> saveAllUser(List<UserDTO> usersDTO);
	Optional<Users> findUserById(Long Id);
	<T> List<Users> findUserByField(String fieldName, T fieldValue);
	Optional<Users> findUserByNameAndMail(String userName,String userMail);
	Optional<Users> findUserByRole(Long userRole);
	List<Users> findAllUsers();
	<T> Page<Users> findAllUserInPages(int pageNumber, int pageSize, String sortByField, String sortDirection);
	Users updateUser(Long id, UserDTO userDTO);
	List<Users> updateAllUsers(List<UserDTO> userDTOList);
	<T> List<Users> updateAllUserBySingleField(String fieldName, T fieldValue);
	<T> void setFieldValue(Users user, String fieldName, T fieldValue);
	void deleteUserById(Long id);
	void deleteAllUsers() throws Exception;
	<T> void deleteUserByField(String fieldName, T fieldValue);

}

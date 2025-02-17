package com.travel.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.travel.app.dto.UserDTO;
import com.travel.app.model.User;

public interface UserService {

	User saveSingleUser(UserDTO userDTO);
	List<User> saveAllUSer(List<UserDTO> usersDTO);
	Optional<User> findUserById(Long Id);
	<T> List<User> findUserByField(T fieldName, T fieldValue);
	Optional<User> findUserByNameAndMail(String userName,String userMail);
	List<User> findAllUsers();
	<T> Page<User> findAllUserInPages(int pageNumber, int pageSize, T sortByField, String sortDirection);
	User updateUser(Long id, UserDTO userDTO);
	List<User> updateAllUsers(List<Long> idList, List<UserDTO> userDTOList);
	<T> List<User> updateAllUserByFields(List<T> fieldList, List<T>valueList, List<UserDTO> userDTOList);
	<T> List<User> updateAllUserBySingleField(T fieldName, T fieldValue);
	<T> void setFieldValue(User user, T fieldName, T fieldValue);
	void deleteUserById(Long id);
	void deleteAllUsers() throws Exception;
	<T> void deleteUserByField(T fieldName, T fieldValue);

}

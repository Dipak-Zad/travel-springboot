package com.travel.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.travel.app.dto.RoleDTO;
import com.travel.app.model.Role;

public interface RoleService {
	
	Role saveSingleRole(RoleDTO placeDTO);
	List<Role> saveAllRole(List<RoleDTO> placesDTO);
	Optional<Role> findRoleById(Long id);
	<T> List<Role> searchByField(String fieldname,T fieldvalue);
	Optional<Role> findRoleByRole(String role);
	List<Role> findAllRoles();
	<T> Page<Role> findAllRoleInPages(int pageNumber, int pageSize, String sortByField, String sortDirection);
	Role updateRole(Long id, RoleDTO placeDTO);
	List<Role> updateAllRoles(List<RoleDTO> placeDTOList);
	//<T> List<Role> updateAllRoleByFields(List<T> fieldList, List<T> valueList, List<RoleDTO> placesDTO);
	<T> List<Role> updateAllRoleBySingleField(String fieldName, T fieldValue);
	<T> void setFieldValue(Role place, String fiedlName, T fieldValue);
	void deleteRoleById(Long id);
	void deleteAllRoles() throws Exception;
	<T> void deleteRoleByField(String fieldName, T fieldValue);

}

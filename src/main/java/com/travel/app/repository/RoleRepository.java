package com.travel.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.app.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("SELECT r FROM Role r WHERE r.role = :role")
	Optional<Role> findRoleByRole(@Param("role") String role);
	
//	@Query(value = "SELECT * FROM Role WHERE ?1 LIKE %?2%", nativeQuery = true)
//	<T> List<Role> searchByField(T fieldName, T fieldValue);
	
}

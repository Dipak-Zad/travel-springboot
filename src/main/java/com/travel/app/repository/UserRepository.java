package com.travel.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.app.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	@Query("SELECT u from Users u WHERE u.userName = :userName AND u.email = :userMail")
	Optional<Users> findUserByNameAndMail(@Param("userName") String userName, @Param("userMail") String userMail);
	
//	@Query("SELECT u from User u WHERE u.userName = :userName")
//	Optional<User> findUserByName(@Param("userName") String userName);
	
	@Query("SELECT u from Users u WHERE u.role.id = :userRoleId")
	Optional<Users> findUserByRole(@Param("userRoleId") Long userRoleId);

	Optional<Users> findByEmail(String userEmail);

//	@Query(value = "SELECT * FROM Users WHERE ?1 LIKE %?2%", nativeQuery = true)
//	<T> List<User> searchByField(T fieldName, T fieldValue);
}

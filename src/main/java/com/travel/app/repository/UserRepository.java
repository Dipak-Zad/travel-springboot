
package com.travel.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u from Users u WHERE u.userName = :userName AND u.userMail = :userMail")
	Optional<User> findUserByNameAndMail(@Param("userName") String userName, @Param("userMail") String userMail);

	@Query(value = "SELECT * FROM Users WHERE ?1 LIKE %?2%", nativeQuery = true)
	<T> List<User> searchByField(T fieldName, T fieldValue);
}

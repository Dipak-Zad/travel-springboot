package com.travel.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.app.model.PlaceType;

@Repository
public interface PlaceTypeRepository extends JpaRepository<PlaceType, Long> {

	@Query("SELECT pt from PlaceType pt WHERE pt.type = :type AND pt.description = :description")
	Optional<PlaceType> findUserByTypeAndDesc(@Param("type") String type, @Param("description") String description);

//	@Query(value = "SELECT * FROM PlaceType WHERE ?1 LIKE %?2%", nativeQuery = true)
//	<T> List<PlaceType> searchByField(T fieldName, T fieldValue);
	
//	@Query(value = "SELECT * FROM place_type WHERE :fieldName = :fieldValue", nativeQuery = true)
//	List<PlaceType> searchByField(@Param("fieldName") String fieldName, @Param("fieldValue") T fieldValue);
	
//	@Query(value = "SELECT * FROM place_type WHERE :fieldName IN " +
//		       "(SELECT column_name FROM all_tab_columns WHERE table_name = 'PLACE_TYPE') " +
//		       "AND ANYDATA.GETVARCHAR2(ANYDATA.convert((SELECT COLUMN_VALUE FROM JSON_TABLE( " +
//		       "(SELECT JSON_OBJECTAGG(column_name, data_type) FROM all_tab_columns WHERE table_name = 'PLACE_TYPE'), " +
//		       "'$' COLUMNS (COLUMN_VALUE VARCHAR2(4000) PATH '$.:fieldName'))))) = " +
//		       "ANYDATA.GETVARCHAR2(ANYDATA.convert(:fieldValue))",
//		       nativeQuery = true)
//	List<PlaceType> searchByField(@Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);
	
}

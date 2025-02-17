package com.travel.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.travel.app.dto.ReviewDTO;
import com.travel.app.model.Review;

public interface ReviewService {
	
	Review saveSingleReview(ReviewDTO placeDTO);
	List<Review> saveAllReview(List<ReviewDTO> placesDTO);
	Optional<Review> findReviewById(Long id);
	<T> List<Review> findReviewByField(T fieldname,T fieldvalue);
	Optional<Review> findReviewByUserAndPlace(Long userId, Long placeId);
	List<Review> findAllReviews();
	<T> Page<Review> findAllReviewInPages(int pageNumber, int pageSize, T sortByField, String sortDirection);
	Review updateReview(Long id, ReviewDTO placeDTO);
	List<Review> updateAllReviews(List<Long> idList, List<ReviewDTO> placeDTOList);
	<T> List<Review> updateAllReviewByFields(List<T> fieldList, List<T> valueList, List<ReviewDTO> placesDTO);
	<T> List<Review> updateAllReviewBySingleField(T fieldName, T fieldValue);
	<T> void setFieldValue(Review place, T fiedlName, T fieldValue);
	void deleteReviewById(Long id);
	void deleteAllReviews() throws Exception;
	<T> void deleteReviewByField(T fieldName, T fieldValue);

}

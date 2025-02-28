package com.travel.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.travel.app.dto.ReviewDTO;
import com.travel.app.model.Review;

public interface ReviewService {
	
	Review saveSingleReview(ReviewDTO reviewDTO);
	List<Review> saveAllReview(List<ReviewDTO> reviewsDTO);
	Optional<Review> findReviewById(Long id);
	<T> List<Review> findReviewByField(String fieldname,T fieldvalue);
	List<Review> findReviewByUserAndPlace(Long userId, Long placeId);
	List<Review> findReviewByUser(Long userId);
	List<Review> findReviewByPlace(Long placeId);
	List<Review> findAllReviews();
	<T> Page<Review> findAllReviewInPages(int pageNumber, int pageSize, String sortByField, String sortDirection);
	Review updateReview(Long id, ReviewDTO reviewDTO);
	List<Review> updateAllReviews(List<ReviewDTO> reviewDTOList);
	//<T> List<Review> updateAllReviewByFields(List<T> fieldList, List<T> valueList, List<ReviewDTO> reviewsDTO);
	<T> List<Review> updateAllReviewBySingleField(String fieldName, T fieldValue);
	<T> void setFieldValue(Review review, String fiedlName, T fieldValue);
	void deleteReviewById(Long id);
	void deleteAllReviews() throws Exception;
	<T> void deleteReviewByField(String fieldName, T fieldValue);

}

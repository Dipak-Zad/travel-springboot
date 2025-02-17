package com.travel.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.app.dto.ReviewDTO;
import com.travel.app.model.ApiResponse;
import com.travel.app.model.Review;
import com.travel.app.service.ReviewService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private ReviewService ReviewServ;
	
	@PostMapping("/saveReview")
	public ResponseEntity<ApiResponse<Review>> saveReview(@Valid @RequestBody ReviewDTO reviewDTO)
	{
		Review review = ReviewServ.saveSingleReview(reviewDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "New review saved successfully", review));
	}
	
	@GetMapping("/getAllReviews")
	public ResponseEntity<ApiResponse<List<Review>>> getAllReview()
	{
		List<Review> reviews = ReviewServ.findAllReviews();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All reviews fetched successfully", reviews));
	}
	
	@GetMapping("/findReview/{r_id}")
	public ResponseEntity<ApiResponse<Optional<Review>>> getReviewById(@PathVariable Long id)
	{
		Optional<Review> review = ReviewServ.findReviewById(id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Review found", review));
	}
	
	@PutMapping("/updateReview/{r_id}")
	public ResponseEntity<ApiResponse<Review>> updateReview(@PathVariable("r_id") Long r_id, @Valid @RequestBody ReviewDTO reviewDTO)
	{
		Review review = ReviewServ.updateReview(r_id, reviewDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","Review updated succesfully", review));
	}
	
	@DeleteMapping("deleteReview/{r_id}")
	public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long r_id)
	{
		ReviewServ.deleteReviewById(r_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Review deleted succesfully", null));
	}
}

package com.travel.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PostMapping("/saveAllReview")
	public ResponseEntity<ApiResponse<List<Review>>> saveAllReview(@Valid @RequestBody List<ReviewDTO> reviewDTO)
	{
		List<Review> reviewList = ReviewServ.saveAllReview(reviewDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "All reviews saved successfully", reviewList));
	}
	
	@GetMapping("/findReview/{rv_id}")
	public ResponseEntity<ApiResponse<Optional<Review>>> getReviewById(@PathVariable("rv_id") Long rv_id)
	{
		Optional<Review> review = ReviewServ.findReviewById(rv_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Review found", review));
	}

	@GetMapping("/findReviewByField")
	public <T> ResponseEntity<ApiResponse<List<Review>>> getReviewByField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		List<Review> reviews = ReviewServ.findReviewByField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success", "All reviews fetched successfully", reviews));
	}
	
	@GetMapping("/getAllReviews")
	public ResponseEntity<ApiResponse<List<Review>>> getAllReview()
	{
		List<Review> reviews = ReviewServ.findAllReviews();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All reviews fetched successfully", reviews));
	}
	
	@GetMapping("/getAllReviewsInPages")
	public ResponseEntity<ApiResponse<Page<Review>>> getAllReviewInPages(@RequestParam(value="page_number", defaultValue = "0") int pageNumber, @RequestParam(value="page_size", defaultValue = "10") int pageSize, @RequestParam(value="sort_by_field", defaultValue = "id") String sortByField, @RequestParam(value="sort_direction", defaultValue = "asc") String sortDirection)
	{
		Page<Review> reviews = ReviewServ.findAllReviewInPages(pageNumber, pageSize, sortByField, sortDirection);
		return ResponseEntity.ok(new ApiResponse<>("Success", "All reviews fetched successfully", reviews));
	}
	
	@PutMapping("/updateReview/{rv_id}")
	public ResponseEntity<ApiResponse<Review>> updateReview(@PathVariable("rv_id") Long rv_id, @Valid @RequestBody ReviewDTO reviewDTO)
	{
		Review review = ReviewServ.updateReview(rv_id, reviewDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","Review updated succesfully", review));
	}

	@PutMapping("/updateAllReview")
	public ResponseEntity<ApiResponse<List<Review>>> updateAllReview(@Valid @RequestBody List<ReviewDTO> reviewDTO) //@RequestBody List<Long> pt_id,
	{
		List<Review> review = ReviewServ.updateAllReviews(reviewDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","Review updated succesfully", review));
	}

	@PutMapping("/updateAllReviewBySingleField")
	public <T> ResponseEntity<ApiResponse<List<Review>>> updateAllReviewBySingleField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		List<Review> review = ReviewServ.updateAllReviewBySingleField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success","Review updated succesfully", review));
	}
	
	@DeleteMapping("deleteReview/{rv_id}")
	public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable("rv_id") Long rv_id)
	{
		ReviewServ.deleteReviewById(rv_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Review deleted succesfully", null));
	}

	@DeleteMapping("/deleteAllReview")
	public ResponseEntity<ApiResponse<Void>> deleteAllReview() throws Exception
	{
		ReviewServ.deleteAllReviews();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All Reviews deleted succesfully", null));
	}
	
	@DeleteMapping("/deleteReviewByField")
	public <T> ResponseEntity<ApiResponse<Void>> deleteReviewByField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		ReviewServ.deleteReviewByField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Review deleted succesfully", null));
	}
}

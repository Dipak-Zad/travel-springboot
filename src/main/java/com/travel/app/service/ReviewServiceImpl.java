package com.travel.app.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.travel.app.dto.PlaceDTO;
import com.travel.app.dto.ReviewDTO;
import com.travel.app.exception.DuplicateEntityException;
import com.travel.app.exception.SaveEntityException;
import com.travel.app.model.Place;
import com.travel.app.model.Review;
import com.travel.app.model.User;
import com.travel.app.repository.PlaceRepository;
import com.travel.app.repository.ReviewRepository;
import com.travel.app.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReviewServiceImpl<T> implements ReviewService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ReviewRepository ReviewRepo;
	
	@Autowired
	private UserRepository UserRepo;
	
	@Autowired
	private PlaceRepository PlaceRepo;
	
	@Override
	public Review saveSingleReview(ReviewDTO reviewDTO)
	{
		try
		{
			Long userId = reviewDTO.getUserId();
			Long placeId = reviewDTO.getPlaceId();
 			Optional<Review> rCheck = ReviewRepo.findReviewByUserAndPlace(userId, placeId);
 			Review rvw = new Review();
			if(rCheck == null)
			{
				rvw = modelMapper.map(reviewDTO, Review.class);
				rvw = ReviewRepo.save(rvw);
				if(rvw != null)
				{
					//return plc == null ? null : plc;
					return rvw;
				}
				else
				{
					throw new SaveEntityException("Failed to save review");	
				}
			}
			else
			{
				throw new DuplicateEntityException(placeId+" review from '"+userId+"' already exists.");	
			}
			
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save review");
			//e.printStackTrace();
		}
		
	}
	
	@Override
	public List<Review> saveAllReview(List<ReviewDTO> reviewsDTO)
	{
		try
		{
			List<Review> reviews = new ArrayList<Review>();
			Optional<Review> OptReview = null;
			Long userId,placeId;
			for(ReviewDTO rvwDTO : reviewsDTO)
			{
				userId = rvwDTO.getUserId();
				placeId = rvwDTO.getPlaceId();
				OptReview = ReviewRepo.findReviewByUserAndPlace(userId, placeId);
				if(OptReview==null)
				{
					Review tempRvw = modelMapper.map(rvwDTO, Review.class);
					reviews.add(tempRvw);
				}
				else
				{
					throw new DuplicateEntityException(placeId+" review from '"+userId+"' already exists.");
				}
				
			}

			reviews = ReviewRepo.saveAll(reviews);
			if(reviews != null)
			{
				return reviews;
			}
			else
			{
				throw new SaveEntityException("Failed to save given reviews");
			}
			//return reviews == null ? null : reviews; 
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save given reviews");
			//e.printStackTrace();
		}
		
		//return null;
	}
	
	@Override
	public Optional<Review> findReviewById(Long id)
	{
		try
		{
			Optional<Review> plc = ReviewRepo.findById(id);
			if(plc!=null)
			{
				return plc;
			}
			else
			{
				throw new EntityNotFoundException("Review with '"+id+"' not found");
			}
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Review with '"+id+"' not found");
		}
	}
	

	@Override
	public <T> List<Review> findReviewByField(T fieldName,T fieldValue)
	{
		try
		{
			List<Review> reviews = ReviewRepo.searchByField(fieldName, fieldValue);
			if(reviews!=null)
			{
				return reviews;
			}
			else
			{
				throw new EntityNotFoundException("Review with '"+fieldName+"' '"+fieldValue+"' not found");
			}
			
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Review with '"+fieldName+"' '"+fieldValue+"' not found");
		}
	}
	
	@Override
	public Optional<Review> findReviewByUserAndPlace(Long userId, Long reviewId)
	{
		try{
			Optional<Review> rvw = ReviewRepo.findReviewByUserAndPlace(userId, reviewId);
			return rvw == null ? null : rvw;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException(reviewId+" review from '"+userId+"' not found");
		}
	}
	
	@Override
	public List<Review> findAllReviews()
	{
		try
		{
			List<Review> plcs = ReviewRepo.findAll();
			return plcs == null ? null : plcs;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all reviews");
		}
	}
	
	@Override
	public <T> Page<Review> findAllReviewInPages(int page, int size, T sortBy, String sortDir)
	{
		try
		{
			Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(String.valueOf(sortBy)).ascending() : Sort.by(String.valueOf(sortBy)).descending();
			Pageable pageable = PageRequest.of(page, size, sort);
			return ReviewRepo.findAll(pageable);
					
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all reviews");
		}
	}
	
	@Override
	public Review updateReview(Long id, ReviewDTO reviewDTO)
	{
		try
		{
			Review plc = ReviewRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No review found with ID "+id));
			if(plc==null)
			{
				plc = modelMapper.map(reviewDTO, Review.class);
				return ReviewRepo.save(plc);
			}
			else
			{
				throw new SaveEntityException("Failed to update review");
			}
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update review");
		}
	}
	
	@Override
	public List<Review> updateAllReviews(List<Long> idList, List<ReviewDTO> reviewsDTO)
	{
		try
		{
			if(idList.size() != reviewsDTO.size())
			{
				throw new IllegalArgumentException("ID list & DTO list must have the same size");
			}
			List<Review> reviews = new ArrayList<>();
			for(int i=0; i<idList.size();i++)
			{
				final Long reviewId = idList.get(i);
				Review review = ReviewRepo.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("No review found with ID "+reviewId));
				modelMapper.map(reviewsDTO.get(i), review);
				reviews.add(ReviewRepo.save(review));		
			}
			return reviews;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update review");
		}
	}
	
	@Override
	public <T> List<Review> updateAllReviewByFields(List<T> reviewFieldList, List<T> reviewValueList, List<ReviewDTO> updateReviewsDTO)
	{
		if(reviewFieldList.size() != reviewValueList.size() || reviewValueList.size() != updateReviewsDTO.size())
		{
			throw new IllegalArgumentException("ID list & DTO list must have the same size");
		}
		try
		{
			List<Review> updatedReviews = new ArrayList<>();
			
			for(int i=0;i<reviewFieldList.size();i++)
			{
				T fieldName = reviewFieldList.get(i);
				T fieldValue = reviewValueList.get(i);
				ReviewDTO reviewDTO = updateReviewsDTO.get(i);
				updatedReviews = findReviewByField(fieldName,fieldValue);
				modelMapper.map(reviewDTO, updatedReviews.get(i));
				updatedReviews.add(ReviewRepo.save(updatedReviews.get(i)));
				
				throw new EntityNotFoundException("No review found with "+fieldName+" : '"+fieldValue+"'");
		
			}
			
			return updatedReviews;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update given reviews" +e.getMessage());
		}
	}
	
	@Override
	public <T> List<Review> updateAllReviewBySingleField(T fieldName, T fieldValue)
	{
		
		try
		{
			List<Review> reviews = ReviewRepo.findAll();
			for(Review plc : reviews)
			{
				setFieldValue(plc, fieldName, fieldValue);
				ReviewRepo.save(plc);
			}
			return reviews;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update all review's "+fieldName+" with '"+fieldValue+"'");
		}
	}
	
	@Override
	public <T> void setFieldValue(Review review, T fieldName, T fieldValue)
	{
		try
		{
			Field field = Review.class.getDeclaredField(fieldName.toString());
			field.setAccessible(true);
			field.set(review, fieldValue);
		}
		catch(NoSuchFieldException | IllegalAccessException e)
		{
			throw new IllegalArgumentException("Invalid field: "+fieldName);
		}
	}
 
	@Override
	public void deleteReviewById(Long id) {
		try
		{
			ReviewRepo.deleteById(id);
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to All reviews");
		}
		
	}

	@Override
	public void deleteAllReviews() throws Exception {
		try
		{
			ReviewRepo.deleteAll();
		}
		catch(Exception e)
		{
			throw new Exception("Failed to delete all the reviews");
		}
		
	}

	@Override
	public <T> void deleteReviewByField(T fieldName, T fieldValue)
	{
		try
		{
			List<Review> reviews = findReviewByField(fieldName, fieldValue);
			ReviewRepo.deleteAll(reviews);
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Failed to delete review by "+fieldName+" : '"+fieldValue+"'");
		}
		
	}
	
	
	
//	private Review DtoToReview(ReviewDTO reviewDTO) {
//		Review review = modelMapper.map(reviewDTO, Review.class);
//		return review;
//	}
//	
//	private ReviewDTO CategoryToDto(Review review) {
//		ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
//		return reviewDTO;
//	}
	

}

package com.travel.app.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
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

import com.travel.app.dto.ReviewDTO;
import com.travel.app.exception.DuplicateEntityException;
import com.travel.app.exception.SaveEntityException;
import com.travel.app.model.Place;
import com.travel.app.model.Review;
import com.travel.app.model.Users;
import com.travel.app.repository.PlaceRepository;
import com.travel.app.repository.ReviewRepository;
import com.travel.app.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ReviewServiceImpl<T> implements ReviewService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ReviewRepository ReviewRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PlaceRepository placeRepo;
	
	@Override
	public Review saveSingleReview(ReviewDTO reviewDTO)
	{
		try
		{
			Long user = reviewDTO.getUserId();
			Long place = reviewDTO.getPlaceId();
			List<Review> rCheck = ReviewRepo.findReviewByUserAndPlace(user, place);
 			Review review = new Review();
			
 			if(!rCheck.isEmpty())
			{
				throw new DuplicateEntityException(place+" review from '"+user+"' already exists.");	
			}
			
			review = modelMapper.map(reviewDTO, Review.class);
			review.setCreatedDate(LocalDateTime.now());
			review.setCreatedBy("current session user");
			review.setModifiedDate(LocalDateTime.now());
			review.setModifiedBy("current session user");
			review = ReviewRepo.save(review);
			
			if(review == null)
			{
				throw new SaveEntityException("Failed to save review");	
			}
				
			return review;
			
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save review");
		}
		
	}
	
	
	@Override
	public List<Review> saveAllReview(List<ReviewDTO> reviewsDTO)
	{
		try
		{
			List<Review> reviews = new ArrayList<Review>();
			List<Review> ReviewList = null;
			Long userId,placeId;
			for(ReviewDTO reviewDTO : reviewsDTO)
			{
				userId = reviewDTO.getUserId();
				placeId = reviewDTO.getPlaceId();
				ReviewList = ReviewRepo.findReviewByUserAndPlace(userId, placeId);
				
				if(!ReviewList.isEmpty())
				{
					throw new DuplicateEntityException(placeId+" review from '"+userId+"' already exists.");
				}
				
				Review review = modelMapper.map(reviewDTO, Review.class);
				review.setCreatedDate(LocalDateTime.now());
				review.setCreatedBy("current session user");
				review.setModifiedDate(LocalDateTime.now());
				review.setModifiedBy("current session user");
				reviews.add(review);
			}

			reviews = ReviewRepo.saveAll(reviews);

			if(reviews == null)
			{
				throw new SaveEntityException("Failed to save given reviews");
			}
			
			return reviews;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to save given reviews");
		}
		
	}
	
	
	@Override
	public Optional<Review> findReviewById(Long id)
	{
		try
		{
			Optional<Review> review = ReviewRepo.findById(id);
			if(review.isPresent())
			{
				return review;
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
	public <T> List<Review> findReviewByField(String fieldName,T fieldValue)
	{
		try
		{
			String query = "SELECT * FROM review WHERE " + fieldName + " = ?";
			 
			 return entityManager.createNativeQuery(query, Review.class)
                    .setParameter(1, fieldValue)
                    .getResultList();
			
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Review with '"+fieldName+"' '"+fieldValue+"' not found");
		}
	}
	
	
	@Override
	public List<Review> findReviewByUserAndPlace(Long userId, Long reviewId)
	{
		try
		{
			List<Review> review = ReviewRepo.findReviewByUserAndPlace(userId, reviewId);
			return review == null ? null : review;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException(reviewId+" review from '"+userId+"' not found");
		}
	}
	
	@Override
	public List<Review> findReviewByUser(Long userId)
	{
		String UserName = "";
		try
		{
			Users user = userRepo.findById(userId).orElseThrow(() -> new  EntityNotFoundException("user with '"+userId+"' not found")); 
			UserName = user.getUserName();
			List<Review> review = ReviewRepo.findReviewByUser(userId);
			return review == null ? null : review;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("review from '"+UserName+"' not found");
		}
	}
	
	@Override
	public List<Review> findReviewByPlace(Long placeId)
	{
		String Place = "";
		try
		{
			Place place = placeRepo.findById(placeId).orElseThrow(() -> new EntityNotFoundException("place with '"+placeId+"' not found"));
			Place = place.getPlaceName();
			List<Review> review = ReviewRepo.findReviewByPlace(placeId);
			return review == null ? null : review;

		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("review of '"+Place+"' not found");
		}
	}
	
	
	@Override
	public List<Review> findAllReviews()
	{
		try
		{
			List<Review> reviews = ReviewRepo.findAll();
			return reviews == null ? null : reviews;
		}
		catch(Exception e)
		{
			throw new EntityNotFoundException("Failed to find all reviews");
		}
	}
	
	
	@Override
	public <T> Page<Review> findAllReviewInPages(int page, int size, String sortBy, String sortDir)
	{
		try
		{
			Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
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
			Review review = ReviewRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No review found with ID "+id));
			
			if(reviewDTO.getRating()!=null && ((reviewDTO.getRating()<=5) && (reviewDTO.getRating()>0)))
			{
				review.setRating(reviewDTO.getRating());
			}
			
			if(reviewDTO.getReview()!=null && !reviewDTO.getReview().trim().isEmpty())
			{
				review.setReview(reviewDTO.getReview());
			}
			
			if(reviewDTO.getStatus()!=null)
			{
				review.setStatus(reviewDTO.getStatus());
			}
			
			review.setModifiedDate(LocalDateTime.now());
			review.setModifiedBy("new current session user");
		
			return ReviewRepo.save(review);
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update review");
		}
	}
	
	
	@Override
	public List<Review> updateAllReviews(List<ReviewDTO> reviewsDTO)
	{
		try
		{
			List<Review> reviews = new ArrayList<>();
			
			for(int i=0; i<reviewsDTO.size();i++)
			{
				ReviewDTO reviewDTO = reviewsDTO.get(i);
				Long reviewId = reviewDTO.getId();
				Review review = ReviewRepo.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("No review found with ID "+reviewId));
				
				if(reviewDTO.getRating()!=null && ((reviewDTO.getRating()<=5) && (reviewDTO.getRating()>0)))
				{
					review.setRating(reviewDTO.getRating());
				}
				
				if(reviewDTO.getReview()!=null && !reviewDTO.getReview().trim().isEmpty())
				{
					review.setReview(reviewDTO.getReview());
				}
				
				if(reviewDTO.getStatus()!=null)
				{
					review.setStatus(reviewDTO.getStatus());
				}
				
				review.setModifiedDate(LocalDateTime.now());
				review.setModifiedBy("new current session user");
				
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
	public <T> List<Review> updateAllReviewBySingleField(String fieldName, T fieldValue)
	{
		
		try
		{
			List<Review> reviews = ReviewRepo.findAll();

			if (reviews.isEmpty()) {
	            throw new EntityNotFoundException("No placeTypes found to update");
	        }
			
			for(Review review : reviews)
			{
				setFieldValue(review, fieldName, fieldValue);
				ReviewRepo.save(review);
			}
			return reviews;
		}
		catch(Exception e)
		{
			throw new SaveEntityException("Failed to update all review's "+fieldName+" with '"+fieldValue+"'");
		}
	}
	
	
	@Override
	public <T> void setFieldValue(Review review, String fieldName, T fieldValue)
	{
		try
		{
			Field field = Review.class.getDeclaredField(fieldName.toString());
			field.setAccessible(true);
			if (field.getType().isEnum()) {
	            @SuppressWarnings("unchecked")
	            Class<Enum> enumType = (Class<Enum>) field.getType();
	            Enum enumValue = Enum.valueOf(enumType, fieldValue.toString());
	            field.set(review, enumValue);
	        } else {
	            field.set(review, fieldValue);
	        }
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
	public <T> void deleteReviewByField(String fieldName, T fieldValue)
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
	

	

}

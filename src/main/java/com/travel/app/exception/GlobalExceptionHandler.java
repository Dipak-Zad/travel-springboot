package com.travel.app.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.travel.app.model.ApiResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//	@ExceptionHandler(EntityNotFoundException.class)
//	public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(EntityNotFoundException ex)
//	{
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("error", ex.getMessage(), null));
//	}
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex)
//	{
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("error", "Invalid data", null));
//	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleEntityNotFoundExceptions(EntityNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse<>("error", ex.getMessage(), null));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex)
	{
		List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiResponse<>("error","Validation failed", errorMessages)); 
	}
	
	@ExceptionHandler(SaveEntityException.class)
    public ResponseEntity<ApiResponse<Void>> handleSaveEntity(SaveEntityException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        		.body(new ApiResponse<>("error", ex.getMessage(), null));
    }
	
	@ExceptionHandler(DuplicateEntityException.class)
	public ResponseEntity<ApiResponse<Void>> handleDuplicateEntityExceptions(DuplicateEntityException ex)
	{
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiResponse<>("error", ex.getMessage(), null));
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        		.body(new ApiResponse<>("Something went wrong:", ex.getMessage(), null));
    }
	
}

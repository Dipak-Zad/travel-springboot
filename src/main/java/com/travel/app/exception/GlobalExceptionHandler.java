package com.travel.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.travel.app.model.ApiResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(EntityNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("error", ex.getMessage(), null));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("error", "Invalid data", null));
	}
	
}

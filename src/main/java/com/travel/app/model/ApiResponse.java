package com.travel.app.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class ApiResponse<T> {

	private String status;  // success | error
    private String message; // Human-readable message
    private T data;         // Actual response data
    private List<String> errors; // Optional: List of errors
    private String timestamp; // Optional: ISO 8601 timestamp
    private String path; // Optional: API endpoint path

    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now().toString();
    }

    public ApiResponse(String status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = Instant.now().toString();
    }
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}

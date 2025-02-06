package com.travel.app.model;

import java.time.LocalDateTime;

public class ApiResponse<T> {

	private String status;
	private String Message;
	private T data;
	private LocalDateTime timestamp;
	
	
	
	public ApiResponse(String status, String message, T data) {
		super();
		this.status = status;
		this.Message = message;
		this.data = data;
		this.timestamp = LocalDateTime.now();
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
}

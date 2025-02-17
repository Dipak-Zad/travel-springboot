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

import com.travel.app.dto.UserDTO;
import com.travel.app.model.ApiResponse;
import com.travel.app.model.User;
import com.travel.app.service.UserService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService UserServ;
	
	@PostMapping("/saveUser")
	public ResponseEntity<ApiResponse<User>> saveUser(@Valid @RequestBody UserDTO userDTO)
	{
		User user = UserServ.saveSingleUser(userDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "New user saved successfully", user));
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<ApiResponse<List<User>>> getAllUser()
	{
		List<User> users = UserServ.findAllUsers();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All users fetched successfully", users));
	}
	
	@GetMapping("/findUser/{u_id}")
	public ResponseEntity<ApiResponse<Optional<User>>> getUserById(@PathVariable Long id)
	{
		Optional<User> user = UserServ.findUserById(id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "User found", user));
	}
	
	@PutMapping("/updateUser/{u_id}")
	public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable("u_id") Long u_id, @Valid @RequestBody UserDTO userDTO)
	{
		User user = UserServ.updateUser(u_id, userDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","User updated succesfully", user));
	}
	
	@DeleteMapping("deleteUser/{u_id}")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long u_id)
	{
		UserServ.deleteUserById(u_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "User deleted succesfully", null));
	}
}

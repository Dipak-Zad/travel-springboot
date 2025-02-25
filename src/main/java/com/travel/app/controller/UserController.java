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
	
	@PostMapping("/saveAllUser")
	public ResponseEntity<ApiResponse<List<User>>> saveAllUser(@Valid @RequestBody List<UserDTO> userDTO)
	{
		List<User> userList = UserServ.saveAllUser(userDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "All users saved successfully", userList));
	}
	
	@GetMapping("/findUser/{u_id}")
	public ResponseEntity<ApiResponse<Optional<User>>> getUserById(@PathVariable("u_id") Long id)
	{
		Optional<User> user = UserServ.findUserById(id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "User found", user));
	}
	
	@GetMapping("/findUserByField")
	public <T> ResponseEntity<ApiResponse<List<User>>> getUserByField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		List<User> users = UserServ.findUserByField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success", "All users fetched successfully", users));
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<ApiResponse<List<User>>> getAllUser()
	{
		List<User> users = UserServ.findAllUsers();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All users fetched successfully", users));
	}
	
	@GetMapping("/getAllUsersInPages")
	public ResponseEntity<ApiResponse<Page<User>>> getAllUserInPages(@RequestParam(value="page_number", defaultValue = "0") int pageNumber, @RequestParam(value="page_size", defaultValue = "10") int pageSize, @RequestParam(value="sort_by_field", defaultValue = "id") String sortByField, @RequestParam(value="sort_direction", defaultValue = "asc") String sortDirection)
	{
		Page<User> users = UserServ.findAllUserInPages(pageNumber, pageSize, sortByField, sortDirection);
		return ResponseEntity.ok(new ApiResponse<>("Success", "All users fetched successfully", users));
	}
	
	@PutMapping("/updateUser/{u_id}")
	public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable("u_id") Long u_id, @Valid @RequestBody UserDTO userDTO)
	{
		User user = UserServ.updateUser(u_id, userDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","User updated succesfully", user));
	}
	
	@PutMapping("/updateAllUser")
	public ResponseEntity<ApiResponse<List<User>>> updateAllUser(@Valid @RequestBody List<UserDTO> userDTO) //@RequestBody List<Long> pt_id,
	{
		List<User> user = UserServ.updateAllUsers(userDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","User updated succesfully", user));
	}
	
	@PutMapping("/updateAllUserBySingleField")
	public <T> ResponseEntity<ApiResponse<List<User>>> updateAllUserBySingleField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		List<User> user = UserServ.updateAllUserBySingleField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success","User updated succesfully", user));
	}
	
	@DeleteMapping("deleteUser/{u_id}")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("u_id") Long u_id)
	{
		UserServ.deleteUserById(u_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "User deleted succesfully", null));
	}
	
	@DeleteMapping("/deleteAllUser")
	public ResponseEntity<ApiResponse<Void>> deleteAllUser() throws Exception
	{
		UserServ.deleteAllUsers();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All Users deleted succesfully", null));
	}
	
	@DeleteMapping("/deleteUserByField")
	public <T> ResponseEntity<ApiResponse<Void>> deleteUserByField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		UserServ.deleteUserByField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success", "User deleted succesfully", null));
	}
}

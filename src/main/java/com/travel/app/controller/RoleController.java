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

import com.travel.app.dto.RoleDTO;
import com.travel.app.model.ApiResponse;
import com.travel.app.model.Role;
import com.travel.app.service.RoleService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/roles")
public class RoleController {

	@Autowired
	private RoleService RoleServ;
	
	@PostMapping("/saveRole")
	public ResponseEntity<ApiResponse<Role>> saveRole(@Valid @RequestBody RoleDTO roleDTO)
	{
		Role role = RoleServ.saveSingleRole(roleDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "New role saved successfully", role));
	}
	
	@GetMapping("/getAllRoles")
	public ResponseEntity<ApiResponse<List<Role>>> getAllRole()
	{
		List<Role> roles = RoleServ.findAllRoles();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All roles fetched successfully", roles));
	}
	
	@GetMapping("/findRole/{r_id}")
	public ResponseEntity<ApiResponse<Optional<Role>>> getRoleById(@PathVariable Long id)
	{
		Optional<Role> role = RoleServ.findRoleById(id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Role found", role));
	}
	
	@PutMapping("/updateRole/{r_id}")
	public ResponseEntity<ApiResponse<Role>> updateRole(@PathVariable("r_id") Long r_id, @Valid @RequestBody RoleDTO roleDTO)
	{
		Role role = RoleServ.updateRole(r_id, roleDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","Role updated succesfully", role));
	}
	
	@DeleteMapping("deleteRole/{r_id}")
	public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long r_id)
	{
		RoleServ.deleteRoleById(r_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Role deleted succesfully", null));
	}
}

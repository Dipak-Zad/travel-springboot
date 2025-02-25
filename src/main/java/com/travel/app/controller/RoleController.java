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
	
	@PostMapping("/saveAllRole")
	public ResponseEntity<ApiResponse<List<Role>>> saveAllRole(@Valid @RequestBody List<RoleDTO> roleDTO)
	{
		List<Role> roleList = RoleServ.saveAllRole(roleDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Success", "All roles saved successfully", roleList));
	}
	
	@GetMapping("/findRole/{r_id}")
	public ResponseEntity<ApiResponse<Optional<Role>>> getRoleById(@PathVariable("r_id") Long r_id)
	{
		Optional<Role> role = RoleServ.findRoleById(r_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Role found", role));
	}
	
	@GetMapping("/findRoleByField")
	public <T> ResponseEntity<ApiResponse<List<Role>>> getRoleByField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		List<Role> roles = RoleServ.searchByField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success", "All roles fetched successfully", roles));
	}
	
	@GetMapping("/getAllRoles")
	public ResponseEntity<ApiResponse<List<Role>>> getAllRole()
	{
		List<Role> roles = RoleServ.findAllRoles();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All roles fetched successfully", roles));
	}
	
	@GetMapping("/getAllRolesInPages")
	public ResponseEntity<ApiResponse<Page<Role>>> getAllRoleInPages(@RequestParam(value="page_number", defaultValue = "0") int pageNumber, @RequestParam(value="page_size", defaultValue = "10") int pageSize, @RequestParam(value="sort_by_field", defaultValue = "id") String sortByField, @RequestParam(value="sort_direction", defaultValue = "asc") String sortDirection)
	{
		Page<Role> roles = RoleServ.findAllRoleInPages(pageNumber, pageSize, sortByField, sortDirection);
		return ResponseEntity.ok(new ApiResponse<>("Success", "All roles fetched successfully", roles));
	}
	
	@PutMapping("/updateRole/{r_id}")
	public ResponseEntity<ApiResponse<Role>> updateRole(@PathVariable("r_id") Long r_id, @Valid @RequestBody RoleDTO roleDTO)
	{
		Role role = RoleServ.updateRole(r_id, roleDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","Role updated succesfully", role));
	}
	
	@PutMapping("/updateAllRole")
	public ResponseEntity<ApiResponse<List<Role>>> updateAllRole(@Valid @RequestBody List<RoleDTO> roleDTO) //@RequestBody List<Long> pt_id,
	{
		List<Role> role = RoleServ.updateAllRoles(roleDTO);
		return ResponseEntity.ok(new ApiResponse<>("Success","Role updated succesfully", role));
	}
	
	@PutMapping("/updateAllRoleBySingleField")
	public <T> ResponseEntity<ApiResponse<List<Role>>> updateAllRoleBySingleField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		List<Role> role = RoleServ.updateAllRoleBySingleField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success","Role updated succesfully", role));
	}
	
	@DeleteMapping("deleteRole/{r_id}")
	public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable("r_id") Long r_id)
	{
		RoleServ.deleteRoleById(r_id);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Role deleted succesfully", null));
	}
	
	@DeleteMapping("/deleteAllRole")
	public ResponseEntity<ApiResponse<Void>> deleteAllRole() throws Exception
	{
		RoleServ.deleteAllRoles();
		return ResponseEntity.ok(new ApiResponse<>("Success", "All Roles deleted succesfully", null));
	}
	
	@DeleteMapping("/deleteRoleByField")
	public <T> ResponseEntity<ApiResponse<Void>> deleteRoleByField(@RequestParam("field_name") String fieldName, @RequestParam("field_value") T fieldValue)
	{
		RoleServ.deleteRoleByField(fieldName,fieldValue);
		return ResponseEntity.ok(new ApiResponse<>("Success", "Role deleted succesfully", null));
	}
	
}

package com.dxc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.entity.ApiResponse;
import com.dxc.entity.Users;
import com.dxc.payload.UserDto;
import com.dxc.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@GetMapping("/all") 
	public ApiResponse<List<Users>> listUsers() {
		return new ApiResponse<>(HttpStatus.OK.value(), "User list fetched successfully.", userService.getAllUsers());
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> deleteUserById(@PathVariable(name = "id") long uid) {
		userService.deletUserById(uid);
		return new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully.", null);
	}

	@PutMapping("/{id}")
	public ApiResponse<UserDto> update(@RequestBody UserDto userDto) {
		return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", userService.updateUser(userDto));
	}
}

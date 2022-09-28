package com.dxc.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.entity.ApiResponse;
import com.dxc.entity.Posts;
import com.dxc.entity.Roles;
import com.dxc.entity.Roles.ERole;
import com.dxc.entity.Users;
import com.dxc.exception.ResourceNotFoundException;
import com.dxc.exception.UserExistException;
import com.dxc.payload.SignUpDto;
import com.dxc.repository.RoleRepository;
import com.dxc.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/{id}")
	 public Users getUserById(@PathVariable("id") long uid, Posts posts) {
		return userRepository.findById(uid).map(post -> {
		    posts.getUsers();
		    return post;
		  }).orElseThrow(() -> new ResourceNotFoundException("Not found user with id", null, uid));
	}

	@PostMapping("/registration")
	public  ApiResponse<Users> createUser(@RequestBody SignUpDto signUpDto) throws UserExistException {
		if(userRepository.findByEmail(signUpDto.getEmail()) !=null){
            throw new UserExistException("User already exists for this email: " + signUpDto.getEmail());
    }
	
		Users user = new Users (
			signUpDto.getEmail(),
			passwordEncoder.encode(signUpDto.getPassword()),
			signUpDto.getUsername());
	
		Set<String> strRoles = signUpDto.getRole();
		Set<Roles> roles = new HashSet<>();
	
		if (strRoles == null) {
			Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Roles adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						break;
					default:
						Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
				});
		}
	
		user.setRoles(roles);
		return new ApiResponse<>(HttpStatus.OK.value(), "User registered successfully", userRepository.save(user));
	}

}

package com.dxc.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.dxc.entity.Roles;
import com.dxc.entity.Users;
import com.dxc.exception.PasswordNotMatchException;
import com.dxc.exception.UserExistException;
import com.dxc.exception.UserNotFoundException;
import com.dxc.payload.SignUpDto;
import com.dxc.payload.UserDto;

public interface UserService  extends UserDetailsService{
	
	List<Users> getAllUsers ();
 
	Users getUserById(long uid);
	
	UserDto changePassword(UserDto userDto, String email);
	
	UserDto updateUser (UserDto userDto);
	
	void deletUserById(long uid);

	boolean checkIfUserExist(String email);
	
	String updateResetPasswordToken (String email);
	
	Users getUserByEmail(String email);

	Users getByResetPasswordToken(String token);

	void updatePassword(Users users, String newPassword);
	
}

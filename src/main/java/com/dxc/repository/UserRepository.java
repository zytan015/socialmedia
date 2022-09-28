package com.dxc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	
	public Users findByEmail(String email);
	public Users findByResetPasswordToken(String token);
	public Users findByUsername(String username);
	
	//List<Users> findUserByRoleId(Long rid);
}

package com.dxc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.entity.Roles;
import com.dxc.repository.RoleRepository;

@Service         
public class RoleServiceImpl implements RoleService{
	
	@Autowired 
	private RoleRepository roleRepository;

	@Override
	public List<Roles> listRoles(Roles roles) {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

}

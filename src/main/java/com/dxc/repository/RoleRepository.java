package com.dxc.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.entity.Roles;
import com.dxc.entity.Roles.ERole;
import com.dxc.entity.Users;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long>{

	Optional<Roles> findByName(ERole name);

	//List<Roles> findRoleByUserId(Long uid);


}

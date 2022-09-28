package com.dxc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dxc.entity.Posts;
import com.dxc.entity.Users;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long>{

//	@Query(value="SELECT pid FROM Posts p WHERE p.uid = ?1")
//	long findPidByUid(long uid);
	
	//Optional<Posts> findPostByUid(Long uid);
}

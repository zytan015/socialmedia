package com.dxc.payload;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dxc.entity.Posts;
import com.dxc.entity.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Long uid;
	private String username;
	private String email;
	private String password;
	private Set<Roles> role = new HashSet<>();
	//private Set<Posts> post = new HashSet<>();
	private List<Posts> posts;

}

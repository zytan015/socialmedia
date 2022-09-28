package com.dxc.payload;

import com.dxc.entity.DatabaseFile;
import com.dxc.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

	private Long pid;
	private int views;
	private String description;
	private String link;
	private Users users;
	private DatabaseFile dbFile;
}

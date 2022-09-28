package com.dxc.service;

import com.dxc.entity.Posts;
import com.dxc.payload.PostDTO;
import com.dxc.payload.PostResponse;

public interface PostService {
	
	PostResponse getAppPosts(int PageNo, int pageSize, String sortBy, String sortDir);
	
	PostDTO getPostById(long id);
	
	PostDTO updatePost(String caption, long id, String modifiedBy);
	
	void deletePostById(long id);

	Posts createPost(Posts posts);

	PostDTO updateViewById(long pid, int count);

}

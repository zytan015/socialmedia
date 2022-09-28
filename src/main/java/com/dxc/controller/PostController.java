package com.dxc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.entity.ApiResponse;
import com.dxc.entity.Posts;
import com.dxc.payload.PostDTO;
import com.dxc.payload.PostResponse;
import com.dxc.repository.PostRepository;
import com.dxc.service.PostService;
import com.dxc.utils.AppConst;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	
	@PostMapping
	public ApiResponse<Posts> createPost(@RequestBody Posts posts, HttpServletRequest request){
		System.out.println(posts);
		return new ApiResponse<Posts>(HttpStatus.CREATED.value(),"Post created successfully" , postService.createPost(posts));
	}
	
	//Get all Post REST API
	@GetMapping("/all")
	public PostResponse getAllBlobs(
			@RequestParam(value="pageNo", defaultValue = AppConst.DEFAULT_PAGE_NUMBER, required=false)int pageNo, 
			@RequestParam(value="pageSize", defaultValue=AppConst.DEFAULT_PAGE_SIZE,required=false)int pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConst.DEFAULT_SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConst.DEFAULT_SORT_DIRECTION,required=false)String sortDir
			){
				return postService.getAppPosts(pageNo, pageSize, sortBy, sortDir);	
	}
	
	@GetMapping
	public ApiResponse<PostDTO> getAllPost(){
		return new  ApiResponse<PostDTO>(HttpStatus.OK.value(), null, postRepository.findAll());
		
	}
	
	//Get all Post By Id
	@GetMapping("/{id}")
	public ApiResponse<Posts> getPostById(@PathVariable(name="id") long pid){
		return new ApiResponse<Posts>(HttpStatus.OK.value(), "Successfully retrieve posts", postService.getPostById(pid));
	}
	
	//Update post by ID
	@PutMapping("/{id}")
	public ApiResponse<Posts> updatePost(@PathVariable(name="id") long pid, @RequestParam String caption, @RequestParam String modifiedBy){
		return new ApiResponse<Posts>(HttpStatus.OK.value(), "Post caption updated", postService.updatePost(caption, pid, modifiedBy));
	}
	
	//Delete post by Id
	@DeleteMapping("/{id}")
	public ApiResponse<String> deletePostById(@PathVariable(name="id") long pid){
		postService.deletePostById(pid);
		return new ApiResponse<String>(HttpStatus.OK.value(),"Post Entity Deleted Successfully", null);
	}
	
	@PutMapping("/views/{id}")
	public ApiResponse<Posts> updateViewById(@PathVariable(name="id") long pid, @RequestBody int count){
		//postService.updateViewById(pid, count);
		return new ApiResponse<Posts>(HttpStatus.OK.value(),"Views updated successfully", postService.updateViewById(pid, count));
	}
	
	
}

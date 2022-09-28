package com.dxc.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dxc.entity.Posts;
import com.dxc.entity.Users;
import com.dxc.exception.ResourceNotFoundException;
import com.dxc.payload.PostDTO;
import com.dxc.payload.PostResponse;
import com.dxc.repository.DatabaseFileRepository;
import com.dxc.repository.PostRepository;
import com.dxc.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DatabaseFileRepository dbFileRespository;

	
	//convert entity to DTO
	private PostDTO mapToDto(Posts posts) {
		PostDTO postDto = new PostDTO();
		postDto.setPid(posts.getPid());
		postDto.setViews(posts.getViews());
		postDto.setDescription(posts.getDescription());
		postDto.setLink(posts.getLink());
		postDto.setUsers(posts.getUsers());
		postDto.setDbFile(posts.getDbFile());
		
		return postDto;
	}
	
	//convert DTO to entity
	public Posts mapToEntity(PostDTO postDTO) {
		Posts posts = new Posts();
		posts.setViews(postDTO.getViews());
		posts.setDescription(postDTO.getDescription());
		posts.setLink(postDTO.getLink());
		posts.setUsers(postDTO.getUsers());
		posts.setDbFile(postDTO.getDbFile());
		return posts;
	}
	
	
	
	//implementing Create PostBlog
//	@Override
//	public PostDTO createPostByPostDto(PostDTO postDto) {
//		// convert Dto to entity
//		Posts posts = mapToEntity(postDto);
//		Posts newPost = postRepository.save(posts);
//		
//		//convert entity to DTO
//		PostDTO postResponse = mapToDto(newPost);
//		return postResponse;
//	}
	
	@Override
	public Posts createPost(Posts posts) {
		Users users = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Set<Posts> post = new HashSet<Posts>();
		posts.setDbFile(posts.getDbFile());
		posts.setCreatedBy(posts.getCreatedBy().replace("\"", ""));
		posts.setLastModifiedBy(posts.getLastModifiedBy().replace("\"", ""));
		posts.setUsers(users);
		post.add(posts);
		return postRepository.save(posts);  
	}
	

	@Override
	public PostResponse getAppPosts(int PageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
			Sort.by(sortBy).ascending()
			:Sort.by(sortBy).descending();
		Pageable pagable = PageRequest.of(PageNo, pageSize, sort);
		Page<Posts> posts=postRepository.findAll(pagable);
		List<Posts> listOfPosts=posts.getContent();
		
		List<PostDTO> content=listOfPosts.stream().map(
				post -> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElement(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		return postResponse;
	}
	
	@Override
	public PostDTO getPostById(long pid) {
		Posts posts = postRepository.findById(pid).orElseThrow(
				() ->new ResourceNotFoundException("Posts", "pid", pid)
				);
		return mapToDto(posts);
	}
	
	@Override
	public PostDTO updatePost(String caption, long pid, String modifiedBy) {
		Posts posts = postRepository.findById(pid).orElseThrow(
				() ->new ResourceNotFoundException("Post", "pid", pid)
				);
		posts.setDescription(caption);
		posts.setLastModifiedBy(modifiedBy.replace("\"", ""));
		postRepository.save(posts);
		return mapToDto(posts);
	}
	
	@Override
	public void deletePostById(long pid) {
		Posts posts = postRepository.findById(pid).orElseThrow(
				() ->new ResourceNotFoundException("Posts", "pid", pid)
				);
		
		postRepository.delete(posts);
	}
	
	@Override
	public PostDTO updateViewById(long pid, int count) {
		Posts posts = postRepository.findById(pid).orElseThrow(
				() ->new ResourceNotFoundException("Posts", "pid", pid)
				);
		
		posts.setViews(count);
		postRepository.save(posts);
		return mapToDto(posts);
	}
	
	
}

package com.bbar.game.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bbar.game.post.domain.Post;
import com.bbar.game.post.dto.BoardDTO;
import com.bbar.game.post.repository.PostRepository;
import com.bbar.game.user.domain.User;
import com.bbar.game.user.service.UserService;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private UserService userService;
	
	public PostService(PostRepository postRepository, UserService userService) {
		this.postRepository = postRepository;
		this.userService = userService;
	}
	
	public boolean addPost(int userId, String title, String contents) {
		
		Post post = Post.builder()
		.userId(userId)
		.title(title)
		.contents(contents)
		.build();
		
		try {
			postRepository.save(post);
			return true;
		} catch(Exception e) {
			return false;
		}		
	}
	
	public List<BoardDTO> getPostList(int id){
		List<Post> postList =  postRepository.findAllByOrderByIdDesc();
		
		List<BoardDTO> boardList = new ArrayList<>();
		
		for(Post post:postList) {
			int userId = post.getUserId();
			User user = userService.getUser(userId); // 조회
			
			BoardDTO board = BoardDTO.builder()
			.postId(post.getId())
			.userId(userId)
			.title(post.getTitle())
			.contents(post.getContents())
			.imagePath(user.getImagePath())
			.nickname(user.getNickname())
			.createdAt(post.getCreatedAt())
			.build();
			
			boardList.add(board);
		}
		
		return boardList;
	}
	
//	public Post getPostId(int id) {
//		
//		Optional<Post> optionalPost = postRepository.findById(id);
//		
//		return optionalPost.orElse(null);
//	}
	
	public BoardDTO getPost(int id) {
		Post post = postRepository.findById(id).orElse(null);
		
		int userId = post.getUserId();
		User user = userService.getUser(userId);
		
		 BoardDTO board = BoardDTO.builder()
        .postId(post.getId())
        .userId(userId)
        .title(post.getTitle())
        .contents(post.getContents())
        .imagePath(user.getImagePath())
        .nickname(user.getNickname())
        .createdAt(post.getCreatedAt())
        .build();
		 
		 return board;
		
	}
	

}

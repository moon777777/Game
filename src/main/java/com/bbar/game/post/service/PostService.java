package com.bbar.game.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bbar.game.like.service.LikeService;
import com.bbar.game.post.domain.Post;
import com.bbar.game.post.dto.BoardDTO;
import com.bbar.game.post.repository.PostRepository;
import com.bbar.game.user.domain.User;
import com.bbar.game.user.service.UserService;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private UserService userService;
	private LikeService likeService;
	
	public PostService(PostRepository postRepository, UserService userService
			, LikeService likeService) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.likeService = likeService;
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
	
	public BoardDTO getPost(int id, int userId) {
		Post post = postRepository.findById(id).orElse(null);
		
		User user = userService.getUser(post.getUserId());
		boolean isLike = likeService.isLike(post.getId(), "post", userId);
		
		 BoardDTO board = BoardDTO.builder()
        .postId(post.getId())
        .userId(post.getUserId())
        .title(post.getTitle())
        .contents(post.getContents())
        .imagePath(user.getImagePath())
        .nickname(user.getNickname())
        .createdAt(post.getCreatedAt())
        .isLike(isLike)
        .build();
		 
		 return board;
		
	}
	

}

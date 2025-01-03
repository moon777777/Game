package com.bbar.game.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bbar.game.comment.DTO.CommentDTO;
import com.bbar.game.comment.service.CommentService;
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
	private CommentService commentService; 
	
	public PostService(PostRepository postRepository, UserService userService
			, LikeService likeService, CommentService commentService) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.likeService = likeService;
		this.commentService = commentService;
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
			User user = userService.getUser(userId);
			int likeCount = likeService.getLikeCount("post", post.getId());
			int commentCount = commentService.getCommentCount(post.getId());
			
			
			BoardDTO board = BoardDTO.builder()
			.postId(post.getId())
			.userId(userId)
			.title(post.getTitle())
			.contents(post.getContents())
			.imagePath(user.getImagePath())
			.nickname(user.getNickname())
			.createdAt(post.getCreatedAt())
			.likeCount(likeCount)
			.commentCount(commentCount)
			.build();
			
			boardList.add(board);
		}
		
		return boardList;
	}
	
	public BoardDTO getPost(int id, int userId) {
		Post post = postRepository.findById(id).orElse(null);
		
		User user = userService.getUser(post.getUserId());
		boolean isLike = likeService.isLike(post.getId(), "post", userId);
		int likeCount = likeService.getLikeCount("post", post.getId());
		int commentCount = commentService.getCommentCount(post.getId());
		postRepository.updateView(id);
		
		post = postRepository.findById(id).orElse(null);
		
		List<CommentDTO> commentList = commentService.getCommentList(post.getId(), userId);
		
		 BoardDTO board = BoardDTO.builder()
        .postId(post.getId())
        .userId(post.getUserId())
        .title(post.getTitle())
        .contents(post.getContents())
        .imagePath(user.getImagePath())
        .nickname(user.getNickname())
        .createdAt(post.getCreatedAt())
        .isLike(isLike)
        .likeCount(likeCount)
        .commentCount(commentCount)
        .commentList(commentList)
        .viewCount(post.getViewCount())
        .build();
		 
		 return board;
		
	}
	
	public int updateView(int postId) {
		return postRepository.updateView(postId);
	}

}

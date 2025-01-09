package com.bbar.game.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bbar.game.comment.DTO.CommentDTO;
import com.bbar.game.comment.service.CommentService;
import com.bbar.game.like.service.LikeService;
import com.bbar.game.post.domain.Post;
import com.bbar.game.post.dto.BoardDTO;
import com.bbar.game.post.repository.PostRepository;
import com.bbar.game.replies.service.RepliesService;
import com.bbar.game.user.domain.User;
import com.bbar.game.user.service.UserService;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private UserService userService;
	private LikeService likeService;
	private CommentService commentService;
	private RepliesService repliesService;
	
	public PostService(PostRepository postRepository, UserService userService
			, LikeService likeService, CommentService commentService
			, RepliesService repliesService) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.likeService = likeService;
		this.commentService = commentService;
		this.repliesService = repliesService;
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
	
	public boolean updatePost(int id, String title, String contents, int userId) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		if(optionalPost.isPresent()) {
			Post post = optionalPost.get();
			
			if(post.getUserId() == userId) {
				
				post = post.toBuilder()
				.title(title)
				.contents(contents)
				.build();
				
				postRepository.save(post);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
			
	}
	
	public boolean deletePost(int id, int userId) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		if(optionalPost.isPresent()) {
			Post post = optionalPost.get();
			
			if(post.getUserId() == userId) {
				likeService.deleteLikeByTargetId("post", id);
				likeService.deleteLikeByTargetId("comment", id);
				likeService.deleteLikeByTargetId("reply", id);
				commentService.deleteCommentByPostId(id);
				repliesService.deleteRepliesByPostId(id);
				
				postRepository.delete(post);
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}		
	}
	
	public List<BoardDTO> getPostList(){
		List<Post> postList =  postRepository.findAllByOrderByIdDesc();
		
		List<BoardDTO> boardList = new ArrayList<>();
		
		for(Post post:postList) {
			int userId = post.getUserId();
			User user = userService.getUser(userId);
			int likeCount = likeService.getLikeCount("post", post.getId());
			int commentCount = commentService.getCommentCount(post.getId());
			int repliesCount = repliesService.countReplies(post.getId());
			
			BoardDTO board = BoardDTO.builder()
			.postId(post.getId())
			.userId(userId)
			.title(post.getTitle())
			.contents(post.getContents())
			.imagePath(user.getImagePath())
			.nickname(user.getNickname())
			.createdAt(post.getCreatedAt())
			.likeCount(likeCount)
			.commentCount(commentCount + repliesCount)
			.viewCount(post.getViewCount())
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
	
	public Page<BoardDTO> paging(Pageable pageable) {
		int page = pageable.getPageNumber() - 1;
		int pageLimit = 10;
		
		Pageable usePageable = PageRequest.of(page, pageLimit, Sort.by("id").descending());
		
		Page<Post> boardPage = postRepository.findAll(usePageable);
		
		return boardPage.map(post -> {
			int userId = post.getUserId();
			User user = userService.getUser(userId);
			int likeCount = likeService.getLikeCount("post", post.getId());
			int commentCount = commentService.getCommentCount(post.getId());
			int repliesCount = repliesService.countReplies(post.getId());
			
			BoardDTO board = BoardDTO.builder()
			.postId(post.getId())
			.userId(userId)
			.title(post.getTitle())
			.contents(post.getContents())
			.nickname(user.getNickname())
			.createdAt(post.getCreatedAt())
			.likeCount(likeCount)
			.commentCount(commentCount + repliesCount)
			.viewCount(post.getViewCount())
			.build();
			
			return board;
		});
	}

}

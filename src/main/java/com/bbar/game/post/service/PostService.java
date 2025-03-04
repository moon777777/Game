package com.bbar.game.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bbar.game.comment.DTO.CommentDTO;
import com.bbar.game.comment.service.CommentService;
import com.bbar.game.images.DTO.ImagesDTO;
import com.bbar.game.images.service.ImagesService;
import com.bbar.game.like.service.LikeService;
import com.bbar.game.post.domain.Post;
import com.bbar.game.post.dto.BoardDTO;
import com.bbar.game.post.repository.PostRepository;
import com.bbar.game.replies.service.RepliesService;
import com.bbar.game.user.domain.User;
import com.bbar.game.user.repository.UserRepository;
import com.bbar.game.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private UserService userService;
	private LikeService likeService;
	private CommentService commentService;
	private RepliesService repliesService;
	private ImagesService imagesService;
	private UserRepository userRepository;
	
	public PostService(PostRepository postRepository, UserService userService
			, LikeService likeService, CommentService commentService
			, RepliesService repliesService
			, ImagesService imagesService
			, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.likeService = likeService;
		this.commentService = commentService;
		this.repliesService = repliesService;
		this.imagesService = imagesService;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public boolean addPost(int userId, String title, String contents, String youtubeUrl ,List<MultipartFile> files) {
		
		Post post = Post.builder()
		.userId(userId)
		.title(title)
		.youtubeUrl(youtubeUrl)
		.contents(contents)
		.build();
		
		try {
			postRepository.save(post);
			if(files != null) {
				boolean imagesSave = imagesService.addMultiImages(userId, post.getId(), files);
			}
			return true;
		} catch(Exception e) {
			return false;
		}		
	}
	
	public boolean updatePost(int id, String title, String contents, int userId, String youtubeUrl, List<MultipartFile> files) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		if(optionalPost.isPresent()) {
			Post post = optionalPost.get();
			if(files != null) {
				boolean imagesSave = imagesService.addMultiImages(userId, post.getId(), files);
			}
			if(post.getUserId() == userId) {
							
				post = post.toBuilder()
				.title(title)
				.contents(contents)
				.youtubeUrl(youtubeUrl)
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
				imagesService.deleteImagesByPostId(id);
				
				postRepository.delete(post);
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}		
	}
	
	public Page<BoardDTO> getPopularList(Pageable pageable){
		int page = pageable.getPageNumber();
		int pageLimit = 10;
		
		Pageable usePageable = PageRequest.of(page, pageLimit, Sort.by("id").descending());
		
		Page<Post> boardPage = postRepository.findPopular(usePageable);
		
		return boardPage.map(post -> {
			int userId = post.getUserId();
			User user = userService.getUser(userId);
			int likeCount = likeService.getLikeCount("post", post.getId());
			int commentCount = commentService.getCommentCount(post.getId());
			int repliesCount = repliesService.countReplies(post.getId());
			boolean isLike = false;

			List<ImagesDTO> imagesList = imagesService.getImages(post.getId());
			
			BoardDTO board = BoardDTO.builder()
			.postId(post.getId())
			.userId(userId)
			.title(post.getTitle())
			.contents(post.getContents())
			.nickname(user.getNickname())
			.imagePath(user.getImagePath())
			.createdAt(post.getCreatedAt())
			.likeCount(likeCount)
			.commentCount(commentCount + repliesCount)
			.viewCount(post.getViewCount())
			.imageFiles(imagesList)
			.youtubeUrl(post.getYoutubeUrl())
			.isLike(isLike)
			.build();
			
			return board;
		});
	}
	
	public BoardDTO getPost(int id, Integer userId) {
		Post post = postRepository.findById(id).orElse(null);
		
		if(userId == null) {
			userId = 0;
		}
		
		User user = userService.getUser(post.getUserId());
//		boolean isLike = likeService.isLike(post.getId(), "post", userId);
		
		 boolean isLike = false;
		    if (userId > 0) {
		        isLike = likeService.isLike(post.getId(), "post", userId);
		    }
		int likeCount = likeService.getLikeCount("post", post.getId());
		int commentCount = commentService.getCommentCount(post.getId());
		postRepository.updateView(id);

		List<CommentDTO> commentList = commentService.getCommentList(post.getId(), userId);
		List<ImagesDTO> imagesList = imagesService.getImages(post.getId());
		
		
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
        .imageFiles(imagesList)
        .youtubeUrl(post.getYoutubeUrl())
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
			
			List<ImagesDTO> imagesList = imagesService.getImages(post.getId());
			
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
			.imageFiles(imagesList)
			.youtubeUrl(post.getYoutubeUrl())
			.build();
			
			return board;
		});
	}
	
	
	public Page<BoardDTO> searchPaging(String title, Pageable pageable) {
		int page = pageable.getPageNumber();
		int pageLimit = 10;
		
		Pageable usePageable = PageRequest.of(page, pageLimit, Sort.by("id").descending());
		
		Page<Post> boardPage = postRepository.findByTitleContaining(title, usePageable);
		
		return boardPage.map(post -> {
			int userId = post.getUserId();
			User user = userService.getUser(userId);
			int likeCount = likeService.getLikeCount("post", post.getId());
			int commentCount = commentService.getCommentCount(post.getId());
			int repliesCount = repliesService.countReplies(post.getId());
			
			List<ImagesDTO> imagesList = imagesService.getImages(post.getId());
			
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
			.imageFiles(imagesList)
			.youtubeUrl(post.getYoutubeUrl())
			.build();
			
			return board;
		});
	}


}

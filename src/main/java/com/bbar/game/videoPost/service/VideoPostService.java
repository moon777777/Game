package com.bbar.game.videoPost.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bbar.game.like.service.LikeService;
import com.bbar.game.post.domain.Post;
import com.bbar.game.post.dto.BoardDTO;
import com.bbar.game.user.domain.User;
import com.bbar.game.user.service.UserService;
import com.bbar.game.videoPost.domain.VideoPost;
import com.bbar.game.videoPost.repository.VideoPostRepository;

@Service
public class VideoPostService {
	
	private VideoPostRepository videoPostRepository;
	private UserService userService;
	private LikeService likeService;
	
	public VideoPostService(VideoPostRepository videoPostRepository
			, UserService userService, LikeService likeService) {
		this.videoPostRepository = videoPostRepository;
		this.userService = userService;
		this.likeService = likeService;
	}
	
	public boolean addVideoPost(int userId, String title, String youtubeUrl, String thumbnailUrl) {
		
		if(userId == 5) {
			VideoPost videoPost = VideoPost.builder()
			.userId(userId)
			.title(title)
			.youtubeUrl(youtubeUrl)
			.thumbnail(thumbnailUrl)
			.build();
		
			videoPostRepository.save(videoPost);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateVideoPost(int id, String title, int userId, String youtubeUrl) {
		
		Optional<VideoPost> optionalVideoPost = videoPostRepository.findById(id);
		
		if(optionalVideoPost.isPresent()) {
			VideoPost videoPost = optionalVideoPost.get();

			if(videoPost.getUserId() == userId) {			
				videoPost = videoPost.toBuilder()
				.title(title)
				.youtubeUrl(youtubeUrl)
				.build();
				
				videoPostRepository.save(videoPost);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
			
	}
	
	public Page<BoardDTO> paging(Pageable pageable) {
		int page = pageable.getPageNumber() - 1;
		int pageLimit = 10;
		
		Pageable usePageable = PageRequest.of(page, pageLimit, Sort.by("id").descending());
		
		Page<VideoPost> boardPage = videoPostRepository.findAll(usePageable);
		
		return boardPage.map(post -> {
			int userId = post.getUserId();
			User user = userService.getUser(userId);
			int likeCount = likeService.getLikeCount("post", post.getId());
			
			BoardDTO board = BoardDTO.builder()
			.postId(post.getId())
			.userId(userId)
			.title(post.getTitle())
			.nickname(user.getNickname())
			.createdAt(post.getCreatedAt())
			.likeCount(likeCount)
			.viewCount(post.getViewCount())
			.youtubeUrl(post.getYoutubeUrl())
			.thumbnail(post.getThumbnail())
			.build();
			
			return board;
		});
	}
	
	public BoardDTO getVideoPost(int id, Integer userId) {
		VideoPost videoPost = videoPostRepository.findById(id).orElse(null);
		
		if(userId == null) {
			userId = 0;
		}
		
		User user = userService.getUser(videoPost.getUserId());
		
		 boolean isLike = false;
		    if (userId > 0) {
		        isLike = likeService.isLike(videoPost.getId(), "videoPost", userId);
		    }
		int likeCount = likeService.getLikeCount("Videopost", videoPost.getId());
		videoPostRepository.updateView(id);
		
		 BoardDTO board = BoardDTO.builder()
        .postId(videoPost.getId())
        .userId(videoPost.getUserId())
        .title(videoPost.getTitle())
        .nickname(user.getNickname())
        .createdAt(videoPost.getCreatedAt())
        .isLike(isLike)
        .likeCount(likeCount)
        .viewCount(videoPost.getViewCount())
        .youtubeUrl(videoPost.getYoutubeUrl())
        .build();
		 
		 return board;
		
	}

}

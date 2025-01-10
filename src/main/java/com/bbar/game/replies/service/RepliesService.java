package com.bbar.game.replies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bbar.game.comment.domain.Comment;
import com.bbar.game.like.service.LikeService;
import com.bbar.game.replies.DTO.RepliesDTO;
import com.bbar.game.replies.domain.Replies;
import com.bbar.game.replies.repository.RepliesRepository;
import com.bbar.game.user.domain.User;
import com.bbar.game.user.service.UserService;

@Service
public class RepliesService {
	
	private RepliesRepository repliesRepository;
	private UserService userService;
	private LikeService likeService;
	
	public RepliesService(RepliesRepository repliesRepository, UserService userService
			,  LikeService likeService) {
		this.repliesRepository = repliesRepository;
		this.userService = userService;
		this.likeService = likeService;
	}
	
	public boolean addReplies(int userId, int postId ,int commentId, String contents) {
		
		Replies replies = Replies.builder()
		.userId(userId)
		.postId(postId)
		.commentId(commentId)
		.contents(contents)
		.build();
	
		try {
			repliesRepository.save(replies);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public boolean updateReplies(int id, String contents, int userId) {
		Optional<Replies> optionalReplies = repliesRepository.findById(id);
		
		if(optionalReplies.isPresent()) {
			Replies replies = optionalReplies.get();
			
			if(replies.getUserId() == userId) {
				
				replies = replies.toBuilder()
				.contents(contents)
				.build();
				
				repliesRepository.save(replies);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean deleteReplies(int id, int userId) {
		
		Optional<Replies> optionalReplies = repliesRepository.findById(id);
		
		if(optionalReplies.isPresent()) {
			Replies replies = optionalReplies.get();
			
			if(replies.getUserId() == userId) {
				likeService.deleteLikeByTargetId("reply", id);
				
				repliesRepository.delete(replies);
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}		
	}
	
	public List<RepliesDTO> getRepliesList(int commentId, int session) {
		
		List<Replies> repliesList = repliesRepository.findByCommentId(commentId);
		
		List<RepliesDTO> repliesDTOList = new ArrayList<>();
		
		for(Replies replies: repliesList) {
			int userId = replies.getUserId();
			User user = userService.getUser(userId);
			boolean isRepliesLike = likeService.isLike(replies.getId(), "reply", session);
			int repliesLikeCount = likeService.getLikeCount("reply", replies.getId());
			
			RepliesDTO repliesDTO = RepliesDTO.builder()
			.repliesId(replies.getId())
			.userId(userId)
			.nickname(user.getNickname())
			.contents(replies.getContents())
			.createdAt(replies.getCreatedAt())
			.imagePath(user.getImagePath())
			.isRepliesLike(isRepliesLike)
			.repliesLikeCount(repliesLikeCount)
			.build();
			
			repliesDTOList.add(repliesDTO);
		}
		
		return repliesDTOList;
	}
	
	public void deleteRepliesByPostId(int postId) {
		repliesRepository.deleteByPostId(postId);
	}
	
	public int countReplies(int postId) {
		return repliesRepository.countByPostId(postId);
	}
	

}

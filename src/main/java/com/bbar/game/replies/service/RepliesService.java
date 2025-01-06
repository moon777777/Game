package com.bbar.game.replies.service;

import org.springframework.stereotype.Service;

import com.bbar.game.comment.domain.Comment;
import com.bbar.game.replies.domain.Replies;
import com.bbar.game.replies.repository.RepliesRepository;
import com.bbar.game.user.service.UserService;

@Service
public class RepliesService {
	
	private RepliesRepository repliesRepository;
	private UserService userService;
	
	public RepliesService(RepliesRepository repliesRepository, UserService userService) {
		this.repliesRepository = repliesRepository;
		this.userService = userService;
	}
	
	public boolean addReplies(int userId, int commentId, String contents) {
		
		Replies replies = Replies.builder()
		.userId(userId)
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
	

}

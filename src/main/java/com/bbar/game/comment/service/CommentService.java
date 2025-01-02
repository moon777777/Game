package com.bbar.game.comment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bbar.game.comment.DTO.CommentDTO;
import com.bbar.game.comment.Repository.CommentRepository;
import com.bbar.game.comment.domain.Comment;
import com.bbar.game.user.domain.User;
import com.bbar.game.user.service.UserService;

@Service
public class CommentService {
	
	private CommentRepository commentRepository;
	private UserService userService;
	
	public CommentService(CommentRepository commentRepository, UserService userService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
		
	}
	
	public boolean addComment(int userId, int postId, String contents) {
		
		Comment comment = Comment.builder()
		.userId(userId)
		.postId(postId)
		.contents(contents)
		.build();
	
		try {
			commentRepository.save(comment);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public List<CommentDTO> getCommentList(int postId) {
		
		List<Comment> commentList = commentRepository.findByPostId(postId);
		
		List<CommentDTO> commentDTOList = new ArrayList<>();
		
		for(Comment comment: commentList) {
			
			int userId = comment.getUserId();
			User user = userService.getUser(userId);
			
			CommentDTO commentDTO = CommentDTO.builder()
			.commentId(comment.getId())
			.userId(userId)
			.nickname(user.getNickname())
			.contents(comment.getContents())
			.build();
			
			commentDTOList.add(commentDTO);
		}
		
		return commentDTOList;
	}
	
	
}

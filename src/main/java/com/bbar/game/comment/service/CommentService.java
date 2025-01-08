package com.bbar.game.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bbar.game.comment.DTO.CommentDTO;
import com.bbar.game.comment.Repository.CommentRepository;
import com.bbar.game.comment.domain.Comment;
import com.bbar.game.like.service.LikeService;
import com.bbar.game.post.domain.Post;
import com.bbar.game.replies.DTO.RepliesDTO;
import com.bbar.game.replies.service.RepliesService;
import com.bbar.game.user.domain.User;
import com.bbar.game.user.service.UserService;

@Service
public class CommentService {
	
	private CommentRepository commentRepository;
	private UserService userService;
	private LikeService likeService;
	private RepliesService repliesService;
	
	public CommentService(CommentRepository commentRepository, UserService userService
			, LikeService likeService, RepliesService repliesService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.likeService = likeService;
		this.repliesService = repliesService;
		
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
	
	public boolean deleteComment(int id, int userId) {
		
		Optional<Comment> optionalComment = commentRepository.findById(id);
		
		if(optionalComment.isPresent()) {
			Comment comment = optionalComment.get();
			
			if(comment.getUserId() == userId) {
				likeService.deleteLikeByTargetId("comment", id);
//				likeService.deleteLikeByTargetId("reply", id);
//				repliesService.deleteRepliesByPostId(id);
				
				commentRepository.delete(comment);
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}		
	}
	
	public List<CommentDTO> getCommentList(int postId, int session) {
		
		List<Comment> commentList = commentRepository.findByPostId(postId);
		
		List<CommentDTO> commentDTOList = new ArrayList<>();
		
		for(Comment comment: commentList) {
			
			int userId = comment.getUserId();
			User user = userService.getUser(userId);
			boolean isCommentLike = likeService.isLike(comment.getId(), "comment", session);
			int commentLikeCount = likeService.getLikeCount("comment", comment.getId());
			List<RepliesDTO> repliesList = repliesService.getRepliesList(comment.getId(), session);
			
			CommentDTO commentDTO = CommentDTO.builder()
			.commentId(comment.getId())
			.userId(userId)
			.nickname(user.getNickname())
			.contents(comment.getContents())
			.createdAt(comment.getCreatedAt())
			.imagePath(user.getImagePath())
			.isCommentLike(isCommentLike)
			.commentLikeCount(commentLikeCount)
			.repliesList(repliesList)
			.build();
			
			commentDTOList.add(commentDTO);
		}
		
		return commentDTOList;
	}
	
	public int getCommentCount(int postId) {
		return commentRepository.countByPostId(postId);
	}
	
	public void deleteCommentByPostId(int postId) {
		commentRepository.deleteByPostId(postId);
	}
	
	
}

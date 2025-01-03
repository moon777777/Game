package com.bbar.game.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.bbar.game.comment.DTO.CommentDTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardDTO {
	
	private int postId;
	private int userId;
	
	private String title;
	private String contents;
	private String nickname;
	private String imagePath;
	private LocalDateTime createdAt;
	
	private int likeCount;
	private boolean isLike;
	private int commentCount;
	
	private List<CommentDTO> commentList; 

}

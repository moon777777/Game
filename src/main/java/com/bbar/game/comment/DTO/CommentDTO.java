package com.bbar.game.comment.DTO;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentDTO {
	private int commentId;
	private int userId;
	
	private String nickname;
	private String contents;
	
	private LocalDateTime createdAt;
	private String imagePath;
	
	private int commentLikeCount;
	private boolean isCommentLike;
}

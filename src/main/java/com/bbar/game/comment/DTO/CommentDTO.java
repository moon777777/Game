package com.bbar.game.comment.DTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentDTO {
	private int commentId;
	private int userId;
	
	private String nickname;
	private String contents;
}

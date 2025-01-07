package com.bbar.game.replies.DTO;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RepliesDTO {
	private int userId;
	private int repliesId;
	private String nickname;
	private String contents;
	private String imagePath;
	private LocalDateTime createdAt;
	
	private int repliesLikeCount;
	private boolean isRepliesLike;

}

package com.bbar.game.images.DTO;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImagesDTO {
	private int imagesId;
	private int postId;
	
	private String imagePath;
	private LocalDateTime createdAt;

}

package com.bbar.game.images.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bbar.game.images.domain.Images;
import com.bbar.game.images.repository.ImagesRepository;

@Service
public class ImagesService {
	
	private ImagesRepository imagesRepository; 
	
	public ImagesService(ImagesRepository imagesRepository) {
		this.imagesRepository = imagesRepository;
	}
	
	public boolean addImages(int userId, int postId, MultipartFile file) {
		
		String imagePath = com.bbar.game.common.MultiFileManager.saveFile(userId, postId, file);
		
		Images images = Images.builder()
		.postId(postId)
		.imagePath(imagePath)
		.build();
		
		try {
			imagesRepository.save(images);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

}

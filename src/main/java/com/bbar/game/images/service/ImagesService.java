package com.bbar.game.images.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bbar.game.common.FileManager;
import com.bbar.game.images.domain.Images;
import com.bbar.game.images.repository.ImagesRepository;

@Service
public class ImagesService {
	
	private ImagesRepository imagesRepository; 
	
	public ImagesService(ImagesRepository imagesRepository) {
		this.imagesRepository = imagesRepository;
	}
	
	public boolean addImages(int userId, int postId, MultipartFile file) {

	    String imagePath = FileManager.saveFiles(userId, postId, file);
	
		Images images = Images.builder()
		.postId(postId)
		.imagePath(imagePath)
		.build();
	  	
		 try {
            imagesRepository.save(images);
            return true;
        } catch (Exception e) {
            return false; 
        }
	}
	
	public boolean addMultiImages(int userId, int postId, List<MultipartFile> files) {
		
		List<Images> imagesList = new ArrayList<>();
		
		if (files == null) {
	        return false;
	    }
		
		for (MultipartFile file : files) {
			String imagePath = FileManager.saveFiles(userId, postId, file);
			
			Images images = Images.builder()
			.postId(postId)
			.imagePath(imagePath)
			.build();
			
			imagesList.add(images);
		}
	            
		 try {
            imagesRepository.saveAll(imagesList);
            return true;
        } catch (Exception e) {
            return false; 
        }
    }
}


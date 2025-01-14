package com.bbar.game.images.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bbar.game.common.FileManager;
import com.bbar.game.images.DTO.ImagesDTO;
import com.bbar.game.images.domain.Images;
import com.bbar.game.images.repository.ImagesRepository;

@Service
public class ImagesService {
	
	private ImagesRepository imagesRepository; 
	
	public ImagesService(ImagesRepository imagesRepository) {
		this.imagesRepository = imagesRepository;
	}
	
//	public boolean addImages(int userId, int postId, MultipartFile file) {
//
//	    String imagePath = FileManager.saveFiles(userId, postId, file);
//	
//		Images images = Images.builder()
//		.postId(postId)
//		.imagePath(imagePath)
//		.build();
//	  	
//		 try {
//            imagesRepository.save(images);
//            return true;
//        } catch (Exception e) {
//            return false; 
//        }
//	}

	public boolean addMultiImages(int userId, int postId, List<MultipartFile> files) {
		
		List<Images> imagesList = new ArrayList<>();
		
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
	
	public List<ImagesDTO> getImages(int postId) {
        List<Images> images = imagesRepository.findByPostId(postId); 
        List<ImagesDTO> imagesDTOList = new ArrayList<>();
        
        for (Images image : images) {
        	 ImagesDTO imagesDTO = ImagesDTO.builder()
             .imageId(image.getId())
             .postId(image.getPostId())
             .imagePath(image.getImagePath())
             .build();
        	 
        	 imagesDTOList.add(imagesDTO);
        }
        
        return imagesDTOList;
    }
	
	// 게시물 삭제시 이미지 삭제
	public void deleteImagesByPostId(int postId) {
		imagesRepository.deleteByPostId(postId);
	}
	
	// 이미지만 따로 삭제
	public boolean deleteImage(int id) {
		Optional<Images> optionalImages = imagesRepository.findById(id);
		
		if(optionalImages.isPresent()) {
			Images images = optionalImages.get();
			
			imagesRepository.delete(images);
			return true;
		} else {
			return false;
		}
	}
}


package com.bbar.game.images;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bbar.game.images.service.ImagesService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class ImagesRestController {
	
private ImagesService imagesService; 
	
	public ImagesRestController(ImagesService imagesService) {
		this.imagesService = imagesService;
	}
	
	@PostMapping("/images/input")
	public Map<String, String> addImages(
			HttpSession session
			, @RequestParam("postId") int postId
			, @RequestParam("imageFile") MultipartFile file){
		
		int userId = (Integer)session.getAttribute("userId");
		
		Map <String, String> resultMap = new HashMap<>();
		
		 boolean success = imagesService.addImages(userId, postId, file);
		
		if(imagesService.addImages(userId, postId, file)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

}

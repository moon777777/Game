package com.bbar.game.images;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbar.game.images.service.ImagesService;

@RestController
@RequestMapping("/post")
public class ImagesRestController {
	
private ImagesService imagesService; 
	
	public ImagesRestController(ImagesService imagesService) {
		this.imagesService = imagesService;
	}
	
	@DeleteMapping("/images/delete")
	public Map<String, String> deleteImage(
			@RequestParam("id") int id){
		
		Map <String, String> resultMap = new HashMap<>();
		
		if(imagesService.deleteImage(id)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

}

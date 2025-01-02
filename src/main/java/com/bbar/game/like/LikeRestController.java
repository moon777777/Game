package com.bbar.game.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbar.game.like.service.LikeService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class LikeRestController {
	
private LikeService likeService;
	
	public LikeRestController(LikeService likeService ) {
		this.likeService = likeService;
	}
	
	@PostMapping("/like")
	public Map<String, String> like(
	        @RequestParam("targetType") String targetType
	        , @RequestParam("targetId") int targetId,        
	        HttpSession session) {

	    int userId = (Integer) session.getAttribute("userId");
	    Map<String, String> resultMap = new HashMap<>();

	    if (likeService.addLike(targetType, targetId, userId)) {
	        resultMap.put("result", "success");
	    } else {
	        resultMap.put("result", "fail");
	    }
	    
	    return resultMap;
	}
	
	@DeleteMapping("/unlike")
	public Map<String, String> unlike(
			@RequestParam("targetType") String targetType
			, @RequestParam("targetId") int targetId
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		Map <String, String> resultMap = new HashMap<>();
		if(likeService.deleteLike(targetId, targetType, userId)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
}
